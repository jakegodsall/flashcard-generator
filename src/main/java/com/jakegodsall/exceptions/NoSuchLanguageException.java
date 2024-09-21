package com.jakegodsall.exceptions;

import java.util.NoSuchElementException;

/**
 * Exception thrown when the language does not exist in the language configuration file.
 */
public class NoSuchLanguageException extends NoSuchElementException {
    public NoSuchLanguageException(String message) { super(message); }
}
