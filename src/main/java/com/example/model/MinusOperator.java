package com.example.model;

public class MinusOperator extends OperatorDecorator {
    public MinusOperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " - " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "MinusOperator{" + getFormula() + "}";
    }
}
