package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quality", namespace = "https://example.org/istar-t")
public class Quality extends NonDecompositionElement {
    @XmlElement(name="formula")
    @XmlJavaTypeAdapter(FormulaAdapter.class)
    private Formula formula;

    @Override
    public Formula getFormula() {
        return formula;
    }
    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "Quality{" +
                "id='" + id + '\'' +
                ", representation=" + representation +
                ", formula=" + formula +
                '}';
    }
}
