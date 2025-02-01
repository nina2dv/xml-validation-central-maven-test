package com.example;

import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Class responsible for validating XML files against Schematron schemas using SchXslt and Saxon-HE.
 * Schematron is a rule-based validation language for XML, and this class uses XSLT transformations
 * to validate XML files against Schematron rules.
 */
public class SchematronValidator {

    // Path to the SchXslt pipeline XSLT file, which is used to compile Schematron to XSLT
    private static final String SCHXSXLT_COMPILE = "src/main/resources/schematron/xslt/compile-for-svrl.xsl";

    /**
     * Compiles a Schematron schema (.sch) into an XSLT stylesheet using SchXslt.
     *
     * @param schematronPath Path to the Schematron (.sch) file.
     * @return Compiled Schematron XSLT as a string.
     * @throws SaxonApiException if an error occurs during compilation.
     */
    public static String compileSchematronToXSLT(String schematronPath) throws SaxonApiException {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable executable = compiler.compile(new StreamSource(new File(SCHXSXLT_COMPILE)));
        XsltTransformer transformer = executable.load();
        transformer.setSource(new StreamSource(new File(schematronPath)));

        StringWriter sw = new StringWriter();
        Serializer serializer = processor.newSerializer(sw);
        transformer.setDestination(serializer);
        transformer.transform();

        return sw.toString();
    }


    /**
     * Applies the compiled Schematron XSLT to an XML document to produce an SVRL report.
     * SVRL (Schematron Validation Report Language) is an XML format for reporting validation results.
     *
     * @param compiledSchematronXSLT The compiled Schematron XSLT as a string.
     * @param xmlPath                Path to the XML file to be validated.
     * @return SVRL report as a string.
     * @throws SaxonApiException if an error occurs during transformation.
     */
    public static String applySchematronValidation(String compiledSchematronXSLT, String xmlPath)
            throws SaxonApiException {
        // Create a new Saxon processor
        Processor processor = new Processor(false);

        // Create an XSLT compiler to compile the compiled Schematron XSLT
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable executable = compiler.compile(new StreamSource(new StringReader(compiledSchematronXSLT)));

        // Load the compiled XSLT into a transformer
        XsltTransformer transformer = executable.load();

        // Set the XML document as the source for the transformation
        transformer.setSource(new StreamSource(new File(xmlPath)));

        // Capture the SVRL output into a StringWriter
        StringWriter sw = new StringWriter();
        Serializer serializer = processor.newSerializer(sw);
        transformer.setDestination(serializer);

        // Perform the transformation to generate the SVRL report
        transformer.transform();

        // Return the SVRL report as a string
        return sw.toString();
    }

