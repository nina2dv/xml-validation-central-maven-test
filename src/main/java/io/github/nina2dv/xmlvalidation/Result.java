package io.github.nina2dv.xmlvalidation;

/**
 * Represents a validation outcome (error or warning) with details.
 */
public class Result {
    private final String test;
    private final String location;
    private final String message;

    /**
     * Constructs a new {@code Result}.
     *
     * @param test     the test condition that was evaluated.
     * @param location the location within the XML document.
     * @param message  the detailed message.
     */
    public Result(String test, String location, String message) {
        this.test = test;
        this.location = location;
        this.message = message;
    }

    /**
     * Constructs a new {@code Result} by copying an existing {@code Result}.
     *
     * @param result the {@code Result} to copy.
     */
    public Result(Result result) {
        this.test = result.getTest();
        this.location = result.getLocation();
        this.message = result.getMessage();
    }

    /**
     * Returns the test condition that was evaluated.
     *
     * @return the test condition.
     */
    public String getTest() {
        return test;
    }

    /**
     * Returns the location within the XML document.
     *
     * @return the location string.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the detailed message.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a string representation of this {@code Result}.
     *
     * @return a string containing the test, location, and message.
     */
    @Override
    public String toString() {
        return "Test: " + test + " | Location: " + location + " | Message: " + message;
    }
}
