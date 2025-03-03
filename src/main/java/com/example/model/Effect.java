package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Effect {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private float probability;

    @XmlElement
    @XmlJavaTypeAdapter(FormulaAdapter.class)
    private Formula preFormula;

    @XmlElement
    @XmlJavaTypeAdapter(FormulaAdapter.class)
    private Formula nprFormula;

    @XmlAttribute
    private boolean satisfying = true;

    @XmlTransient
    private Task task;

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
    public Formula getPreFormula() {
        return preFormula;
    }
    public void setPreFormula(Formula preFormula) {
        this.preFormula = preFormula;
    }
    public Formula getNprFormula() {
        return nprFormula;
    }
    public void setNprFormula(Formula nprFormula) {
        this.nprFormula = nprFormula;
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
    public String toString() {
        return "Effect{" +
                "id='" + id + '\'' +
                ", probability=" + probability +
                ", preFormula=" + preFormula +
                ", nprFormula=" + nprFormula +
                ", satisfying=" + satisfying +
                '}';
    }
}
