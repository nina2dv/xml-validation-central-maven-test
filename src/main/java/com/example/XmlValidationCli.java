package com.example;

import com.example.validation.SchematronValidator;
import com.example.validation.ValidationResult;
import com.example.validation.XsdValidator;
import java.io.File;

/**
 * Command-line interface (CLI) for running XML validations using XSD and Schematron.
 * <p>
 * Usage: <code>java -jar xml-validator.jar &lt;xsd-file&gt; &lt;schematron-file&gt; &lt;xml-file&gt;</code>
 * </p>
 */
public class XmlValidationCli {

    /**
     * Main method for the XML validation CLI.
     *
     * @param args command-line arguments: xsd-file, schematron-file, xml-file.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java -jar xml-validator.jar <xsd-file> <schematron-file> <xml-file>");
            System.exit(1);
        }
        try {
            File xsdFile = new File(args[0]).getCanonicalFile();
            File schematronFile = new File(args[1]).getCanonicalFile();
            File xmlFile = new File(args[2]).getCanonicalFile();

            System.out.println("XSD file: " + xsdFile.getCanonicalPath());
            System.out.println("Schematron file: " + schematronFile.getCanonicalPath());
            System.out.println("XML file: " + xmlFile.getCanonicalPath());

            boolean overallSuccess = true;

            // --- XSD Validation ---
            System.out.println("Running XSD validation...");
            try {
                ValidationResult xsdResult = XsdValidator.validateXMLSchema(xsdFile.getCanonicalPath(), xmlFile.getCanonicalPath());
                if (!xsdResult.isValid()) {
                    System.err.println("XSD validation errors:");
                    xsdResult.getErrors().forEach(System.err::println);
                    overallSuccess = false;
                } else {
                    System.out.println("XSD validation passed.");
                }
                if (!xsdResult.getWarnings().isEmpty()) {
                    System.out.println("XSD validation warnings:");
                    xsdResult.getWarnings().forEach(System.out::println);
                }
            } catch (Exception ex) {
                System.err.println("XSD validation failed: " + ex.getMessage());
                overallSuccess = false;
            }

            // --- Schematron Validation ---
            System.out.println("Running Schematron validation...");
            try {
                ValidationResult schematronResult = SchematronValidator.validateSchematron(
                        schematronFile.getCanonicalPath(), xmlFile.getCanonicalPath());
                if (!schematronResult.isValid()) {
                    System.err.println("Schematron validation errors:");
                    schematronResult.getErrors().forEach(System.err::println);
                    overallSuccess = false;
                } else {
                    System.out.println("Schematron validation passed.");
                }
                // Optionally print warnings:
                if (!schematronResult.getWarnings().isEmpty()) {
                    System.out.println("Schematron validation warnings:");
                    schematronResult.getWarnings().forEach(System.out::println);
                }
            } catch (Exception ex) {
                System.err.println("Schematron validation failed: " + ex.getMessage());
                overallSuccess = false;
            }

            if (!overallSuccess) {
                System.exit(1);
            } else {
                System.out.println("All validations passed.");
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
