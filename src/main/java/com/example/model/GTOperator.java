package com.example.model;

public class GTOperator extends OperatorDecorator {
    public GTOperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " > " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "GTOperator{" + getFormula() + "}";
    }
}
