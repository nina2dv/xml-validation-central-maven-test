package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Effect extends DecompositionElement {
    @XmlAttribute
    private String id;

    @XmlAttribute
    private float probability;

    @XmlAttribute
    private boolean satisfying = true;

    // Task back-reference (not marshalled)
    @XmlTransient
    private Task task;

    // Note: preFormula and nprFormula come from DecompositionElement

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public float getProbability() {
        return probability;
    }
    public void setProbability(float probability) {
        this.probability = probability;
    }
    public boolean isSatisfying() {
        return satisfying;
    }
    public void setSatisfying(boolean satisfying) {
        this.satisfying = satisfying;
    }
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "id='" + id + '\'' +
                ", probability=" + probability +
                ", satisfying=" + satisfying +
                ", preFormula=" + preFormula +
                ", nprFormula=" + nprFormula +
                '}';
    }
}
