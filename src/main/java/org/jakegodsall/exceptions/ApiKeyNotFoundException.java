package org.jakegodsall.exceptions;

/**
 * Exception thrown when the API key is not found in the configuration file.
 */
public class ApiKeyNotFoundException extends Exception {
    public ApiKeyNotFoundException(String message) {
        super(message);
    }
}
