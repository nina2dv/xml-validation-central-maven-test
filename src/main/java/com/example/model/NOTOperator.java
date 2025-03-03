package com.example.model;

public class NOTOperator implements Formula {
    private Formula operand;

    public NOTOperator(Formula operand) {
        this.operand = operand;
    }

    public Formula getOperand() {
        return operand;
    }

    public void setOperand(Formula operand) {
        this.operand = operand;
    }

    @Override
    public String getFormula() {
        return "(NOT " + operand.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "NOTOperator{" + getFormula() + "}";
    }
}
