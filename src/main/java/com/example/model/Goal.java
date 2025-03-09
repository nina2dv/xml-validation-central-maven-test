package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "goal", namespace = "https://example.org/istar-t")
public class Goal extends DecompositionElement {
    @XmlAttribute(name = "name")
    private String id;

    @XmlAttribute(name = "episodeLength")
    private int runs;

    @XmlElement(name = "refinement", namespace = "https://example.org/istar-t")
    private Refinement refinement;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getRuns() {
        return runs;
    }
    public void setRuns(int runs) {
        this.runs = runs;
    }
    public Refinement getRefinement() {
        return refinement;
    }
    public void setRefinement(Refinement refinement) {
        this.refinement = refinement;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id='" + id + '\'' +
                ", runs=" + runs +
                ", refinement=" + refinement +
                ", preFormula=" + preFormula +
                ", nprFormula=" + nprFormula +
                ", children=" + children +
                '}';
    }
}
