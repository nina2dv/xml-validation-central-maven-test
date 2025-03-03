package com.example.model;

public class NonDecompositionElement extends Element {
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
}
