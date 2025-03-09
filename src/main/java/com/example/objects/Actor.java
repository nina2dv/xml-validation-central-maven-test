package com.example.objects;

import java.util.List;

public class Actor extends Element{
    private List<Goal>  goals;
    private List<Task>  tasks;
    private List<Effect>  directEffects;
    private List<Quality>  qualities;

    public List<Goal> getGoals() {
        return goals;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Effect> getDirectEffects() {
        return directEffects;
    }

    public List<Quality> getQualities() {
        return qualities;
    }

    public Element getRoot(){

    }
}
