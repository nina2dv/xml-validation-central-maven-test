package com.example.model;

public class SimpleFormula implements Formula {
    private String formula;

    public SimpleFormula() { }

    public SimpleFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "SimpleFormula{" + "formula='" + formula + '\'' + '}';
    }
}
