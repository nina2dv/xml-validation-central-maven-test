package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "effectGroup", namespace = "https://example.org/istar-t")
public class EffectGroup {
    @XmlElement(name = "effect", namespace = "https://example.org/istar-t")
    private List<Effect> effects;

    public List<Effect> getEffects() { return effects; }
    public void setEffects(List<Effect> effects) { this.effects = effects; }

    @Override
    public String toString() {
        return "EffectGroup{" + "effects=" + effects + '}';
    }
}
