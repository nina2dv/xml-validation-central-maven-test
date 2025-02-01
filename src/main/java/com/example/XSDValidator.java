package com.example;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Class responsible for validating XML files against an XSD schema.
 */
public class XSDValidator {

    /**
     * Validates an XML file against an XSD schema.
     *
     * @param xsdPath Path to the XSD (.xsd) file.
     * @param xmlPath Path to the XML file to be validated.
     * @return true if validation passes, false otherwise.
     */
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try (
                InputStream xsdStream = new FileInputStream(new File(xsdPath));
                InputStream xmlStream = new FileInputStream(new File(xmlPath))
        ) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            System.out.println("XML is valid against the schema.");
            return true;
        } catch (Exception e) {
            System.err.println("XML validation failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Main method to execute XSD validation from the command line.
     *
     * @param args Command-line arguments: <xsd-file.xsd> <xml-file.xml>
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: IstarXMLValidator <xsd-file.xsd> <xml-file.xml>");
            System.exit(1);
        }

        String xsdPath = args[0];
        String xmlPath = args[1];

        boolean isValid = validateXMLSchema(xsdPath, xmlPath);

        if (!isValid) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }
}
