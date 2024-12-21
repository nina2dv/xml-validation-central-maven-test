package main.java;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;

public class IstarXMLValidator {
    public static void validateXMLSchema(String xsdPath, String xmlPath) {
        try (
                InputStream xsdStream = IstarXMLValidator.class.getResourceAsStream(xsdPath);
                InputStream xmlStream = IstarXMLValidator.class.getResourceAsStream(xmlPath)
        ) {
            if (xsdStream == null) {
                System.out.println("XSD resource not found: " + xsdPath);
                return;
            }
            if (xmlStream == null) {
                System.out.println("XML resource not found: " + xmlPath);
                return;
            }

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            System.out.println("XML is valid against the schema");
        } catch (Exception e) {
            System.out.println("XML validation failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String xsdPath = "/istar_rl_schema.xsd";
        String xmlPath = "/figure1b.xml"; // or "/figure1b.xml"

        validateXMLSchema(xsdPath, xmlPath);
    }
}
