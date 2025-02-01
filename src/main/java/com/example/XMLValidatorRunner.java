package com.example;

/**
 * Utility class to run XML validators based on the specified type.
 */
public class XMLValidatorRunner {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: XMLValidatorRunner <validator-type> <schematron-file.sch|xsd-file.xsd> <xml-file.xml>");
            System.err.println("Validator types: xsd, schematron");
            throw new IllegalArgumentException("Insufficient arguments provided.");
        }

        String validatorType = args[0];
        String schemaPath = args[1];
        String xmlPath = args[2];

        boolean isValid = false;

        switch (validatorType.toLowerCase()) {
            case "xsd":
                isValid = XSDValidator.validateXMLSchema(schemaPath, xmlPath);
                break;
            case "schematron":
                isValid = SchematronValidator.validateSchematron(schemaPath, xmlPath);
                break;
            default:
                throw new IllegalArgumentException("Unknown validator type: " + validatorType);
        }

        if (!isValid) {
            throw new RuntimeException("Validation failed for type: " + validatorType);
        }
    }
}
