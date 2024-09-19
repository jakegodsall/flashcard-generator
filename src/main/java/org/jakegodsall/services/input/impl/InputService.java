package org.jakegodsall.services.input.impl;

import java.io.IOException;
import java.util.List;

/**
 * The {@code InputService} interface defines the contract for services that handle
 * input retrieval from the user. Implementations of this interface should provide
 * a method to capture input from the user and return it as a list of words.
 *
 * <p>This interface is designed to focus on the basic functionality of retrieving
 * input as words from a user, which can then be passed into further processing
 * pipelines. It does not concern itself with flashcards or complex data structures
 * beyond capturing user-provided words.</p>
 */
public interface InputService {
    /**
     * Retrieves a list of words entered by the user. The implementation of this method
     * should handle capturing the user's input and converting it into a {@code List<String>}.
     *
     * <p>This method is expected to only handle the retrieval of words, leaving any further
     * processing or conversion to other components in the pipeline.</p>
     *
     * @return a {@code List<String>} representing the words input by the user.
     * @throws IOException if an I/O error occurs during input retrieval.
     */
    List<String> getInput() throws IOException;
}