    /**
     * Parses the SVRL report to determine if there are any failed assertions or reports.
     * Failed assertions are considered errors, while failed reports are considered warnings.
     *
     * @param svrl The SVRL report as a string.
     * @return true if validation passes without critical errors, false otherwise.
     */
    private static boolean parseSVRL(String svrl) {
        // Create a new Saxon processor
        Processor processor = new Processor(false);

        // Create a document builder to parse the SVRL report
        DocumentBuilder builder = processor.newDocumentBuilder();
        XdmNode doc;
        try {
            // Parse the SVRL report into an XdmNode (Saxon's representation of an XML document)
            doc = builder.build(new StreamSource(new StringReader(svrl)));
        } catch (SaxonApiException e) {
            System.err.println("Error parsing SVRL: " + e.getMessage());
            return false;
        }

        // Create an XPath compiler to query the SVRL report
        XPathCompiler xpath = processor.newXPathCompiler();
        xpath.declareNamespace("svrl", "http://purl.oclc.org/dsdl/svrl");

        boolean hasErrors = false;
        boolean hasWarnings = false;
        int errorCount = 0;
        int warningCount = 0;

        try {
            // Process failed-assert elements (errors)
            XPathSelector assertSelector = xpath.compile("//svrl:failed-assert").load();
            assertSelector.setContextItem(doc);
            Iterable<XdmItem> assertResults = assertSelector.evaluate();

            for (XdmItem item : assertResults) {
                XdmNode failedAssert = (XdmNode) item;

                // Extract the SVRL attributes
                String location = failedAssert.getAttributeValue(new QName("location"));
                String test = failedAssert.getAttributeValue(new QName("test"));

                // Extract and clean up the main text
                String rawText = failedAssert.getStringValue();
                String message = rawText.trim().replaceAll("\\s+", " ");
                message = message.replaceAll("^,\\s*", "");

                // Clean up the location string
                String cleanedLocation = location
                        .replaceAll("/Q\\{[^}]*\\}", "/")  // remove /Q{...}/
                        .replaceAll("\\[\\d+\\]", "");     // remove [n] indexes

                // Print the error details
                System.err.println("**Error:**");
                System.err.println("  Location: " + cleanedLocation);
                System.err.println("  Test: " + test);
                System.err.println("  Message: " + message);
                hasErrors = true;
                errorCount++;
            }

            // Process failed-report elements (warnings)
            XPathSelector reportSelector = xpath.compile("//svrl:failed-report").load();
            reportSelector.setContextItem(doc);
            Iterable<XdmItem> reportResults = reportSelector.evaluate();

            for (XdmItem item : reportResults) {
                XdmNode failedReport = (XdmNode) item;

                // Extract the SVRL attributes
                String location = failedReport.getAttributeValue(new QName("location"));
                String test = failedReport.getAttributeValue(new QName("test"));

                // Extract and clean up the main text
                String rawText = failedReport.getStringValue();
                String message = rawText.trim().replaceAll("\\s+", " ");
                message = message.replaceAll("^,\\s*", "");

                // Clean up the location string
                String cleanedLocation = location
                        .replaceAll("/Q\\{[^}]*\\}", "/")
                        .replaceAll("\\[\\d+\\]", "");

                // Print the warning details
                System.out.println("**Warning:**");
                System.out.println("  Location: " + cleanedLocation);
                System.out.println("  Test: " + test);
                System.out.println("  Message: " + message);
                hasWarnings = true;
                warningCount++;
            }

            // Print a summary of the validation results
            if (hasErrors) {
                System.err.println("Schematron validation failed with " + errorCount + " error(s).");
            } else {
                System.out.println("No errors found in Schematron validation.");
            }

            if (hasWarnings) {
                System.out.println("Schematron validation completed with " + warningCount + " warning(s).");
            } else {
                System.out.println("No warnings found in Schematron validation.");
            }

            // Return true if there are no errors, false otherwise
            return !hasErrors;

        } catch (SaxonApiException e) {
            System.err.println("Error during SVRL parsing: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates an XML file against a Schematron schema.
     *
     * @param schematronPath Path to the Schematron (.sch) file.
     * @param xmlPath        Path to the XML file to be validated.
     * @return true if validation passes without critical errors, false otherwise.
     */
    public static boolean validateSchematron(String schematronPath, String xmlPath) {
        try {
            // Step 1: Compile Schematron to XSLT
            String compiledXSLT = compileSchematronToXSLT(schematronPath);

            // Step 2: Apply compiled XSLT to XML to get SVRL
            String svrl = applySchematronValidation(compiledXSLT, xmlPath);

            // Print the raw SVRL output for debugging purposes
            // System.out.println("Raw SVRL Output:");
            // System.out.println(svrl);

            // Step 3: Parse SVRL to determine validation results
            return parseSVRL(svrl);

        } catch (Exception e) {
            System.err.println("Schematron validation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Main method to execute Schematron validation from the command line.
     *
     * @param args Command-line arguments: <schematron-file.sch> <xml-file.xml>
     */
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length < 2) {
            System.err.println("Usage: ValidateSchematron <schematron.sch> <xml-file.xml>");
            System.exit(1); // Exit with an error code if arguments are missing
        }

        // Assign command-line arguments to variables for easier access
        String schematronPath = args[0]; // Path to the Schematron file
        String xmlPath = args[1]; // Path to the XML file to be validated

        // Validate the XML file against the Schematron schema
        boolean isValid = validateSchematron(schematronPath, xmlPath);

        // Exit with appropriate status code based on validation result
        if (!isValid) {
            System.exit(1); // Exit with error code if validation fails
        } else {
            System.exit(0); // Exit with success code if validation passes
        }
    }
}