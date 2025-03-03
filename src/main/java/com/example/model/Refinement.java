package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "refinement", namespace = "https://example.org/istar-t")
public class Refinement {
    @XmlAttribute
    private String type; // AND or OR

    @XmlElement(name = "childGoal", namespace = "https://example.org/istar-t")
    private List<ChildRef> childGoals;

    @XmlElement(name = "childTask", namespace = "https://example.org/istar-t")
    private List<ChildRef> childTasks;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<ChildRef> getChildGoals() { return childGoals; }
    public void setChildGoals(List<ChildRef> childGoals) { this.childGoals = childGoals; }
    public List<ChildRef> getChildTasks() { return childTasks; }
    public void setChildTasks(List<ChildRef> childTasks) { this.childTasks = childTasks; }

    @Override
    public String toString() {
        return "Refinement{" +
                "type='" + type + '\'' +
                ", childGoals=" + childGoals +
                ", childTasks=" + childTasks +
                '}';
    }
}
