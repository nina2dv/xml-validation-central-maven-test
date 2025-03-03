package com.example.model;

public class MultiplyOperator extends OperatorDecorator {
    public MultiplyOperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " * " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "MultiplyOperator{" + getFormula() + "}";
    }
}
