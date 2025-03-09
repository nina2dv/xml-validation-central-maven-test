package com.example.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "actor", namespace = "https://example.org/istar-t")
public class Actor extends Element {
    @XmlAttribute(name = "name")
    private String name;

    @XmlElementWrapper(name = "predicates", namespace = "https://example.org/istar-t")
    @XmlElement(name = "predicate", namespace = "https://example.org/istar-t")
    private List<Predicate> predicates;

    @XmlElementWrapper(name = "preBoxes", namespace = "https://example.org/istar-t")
    @XmlElement(name = "preBox", namespace = "https://example.org/istar-t")
    private List<PreBox> preBoxes;

    @XmlElementWrapper(name = "qualities", namespace = "https://example.org/istar-t")
    @XmlElement(name = "quality", namespace = "https://example.org/istar-t")
    private List<Quality> qualities;

    @XmlElementWrapper(name = "goals", namespace = "https://example.org/istar-t")
    @XmlElement(name = "goal", namespace = "https://example.org/istar-t")
    private List<Goal> goals;

    @XmlElementWrapper(name = "tasks", namespace = "https://example.org/istar-t")
    @XmlElement(name = "task", namespace = "https://example.org/istar-t")
    private List<Task> tasks;

    @XmlElementWrapper(name = "indirectEffects", namespace = "https://example.org/istar-t")
    @XmlElement(name = "indirectEffect", namespace = "https://example.org/istar-t")
    private List<IndirectEffect> indirectEffects;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Predicate> getPredicates() { return predicates; }
    public void setPredicates(List<Predicate> predicates) { this.predicates = predicates; }
    public List<PreBox> getPreBoxes() { return preBoxes; }
    public void setPreBoxes(List<PreBox> preBoxes) { this.preBoxes = preBoxes; }
    public List<Quality> getQualities() { return qualities; }
    public void setQualities(List<Quality> qualities) { this.qualities = qualities; }
    public List<Goal> getGoals() { return goals; }
    public void setGoals(List<Goal> goals) { this.goals = goals; }
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<IndirectEffect> getIndirectEffects() { return indirectEffects; }
    public void setIndirectEffects(List<IndirectEffect> indirectEffects) { this.indirectEffects = indirectEffects; }
    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                "\n------------------\n" +
                ", predicates=" + predicates +
                "\n------------------\n" +

                ", preBoxes=" + preBoxes +
                "\n------------------\n" +

                ", qualities=" + qualities +
                "\n------------------\n" +

                ", goals=" + goals +
                "\n------------------\n" +

                ", tasks=" + tasks +
                "\n------------------\n" +

                ", indirectEffects=" + indirectEffects +
                '}';
    }
}
