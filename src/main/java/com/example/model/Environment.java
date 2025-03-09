package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "environment", namespace = "https://example.org/istar-t")
public class Environment {
    @XmlElement(name = "nonDecompElement", namespace = "https://example.org/istar-t")
    private List<NonDecompositionElement> nonDecompElements;

    public List<NonDecompositionElement> getNonDecompElements() {
        return nonDecompElements;
    }
    public void setNonDecompElements(List<NonDecompositionElement> nonDecompElements) {
        this.nonDecompElements = nonDecompElements;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "nonDecompElements=" + nonDecompElements +
                '}';
    }
}
