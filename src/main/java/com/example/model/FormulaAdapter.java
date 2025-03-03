package com.example.model;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class FormulaAdapter extends XmlAdapter<String, Formula> {
    @Override
    public Formula unmarshal(String v) throws Exception {
        return new SimpleFormula(v);
    }

    @Override
    public String marshal(Formula v) throws Exception {
        return v.getFormula();
    }
}
