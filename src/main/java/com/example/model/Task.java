package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task", namespace = "https://example.org/istar-t")
public class Task extends DecompositionElement {
    @XmlAttribute(name = "name")
    private String id;

    @XmlAttribute(name = "description")
    private String description;

    @XmlElementWrapper(name = "effectGroup", namespace = "https://example.org/istar-t")
    @XmlElement(name = "effect", namespace = "https://example.org/istar-t")
    private List<Effect> effects = new ArrayList<>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Effect> getEffects() {
        return effects;
    }
    public void setEffects(List<Effect> effects) {
        this.effects = effects;
        if (effects != null) {
            for (Effect e : effects) {
                e.setTask(this);
            }
        }
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", preFormula=" + preFormula +
                ", nprFormula=" + nprFormula +
                ", effects=" + effects +
                ", children=" + children +
                '}';
    }
}
