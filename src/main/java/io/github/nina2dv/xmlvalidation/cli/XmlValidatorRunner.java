package io.github.nina2dv.xmlvalidation.cli;

import io.github.nina2dv.xmlvalidation.XmlValidationService;
import io.github.nina2dv.xmlvalidation.ValidationResult;

/**
 * Utility class to run XML validators based on the specified type.
 * <p>
 * Usage: <code>XmlValidatorRunner &lt;validator-type&gt; &lt;schema-file&gt; &lt;xml-file&gt;</code>
 * where validator-type can be "xsd" or "schematron".
 * </p>
 */
public class XmlValidatorRunner {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: XmlValidatorRunner <validator-type> <schema-file> <xml-file>");
            System.err.println("Validator types: xsd, schematron");
            throw new IllegalArgumentException("Insufficient arguments provided.");
        }

        String validatorType = args[0];
        String schemaPath = args[1];
        String xmlPath = args[2];

        XmlValidationService service = new XmlValidationService();
        ValidationResult result = service.validate(validatorType, schemaPath, xmlPath);

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
