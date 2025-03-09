package com.example.objects;

import java.util.List;

public class DecompositionElement extends Element{
    public List<DecompositionElement> children;
    public DecompType decompType;
    public Formula preFormula;
    public Formula nprFormula;

    public List<DecompositionElement> getSiblings(){
    }

    public boolean isSiblings(){

    }

    public List<DecompositionElement>getParent(){

    }

    public boolean isRoot(){

    }

    public void addANDChild(DecompositionElement child){

    }
    public void addORChild(DecompositionElement child){

    }

    public DecompType getDecompType(){

    }

}
