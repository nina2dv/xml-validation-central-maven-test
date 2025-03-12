package com.example.validation;

import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

/**
 * Provides Schematron-based XML validation using SchXslt and Saxon-HE.
 */
public class SchematronValidator {

    // Classpath location of the SchXslt pipeline XSLT file.
    private static final String SCHXSXLT_COMPILE = "/schematron/xslt/compile-for-svrl.xsl";

    /**
     * Compiles a Schematron schema (.sch) into an XSLT stylesheet.
     *
     * @param schematronPath Path to the Schematron (.sch) file.
     * @return Compiled Schematron XSLT as a string.
     * @throws SaxonApiException if an error occurs during XSLT compilation.
     * @throws java.io.IOException if the XSLT resource cannot be read.
     */
    public static String compileSchematronToXSLT(String schematronPath)
            throws SaxonApiException, java.io.IOException {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();

        // Load the XSLT file from the classpath.
        URL xsltUrl = SchematronValidator.class.getResource(SCHXSXLT_COMPILE);
        if (xsltUrl == null) {
            throw new SaxonApiException("Resource not found: " + SCHXSXLT_COMPILE);
        }
        // Note: We no longer catch IOException here—let it propagate.
        InputStream xsltStream = xsltUrl.openStream();
        StreamSource source = new StreamSource(xsltStream);
        source.setSystemId(xsltUrl.toExternalForm());

        XsltExecutable executable = compiler.compile(source);
        XsltTransformer transformer = executable.load();
        transformer.setSource(new StreamSource(schematronPath));

        StringWriter sw = new StringWriter();
        Serializer serializer = processor.newSerializer(sw);
        transformer.setDestination(serializer);
        transformer.transform();

        return sw.toString();
    }

    /**
     * Applies the compiled Schematron XSLT to an XML document.
     *
     * @param compiledSchematronXSLT The compiled Schematron XSLT as a string.
     * @param xmlPath                Path to the XML file to be validated.
     * @return SVRL report as a string.
     * @throws SaxonApiException if an error occurs during transformation.
     */
    public static String applySchematronValidation(String compiledSchematronXSLT, String xmlPath)
            throws SaxonApiException {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable executable = compiler.compile(new StreamSource(new StringReader(compiledSchematronXSLT)));
        XsltTransformer transformer = executable.load();
        transformer.setSource(new StreamSource(xmlPath));

        StringWriter sw = new StringWriter();
        Serializer serializer = processor.newSerializer(sw);
        transformer.setDestination(serializer);
        transformer.transform();

        return sw.toString();
    }

    /**
     * Overloaded version of applySchematronValidation that accepts an InputStream for the XML.
     *
     * @param compiledSchematronXSLT The compiled Schematron XSLT as a string.
     * @param xmlStream              InputStream for the XML document.
     * @return SVRL report as a string.
     * @throws SaxonApiException if an error occurs during transformation.
     */
    public static String applySchematronValidation(String compiledSchematronXSLT, InputStream xmlStream)
            throws SaxonApiException {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable executable = compiler.compile(new StreamSource(new StringReader(compiledSchematronXSLT)));
        XsltTransformer transformer = executable.load();
        transformer.setSource(new StreamSource(xmlStream));

        StringWriter sw = new StringWriter();
        Serializer serializer = processor.newSerializer(sw);
        transformer.setDestination(serializer);
        transformer.transform();

        return sw.toString();
    }

    /**
     * Parses the SVRL report and builds a ValidationResult.
     *
     * @param svrl The SVRL report as a string.
     * @return A ValidationResult containing error and warning messages.
     * @throws SaxonApiException if an error occurs during parsing.
     */
    public static ValidationResult parseSVRL(String svrl) throws SaxonApiException {
        Processor processor = new Processor(false);
        DocumentBuilder builder = processor.newDocumentBuilder();
        XdmNode doc = builder.build(new StreamSource(new StringReader(svrl)));

        XPathCompiler xpath = processor.newXPathCompiler();
        xpath.declareNamespace("svrl", "http://purl.oclc.org/dsdl/svrl");

        ValidationResult result = new ValidationResult();

        // Query both failed-assert and successful-report elements.
        XPathSelector selector = xpath.compile("//svrl:failed-assert | //svrl:successful-report").load();
        selector.setContextItem(doc);
        for (XdmItem item : selector.evaluate()) {
            XdmNode assertion = (XdmNode) item;
            String role = assertion.getAttributeValue(new QName("role"));
            String location = assertion.getAttributeValue(new QName("location"));
            String test = assertion.getAttributeValue(new QName("test"));
            String rawText = assertion.getStringValue();
            String message = rawText.trim().replaceAll("\\s+", " ").replaceAll("^,\\s*", "");
            String cleanedLocation = location
                    .replaceAll("/Q\\{[^}]*\\}", "/")
                    .replaceAll("\\[\\d+\\]", "");

            // Decide based on the role attribute.
            if ("ERROR".equals(role)) {
                result.addError(new Result(test, cleanedLocation, message));
            } else if ("WARN".equals(role)) {
                result.addWarning(new Result(test, cleanedLocation, message));
            }
        }
        return result;
    }

    /**
     * Validates an XML file against a Schematron schema.
     *
     * @param schematronPath Path to the Schematron (.sch) file.
     * @param xmlPath        Path to the XML file to be validated.
     * @return A ValidationResult containing errors and warnings.
     * @throws Exception if any error occurs during validation.
     */
    public static ValidationResult validateSchematron(String schematronPath, String xmlPath)
            throws Exception {
        // Compile Schematron to XSLT.
        String compiledXSLT = compileSchematronToXSLT(schematronPath);
        // Apply the compiled XSLT to the XML document.
        String svrl = applySchematronValidation(compiledXSLT, xmlPath);

        //// Debug purposes
        // System.out.println("SVRL Output: " + svrl);

        // Parse the SVRL and return the validation results.
        return parseSVRL(svrl);
    }
}
