package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChildRef {
    @XmlAttribute(name = "ref")
    private String ref;

    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }

    @Override
    public String toString() {
        return "ChildRef{" + "ref='" + ref + '\'' + '}';
    }
}
