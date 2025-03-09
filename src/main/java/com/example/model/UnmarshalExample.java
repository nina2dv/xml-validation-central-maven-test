package com.example.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class UnmarshalExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context on Model (which uses ModelAdapter)
            JAXBContext context = JAXBContext.newInstance(Actor.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // XML file has <actor ...> as root tag.
            File xmlFile = new File("src/main/resources/xml/figure1a_fixed.xml");
            Actor model = (Actor) unmarshaller.unmarshal(xmlFile);
            System.out.println("Unmarshalled Model Object:");
            System.out.println(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
