package com.example.model;

public class OROperator extends OperatorDecorator {
    public OROperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " OR " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "OROperator{" + getFormula() + "}";
    }
}
