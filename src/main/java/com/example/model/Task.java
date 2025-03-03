package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task", namespace = "https://example.org/istar-t")
public class Task {
    @XmlAttribute(name = "name")
    private String id;

    @XmlAttribute(name = "description")
    private String description;

    private List<Effect> effects = new ArrayList<>();

    @XmlElement(name = "effectGroup", namespace = "https://example.org/istar-t")
    private EffectGroup effectGroup;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public EffectGroup getEffectGroup() { return effectGroup; }
    public void setEffectGroup(EffectGroup effectGroup) {
        this.effectGroup = effectGroup;
        if (effectGroup != null && effectGroup.getEffects() != null) {
            for (Effect e : effectGroup.getEffects()) {
                e.setTask(this);
                effects.add(e);
            }
        }
    }
    public List<Effect> getEffects() { return effects; }

    public boolean isDeterministic() {
        return effects.stream().allMatch(e -> e.getProbability() == 1.0f);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", effectGroup=" + effectGroup +
                ", effects=" + effects +
                '}';
    }
}
