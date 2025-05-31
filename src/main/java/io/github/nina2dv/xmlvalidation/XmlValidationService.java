package io.github.nina2dv.xmlvalidation;

public class XmlValidationService {

    /**
     * Validates an XML file using the specified validator type.
     *
     * @param validatorType the type of validator ("xsd" or "schematron")
     * @param schemaPath    the path to the schema file
     * @param xmlPath       the path to the XML file
     * @return the result of the validation
     * @throws Exception if an unknown validator type is provided
     */
    public ValidationResult validate(String validatorType, String schemaPath, String xmlPath) throws Exception {
        switch (validatorType.toLowerCase()) {
            case "xsd":
                return XsdValidator.validateXMLSchema(schemaPath, xmlPath);
            case "schematron":
                return SchematronValidator.validateSchematron(schemaPath, xmlPath);
            default:
                throw new IllegalArgumentException("Unknown validator type: " + validatorType);
        }
    }
}
