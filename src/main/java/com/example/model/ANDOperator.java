package com.example.model;

public class ANDOperator extends OperatorDecorator {
    public ANDOperator(Formula left, Formula right) {
        super(left, right);
    }
    @Override
    public String getFormula() {
        return "(" + left.getFormula() + " AND " + right.getFormula() + ")";
    }

    @Override
    public String toString() {
        return "ANDOperator{" + getFormula() + "}";
    }
}
