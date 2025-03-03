package com.example.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class UnmarshalExample {
    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(Actor.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // XML file should be placed at src/main/resources/xml/figure1a_fixed.xml (adjust if needed)
            File xmlFile = new File("src/main/resources/xml/figure1a_fixed.xml");
            Actor actor = (Actor) unmarshaller.unmarshal(xmlFile);
            System.out.println("Unmarshalled Actor Object Model:");
            System.out.println(actor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
