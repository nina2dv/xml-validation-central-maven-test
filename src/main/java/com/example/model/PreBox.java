package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "preBox", namespace = "https://example.org/istar-t")
public class PreBox implements Formula {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String description;

    @XmlAnyElement(lax = true)
    private Object expression;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    @XmlTransient
    public Object getExpression() { return expression; }
    public void setExpression(Object expression) { this.expression = expression; }

    @Override
    public String getFormula() {
        return expression != null ? expression.toString() : "";
    }

    @Override
    public String toString() {
        return "PreBox{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", formula='" + getFormula() + '\'' +
                '}';
    }
}
