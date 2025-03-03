package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Predicate {
    @XmlAttribute
    private boolean primitive;
    @XmlAttribute
    private boolean init;
    @XmlAttribute
    private boolean exported;
    @XmlAttribute
    private String description;

    @XmlValue
    private String identifier;

    public boolean isPrimitive() { return primitive; }
    public void setPrimitive(boolean primitive) { this.primitive = primitive; }
    public boolean isInit() { return init; }
    public void setInit(boolean init) { this.init = init; }
    public boolean isExported() { return exported; }
    public void setExported(boolean exported) { this.exported = exported; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIdentifier() { return identifier != null ? identifier.trim() : null; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    @Override
    public String toString() {
        return "Predicate{" +
                "primitive=" + primitive +
                ", init=" + init +
                ", exported=" + exported +
                ", description='" + description + '\'' +
                ", identifier='" + getIdentifier() + '\'' +
                '}';
    }
}
