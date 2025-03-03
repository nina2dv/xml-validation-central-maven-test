package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Atom {
    private String id;
    private String titleText;
    private String titleHTMLText;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitleText() {
        return titleText;
    }
    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }
    public String getTitleHTMLText() {
        return titleHTMLText;
    }
    public void setTitleHTMLText(String titleHTMLText) {
        this.titleHTMLText = titleHTMLText;
    }

    public String getFormula() {
        return titleText; // simplified representation
    }
    public String getAtomRepresentation() {
        return titleText;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "id='" + id + '\'' +
                ", titleText='" + titleText + '\'' +
                ", titleHTMLText='" + titleHTMLText + '\'' +
                '}';
    }
}
