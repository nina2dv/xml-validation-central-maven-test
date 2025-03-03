package com.example.model;

public abstract class OperatorDecorator implements Formula {
    protected Formula left;
    protected Formula right;

    public OperatorDecorator(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }
    public Formula getLeft() {
        return left;
    }
    public void setLeft(Formula left) {
        this.left = left;
    }
    public Formula getRight() {
        return right;
    }
    public void setRight(Formula right) {
        this.right = right;
    }
}
