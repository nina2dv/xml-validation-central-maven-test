package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "actor", namespace = "https://example.org/istar-t")
@XmlJavaTypeAdapter(ModelAdapter.class)
public class Model {
    @XmlElement(name = "actor", namespace = "https://example.org/istar-t")
    private List<Actor> actors;

    @XmlElement(name = "environment", namespace = "https://example.org/istar-t")
    private Environment environment;

    public List<Actor> getActors() {
        return actors;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    public Environment getEnvironment() {
        return environment;
    }
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "Model{" +
                "actors=" + actors +
                ", environment=" + environment +
                '}';
    }
}
