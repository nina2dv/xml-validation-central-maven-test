package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class NonDecompositionElement extends Element {
    @XmlJavaTypeAdapter(FormulaAdapter.class)
    private Formula valueFormula;
    private Atom atom;
    private boolean previous;

    public Formula getFormula() {
        return valueFormula;
    }
    public void setFormula(Formula valueFormula) {
        this.valueFormula = valueFormula;
    }
    public Atom getAtom() {
        return atom;
    }
    public void setAtom(Atom atom) {
        this.atom = atom;
    }
    public boolean isPrevious() {
        return previous;
    }
    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "NonDecompositionElement{" +
                "id='" + id + '\'' +
                ", representation=" + representation +
                ", valueFormula=" + valueFormula +
                ", atom=" + atom +
                ", previous=" + previous +
                '}';
    }
}
