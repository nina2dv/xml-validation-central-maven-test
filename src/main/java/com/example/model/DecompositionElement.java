package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DecompositionElement extends Element {
    protected List<DecompositionElement> children = new ArrayList<>();
    protected DecompType decompType; // AND, OR, TERM

    @XmlJavaTypeAdapter(FormulaAdapter.class)
    protected Formula preFormula;
    @XmlJavaTypeAdapter(FormulaAdapter.class)
    protected Formula nprFormula;

    @XmlTransient
    protected DecompositionElement parent;

    public List<DecompositionElement> getChildren() {
        return children;
    }
    public void addANDChild(DecompositionElement child) {
        child.decompType = DecompType.AND;
        child.parent = this;
        children.add(child);
    }
    public void addORChild(DecompositionElement child) {
        child.decompType = DecompType.OR;
        child.parent = this;
        children.add(child);
    }
    public DecompType getDecompType() {
        return decompType;
    }
    public Formula getPreFormula() { return preFormula; }
    public Formula getNprFormula() { return nprFormula; }
    public DecompositionElement[] getSiblings() {
        if (parent == null) return new DecompositionElement[0];
        return parent.children.stream().filter(child -> child != this).toArray(DecompositionElement[]::new);
    }
    public boolean isSibling(DecompositionElement other) {
        return parent != null && parent.children.contains(other);
    }
    public DecompositionElement getParent() { return parent; }
    public abstract boolean isRoot();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
                .append("{id='").append(id).append('\'')
                .append(", decompType=").append(decompType)
                .append(", preFormula=").append(preFormula)
                .append(", nprFormula=").append(nprFormula);
        if (!children.isEmpty()) {
            sb.append(", children=").append(children);
        }
        sb.append('}');
        return sb.toString();
    }
}
