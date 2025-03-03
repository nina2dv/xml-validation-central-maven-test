package com.example.model;

public class PreviousOperator implements Formula {
    private Formula operand;

    public PreviousOperator(Formula operand) {
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
        return "previous(" + operand.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "PreviousOperator{" + getFormula() + "}";
    }
}
