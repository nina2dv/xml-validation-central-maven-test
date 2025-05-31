package io.github.nina2dv.xmlvalidation;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Provides methods for validating an XML file against an XSD schema.
 */
public class XsdValidator {

    /**
     * Validates an XML file against an XSD schema, accumulating all validation errors and warnings.
     * File access or schema-building problems are thrown as exceptions.
     *
     * @param xsdPath the path to the XSD (.xsd) file.
     * @param xmlPath the path to the XML file.
     * @return a {@link ValidationResult} populated with any errors and warnings found during XML validation.
     * @throws Exception if an error occurs while accessing the files or building the schema.
     */
    public static ValidationResult validateXMLSchema(String xsdPath, String xmlPath) throws Exception {
        ValidationResult validationResult = new ValidationResult();
        try (InputStream xsdStream = new FileInputStream(xsdPath);
             InputStream xmlStream = new FileInputStream(xmlPath)) {

            // Create the schema and validator.
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();

            // Set our custom error handler.
            XsdErrorHandler errorHandler = new XsdErrorHandler();
            validator.setErrorHandler(errorHandler);

            // Perform validation.
            // Some implementations may throw an exception on fatal errors;
            // here we catch it because errors are being collected.
            try {
                StreamSource xmlSource = new StreamSource(xmlStream);
                xmlSource.setSystemId(new File(xmlPath).toURI().toString());
                validator.validate(xmlSource);
            } catch (SAXException ignored) {
                // Ignored: errors are collected via the error handler.
            }

            // Add collected errors.
            for (SAXParseException ex : errorHandler.getErrors()) {
                String location = "Line " + ex.getLineNumber() + ", Column " + ex.getColumnNumber();
                validationResult.addError("XSD Validation", location, ex.getMessage());
            }

            // Add collected warnings.
            for (SAXParseException ex : errorHandler.getWarnings()) {
                String location = "Line " + ex.getLineNumber() + ", Column " + ex.getColumnNumber();
                validationResult.addWarning("XSD Validation", location, ex.getMessage());
            }

        } catch (Exception ex) {
            // Rethrow any exception (such as file access issues) so that the caller is notified.
            throw new Exception("XSD Validation encountered a problem: " + ex.getMessage(), ex);
        }
        return validationResult;
    }
}
