package com.example.model;

public class PlusOperator extends OperatorDecorator {
    public PlusOperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " + " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "PlusOperator{" + getFormula() + "}";
    }
}
