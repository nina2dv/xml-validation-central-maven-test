package com.example;

import com.example.objects.*;
import com.example.xml.EnhancedIStarUnmarshaller;
import jakarta.xml.bind.JAXBException;

import com.example.xml.IStarUnmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Enhanced application that demonstrates the unmarshal process
 * including support for Quality, Condition (PreBox), and IndirectEffect elements
 */
public class EnhancedIStarTApplication {

    public static void main(String[] args) {
        try (InputStream xmlStream = IStarTApplication.class.getResourceAsStream("/xml/figure1a_fixed.xml")) {
            if (xmlStream == null) {
                System.err.println("Error: XML resource '/xml/figure1a_fixed.xml' not found in the classpath.");
                System.exit(1);
            }
            System.out.println("Loading XML file from resources: /xml/figure1a_fixed.xml");

            // Create unmarshaller and directly unmarshal the XML using the InputStream
            IStarUnmarshaller unmarshaller = new IStarUnmarshaller();
            Model model = unmarshaller.unmarshalToModel(xmlStream);

            // Display model information
            printModelInformation(model);

        } catch (JAXBException e) {
            System.err.println("Error unmarshalling XML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Print information about the model structure
     */
    private static void printModelInformation(Model model) {
        System.out.println("=== iStar-T Model Information ===");
        System.out.println("Number of actors: " + model.getActors().size());

        // Process each actor
        int actorCount = 1;
        for (Actor actor : model.getActors()) {
            System.out.println("\nActor " + actorCount + ": " + actor.getName());

            // Goals
            System.out.println("  Goals (" + actor.getGoals().size() + "):");
            for (Goal goal : actor.getGoals()) {
                printGoalInfo(goal, 2);
            }

            // Tasks
            System.out.println("  Tasks (" + actor.getTasks().size() + "):");
            for (Task task : actor.getTasks()) {
                printTaskInfo(task, 2);
            }

            // Qualities
            System.out.println("  Qualities (" + actor.getQualities().size() + "):");
            for (Quality quality : actor.getQualities()) {
                String titleText = quality.getAtom() != null && quality.getAtom().getTitleText() != null ?
                        " (" + quality.getAtom().getTitleText() + ")" : "";
                Formula formula = quality.getValueFormula() != null ? quality.getValueFormula() : null;
                String formulaText = formula != null ? " [Formula: " + formula.getFormula() + "]" : "";

                System.out.println("    - " + quality.getId() + titleText +
                        (quality.isRoot() ? " [ROOT]" : "") + formulaText);
            }

            actorCount++;
        }

        // Print environment information
        System.out.println("\nEnvironment Information:");
        if (model.getEnvironment() != null) {
            List<NonDecompositionElement> elements = model.getEnvironment().getNonDecompElements();
            System.out.println("  Non-decomposition elements: " +
                    (elements != null ? elements.size() : 0));

            if (elements != null && !elements.isEmpty()) {
                int conditionCount = 0;
                int indirectEffectCount = 0;
                int effectCount = 0;

                for (NonDecompositionElement element : elements) {
                    if (element instanceof Condition) {
                        conditionCount++;
                    } else if (element instanceof IndirectEffect) {
                        indirectEffectCount++;
                    } else if (element instanceof Effect) {
                        effectCount++;
                    }
                }

                System.out.println("    - Conditions (PreBoxes): " + conditionCount);
                System.out.println("    - Indirect Effects: " + indirectEffectCount);
                System.out.println("    - Effects: " + effectCount);

                // Print detailed information about Conditions (PreBoxes)
                if (conditionCount > 0) {
                    System.out.println("\n  Conditions (PreBoxes):");
                    for (NonDecompositionElement element : elements) {
                        if (element instanceof Condition) {
                            Condition condition = (Condition) element;
                            String titleText = condition.getAtom() != null && condition.getAtom().getTitleText() != null ?
                                    " (" + condition.getAtom().getTitleText() + ")" : "";
                            Formula formula = condition.getValueFormula() != null ? condition.getValueFormula() : null;
                            String formulaText = formula != null ? " [Formula: " + formula.getFormula() + "]" : "";

                            System.out.println("    - " + condition.getId() + titleText + formulaText);
                        }
                    }
                }

                // Print detailed information about IndirectEffects
                if (indirectEffectCount > 0) {
                    System.out.println("\n  Indirect Effects:");
                    for (NonDecompositionElement element : elements) {
                        if (element instanceof IndirectEffect) {
                            IndirectEffect indirectEffect = (IndirectEffect) element;
                            String titleText = indirectEffect.getAtom() != null && indirectEffect.getAtom().getTitleText() != null ?
                                    " (" + indirectEffect.getAtom().getTitleText() + ")" : "";
                            Formula formula = indirectEffect.getValueFormula() != null ? indirectEffect.getValueFormula() : null;
                            String formulaText = formula != null ? " [Formula: " + formula.getFormula() + "]" : "";

                            System.out.println("    - " + indirectEffect.getId() + titleText +
                                    (indirectEffect.isExported() ? " [Exported]" : "") + formulaText);
                        }
                    }
                }
            }
        } else {
            System.out.println("  No environment information available");
        }
    }

    /**
     * Print information about a goal, with proper indentation
     */
    private static void printGoalInfo(Goal goal, int indentLevel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("  ");
        }

        String titleText = goal.getAtom() != null && goal.getAtom().getTitleText() != null ?
                " (" + goal.getAtom().getTitleText() + ")" : "";

        System.out.println(indent + "- " + goal.getId() + titleText +
                " [Type: " + goal.getDecompType() + "]");

        // Print child elements if this is a decomposition element
        if (goal.getChildren() != null && !goal.getChildren().isEmpty()) {
            System.out.println(indent + "  Children:");
            for (DecompositionElement child : goal.getChildren()) {
                if (child instanceof Goal) {
                    printGoalInfo((Goal) child, indentLevel + 2);
                } else if (child instanceof Task) {
                    printTaskInfo((Task) child, indentLevel + 2);
                } else {
                    System.out.println(indent + "    - " + child.getId());
                }
            }
        }
    }

    /**
     * Print information about a task, with proper indentation
     */
    private static void printTaskInfo(Task task, int indentLevel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("  ");
        }

        String titleText = task.getAtom() != null && task.getAtom().getTitleText() != null ?
                " (" + task.getAtom().getTitleText() + ")" : "";

        System.out.println(indent + "- " + task.getId() + titleText +
                " [Deterministic: " + task.isDeterministic() + "]");

        // Print effects
        if (task.getEffects() != null && !task.getEffects().isEmpty()) {
            System.out.println(indent + "  Effects:");
            for (Effect effect : task.getEffects()) {
                System.out.println(indent + "    - " + effect.getId() +
                        " (Probability: " + effect.getProbability() +
                        ", Satisfying: " + effect.isSatisfying() + ")");
            }
        }
    }
}