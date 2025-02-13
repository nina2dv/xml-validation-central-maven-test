package com.example.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a validation process, including errors and warnings.
 */
public class ValidationResult {
    private final List<Result> errors = new ArrayList<>();
    private final List<Result> warnings = new ArrayList<>();

    /**
     * Adds an error result.
     *
     * @param error the error {@code Result} to add.
     */
    public void addError(Result error) {
        errors.add(error);
    }

    /**
     * Adds an error result with the given details.
     *
     * @param test     the test condition that was evaluated.
     * @param location the location within the XML document.
     * @param message  the detailed error message.
     */
    public void addError(String test, String location, String message) {
        errors.add(new Result(test, location, message));
    }

    /**
     * Adds a warning result.
     *
     * @param warning the warning {@code Result} to add.
     */
    public void addWarning(Result warning) {
        warnings.add(warning);
    }

    /**
     * Adds a warning result with the given details.
     *
     * @param test     the test condition that was evaluated.
     * @param location the location within the XML document.
     * @param message  the detailed warning message.
     */
    public void addWarning(String test, String location, String message) {
        warnings.add(new Result(test, location, message));
    }

    /**
     * Returns an unmodifiable list of error results.
     *
     * @return the list of errors.
     */
    public List<Result> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * Returns an unmodifiable list of warning results.
     *
     * @return the list of warnings.
     */
    public List<Result> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }

    /**
     * Returns {@code true} if there are no errors.
     *
     * @return {@code true} if valid (no errors), {@code false} otherwise.
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
}
