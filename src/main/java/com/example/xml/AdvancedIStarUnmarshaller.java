package com.example.xml;

import com.example.objects.*;
import com.example.xml.XmlClasses.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

/**
 * Advanced implementation for unmarshalling iStar-T XML files to the object model
 */
public class AdvancedIStarUnmarshaller {

    public static void main(String[] args) {
        try {
            // Create the unmarshaller
            IStarUnmarshaller unmarshaller = new IStarUnmarshaller();

            // Unmarshal the XML file
            File xmlFile = new File("figure1a.xml");
            Model model = unmarshaller.unmarshalToModel(xmlFile);

            // Print some information about the model
            System.out.println("Model contains " + model.getActors().size() + " actors");
            for (Actor actor : model.getActors()) {
                System.out.println("Actor: " + actor.getName());
                System.out.println("  Goals: " + actor.getGoals().size());
                System.out.println("  Tasks: " + actor.getTasks().size());
                System.out.println("  Qualities: " + actor.getQualities().size());

                // Find and print the root goal
                Element root = actor.getRoot();
                if (root instanceof Goal) {
                    System.out.println("  Root Goal: " + root.getId());
                } else {
                    System.out.println("  No Root Goal found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inner class for XML unmarshalling
     */
    public static class IStarUnmarshaller {

        /**
         * Unmarshals an XML file to the domain model
         */
        public Model unmarshalToModel(File xmlFile) throws JAXBException {
            // Create JAXB context and unmarshaller
            JAXBContext context = JAXBContext.newInstance(ActorXml.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();

            // Unmarshal the XML file
            ActorXml actorXml = (ActorXml) jaxbUnmarshaller.unmarshal(xmlFile);

            // Create and populate the model
            Model model = new Model();

            // Convert actor XML to domain model
            Actor actor = convertActorToDomainModel(actorXml);

            // Add actor to model
            List<Actor> actors = new ArrayList<>();
            actors.add(actor);
            model.setActors(actors);

            // Create environment
            Environment environment = new Environment();
            model.setEnvironment(environment);

            // Process cross-references and link objects
            processReferences(model);

            return model;
        }

        /**
         * Converts an ActorXml to an Actor domain object
         */
        private Actor convertActorToDomainModel(ActorXml actorXml) {
            Actor actor = new Actor();
            actor.setName(actorXml.getName());

            // Maps to store objects by their IDs for later reference resolution
            Map<String, Atom> atoms = new HashMap<>();
            Map<String, Goal> goals = new HashMap<>();
            Map<String, Task> tasks = new HashMap<>();
            Map<String, Effect> effects = new HashMap<>();
            Map<String, Quality> qualities = new HashMap<>();

            // Process predicates
            List<Atom> predicateAtoms = new ArrayList<>();
            if (actorXml.getPredicates() != null) {
                for (PredicateXml predXml : actorXml.getPredicates().getPredicate()) {
                    Atom atom = new Atom();
                    String id = predXml.getValue().trim();
                    atom.setId(id);
                    atom.setTitleText(predXml.getDescription());
                    atoms.put(id, atom);
                    predicateAtoms.add(atom);
                }
            }

            // Process goals
            List<Goal> goalList = new ArrayList<>();
            if (actorXml.getGoals() != null) {
                for (GoalXml goalXml : actorXml.getGoals().getGoal()) {
                    Goal goal = new Goal();
                    String id = goalXml.getName();

                    // Set basic properties
                    Atom representation = new Atom();
                    representation.setId(id);
                    representation.setTitleText(goalXml.getDescription());
                    goal.setId(id);
                    goal.setRepresentation(representation);

                    // Process refinement if exists
                    if (goalXml.getRefinement() != null) {
                        RefinementXml refinementXml = goalXml.getRefinement();
                        goal.setDecompType(getDecompType(refinementXml.getType()));
                    } else {
                        // Default to terminal
                        goal.setDecompType(DecompType.TERM);
                    }

                    // Set episode runs if specified
                    if (goalXml.getEpisodeLength() != null) {
                        try {
                            goal.setRuns(Integer.parseInt(goalXml.getEpisodeLength()));
                        } catch (NumberFormatException e) {
                            // Default to 1 if not a valid number
                            goal.setRuns(1);
                        }
                    }

                    // Store in maps for later reference
                    goals.put(id, goal);
                    goalList.add(goal);
                }
            }

            // Process tasks
            List<Task> taskList = new ArrayList<>();
            if (actorXml.getTasks() != null) {
                for (TaskXml taskXml : actorXml.getTasks().getTask()) {
                    Task task = new Task();
                    String id = taskXml.getName();

                    // Set basic properties
                    Atom representation = new Atom();
                    representation.setId(id);
                    representation.setTitleText(taskXml.getDescription());
                    task.setId(id);
                    task.setRepresentation(representation);

                    // Process effects if they exist
                    List<Effect> taskEffects = new ArrayList<>();
                    if (taskXml.getEffectGroup() != null) {
                        for (EffectXml effectXml : taskXml.getEffectGroup().getEffect()) {
                            Effect effect = new Effect();
                            String effectId = effectXml.getName();

                            // Basic properties
                            Atom effectAtom = new Atom();
                            effectAtom.setId(effectId);
                            effectAtom.setTitleText(effectXml.getDescription());
                            effect.setId(effectId);
                            effect.setRepresentation(effectAtom);

                            // Set probability
                            effect.setProbability(effectXml.getProbability());

                            // Set satisfying flag
                            effect.setSatisfying(effectXml.getSatisfying() == null || effectXml.getSatisfying());

                            // Store effect in maps and lists
                            effects.put(effectId, effect);
                            taskEffects.add(effect);
                        }
                    }

                    task.setEffects(taskEffects);

                    // Store in maps for later reference
                    tasks.put(id, task);
                    taskList.add(task);
                }
            }

            // Process qualities
            List<Quality> qualityList = new ArrayList<>();
            if (actorXml.getQualities() != null) {
                for (QualityXml qualityXml : actorXml.getQualities().getQuality()) {
                    Quality quality = new Quality();
                    String id = qualityXml.getName();

                    // Set basic properties
                    Atom representation = new Atom();
                    representation.setId(id);
                    representation.setTitleText(qualityXml.getDescription());
                    quality.setId(id);
                    quality.setRepresentation(representation);

                    // Process formula - in a real implementation, we would convert the formula
                    // structure from XML to the corresponding Formula object hierarchy
                    if (qualityXml.getFormula() != null) {
                        // Here we would create a Formula object from the XML structure
                        // For now, we'll create a simple constant formula
                        quality.setValueFormula(Formula.createConstantFormula("Formula for " + id));
                    }

                    // Store in maps for later reference
                    qualities.put(id, quality);
                    qualityList.add(quality);
                }
            }

            // Set collections in the actor
            actor.setGoals(goalList);
            actor.setTasks(taskList);
            actor.setQualities(qualityList);

            // Second pass - link goal refinements
            if (actorXml.getGoals() != null) {
                for (GoalXml goalXml : actorXml.getGoals().getGoal()) {
                    if (goalXml.getRefinement() != null) {
                        Goal goal = goals.get(goalXml.getName());
                        RefinementXml refinementXml = goalXml.getRefinement();

                        // Process child goals
                        if (refinementXml.getChildGoal() != null) {
                            for (ChildRefXml childRef : refinementXml.getChildGoal()) {
                                Goal childGoal = goals.get(childRef.getRef());
                                if (childGoal != null) {
                                    if (goal.getDecompType() == DecompType.AND) {
                                        goal.addANDChild(childGoal);
                                    } else if (goal.getDecompType() == DecompType.OR) {
                                        goal.addORChild(childGoal);
                                    }
                                }
                            }
                        }

                        // Process child tasks
                        if (refinementXml.getChildTask() != null) {
                            for (ChildRefXml childRef : refinementXml.getChildTask()) {
                                Task childTask = tasks.get(childRef.getRef());
                                if (childTask != null) {
                                    if (goal.getDecompType() == DecompType.AND) {
                                        goal.addANDChild(childTask);
                                    } else if (goal.getDecompType() == DecompType.OR) {
                                        goal.addORChild(childTask);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return actor;
        }

        /**
         * Process references between objects after all objects have been created
         */
        private void processReferences(Model model) {
            // This method would handle relationships that couldn't be established
            // during the initial creation of objects, such as cross-references
            // between different elements

            // Example: link tasks to goals based on refinement relationships
            for (Actor actor : model.getActors()) {
                for (Goal goal : actor.getGoals()) {
                    if (goal instanceof DecompositionElement) {
                        DecompositionElement decomp = goal;
                        for (DecompositionElement child : decomp.getChildren()) {
                            // Set parent-child relationships
                            child.setParent(decomp);
                        }
                    }
                }

                // Process effect references
                for (Task task : actor.getTasks()) {
                    for (Effect effect : task.getEffects()) {
                        effect.setTask(task);
                    }
                }
            }
        }

        /**
         * Maps refinement type string to DecompType enum
         */
        private DecompType getDecompType(String type) {
            if ("AND".equalsIgnoreCase(type)) {
                return DecompType.AND;
            } else if ("OR".equalsIgnoreCase(type)) {
                return DecompType.OR;
            } else {
                return DecompType.TERM;
            }
        }
    }
}