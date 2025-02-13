package com.example.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom error handler that collects validation warnings and errors during XSD validation.
 */
public class XsdErrorHandler implements ErrorHandler {
    private final List<SAXParseException> warnings = new ArrayList<>();
    private final List<SAXParseException> errors = new ArrayList<>();

    /**
     * Returns the list of collected warnings.
     *
     * @return a list of {@code SAXParseException} warnings.
     */
    public List<SAXParseException> getWarnings() {
        return warnings;
    }

    /**
     * Returns the list of collected errors.
     *
     * @return a list of {@code SAXParseException} errors.
     */
    public List<SAXParseException> getErrors() {
        return errors;
    }

    /**
     * Handles warning events by collecting them.
     *
     * @param exception the warning exception.
     * @throws SAXException never thrown.
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException {
        warnings.add(exception);
    }

    /**
     * Handles error events by collecting them.
     *
     * @param exception the error exception.
     * @throws SAXException never thrown.
     */
    @Override
    public void error(SAXParseException exception) throws SAXException {
        errors.add(exception);
    }

    /**
     * Handles fatal error events by collecting them.
     *
     * @param exception the fatal error exception.
     * @throws SAXException never thrown.
     */
    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        errors.add(exception);
    }
}
