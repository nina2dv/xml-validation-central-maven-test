package com.example;

import com.example.validation.SchematronValidator;
import com.example.validation.XsdValidator;
import com.example.validation.ValidationResult;
import com.example.validation.Result;

/**
 * Utility class to run XML validators based on the specified type.
 * <p>
 * Usage: <code>XmlValidatorRunner &lt;validator-type&gt; &lt;schema-file&gt; &lt;xml-file&gt;</code>
 * where validator-type can be "xsd" or "schematron".
 * </p>
 */
public class XmlValidatorRunner {
    /**
     * Main method to execute the XML validation.
     *
     * @param args command-line arguments: validator-type, schema-file, xml-file.
     * @throws Exception if an error occurs during validation.
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: XmlValidatorRunner <validator-type> <schema-file> <xml-file>");
            System.err.println("Validator types: xsd, schematron");
            throw new IllegalArgumentException("Insufficient arguments provided.");
        }

        String validatorType = args[0];
        String schemaPath = args[1];
        String xmlPath = args[2];

        ValidationResult result;
        switch (validatorType.toLowerCase()) {
            case "xsd":
                result = XsdValidator.validateXMLSchema(schemaPath, xmlPath);
                break;
            case "schematron":
                result = SchematronValidator.validateSchematron(schemaPath, xmlPath);
                break;
            default:
                throw new IllegalArgumentException("Unknown validator type: " + validatorType);
        }

        if (!result.isValid()) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("Validation failed for type: ").append(validatorType).append("\nErrors:");
            result.getErrors().forEach(error -> errorMsg.append("\n  ").append(error));
            if (!result.getWarnings().isEmpty()) {
                errorMsg.append("\nWarnings:");
                result.getWarnings().forEach(warning -> errorMsg.append("\n  ").append(warning));
            }
            throw new RuntimeException(errorMsg.toString());
        } else {
            System.out.println("Validation passed for type: " + validatorType);
            if (!result.getWarnings().isEmpty()) {
                System.out.println("Warnings:");
                result.getWarnings().forEach(warning -> System.out.println("  " + warning));
            }
        }
    }
}
