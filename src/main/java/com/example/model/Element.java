package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Element {
    protected String id;
    protected Atom representation; // an Atom representing this element

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Atom getRepresentation() {
        return representation;
    }
    public void setRepresentation(Atom representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id='" + id + '\'' +
                ", representation=" + representation +
                '}';
    }
}
