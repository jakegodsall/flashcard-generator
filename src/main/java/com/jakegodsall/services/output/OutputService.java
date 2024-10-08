package com.jakegodsall.services.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jakegodsall.models.flashcards.Flashcard;

import java.io.IOException;
import java.util.List;

/**
 * The {@code OutputService} interface defines the contract for services that
 * handle the output of flashcards. Implementations of this interface are
 * responsible for generating a formatted output from a list of {@link Flashcard}
 * objects and optionally writing this output to a file.
 *
 * <p>This interface supports different output formats, such as CSV, JSON, etc.,
 * and provides methods for both generating the output as a {@code String} and
 * saving it to a file.</p>
 */
public interface OutputService {

    /**
     * Serialises {@code List} of {@link Flashcard} objects to a {@code String}
     * with a specific format according to the requirements of the implementing class.
     *
     * @param flashcards a {@code List} of {@link Flashcard} objects to be serialised.
     * @throws JsonProcessingException if there is an issue with the processing of {@code flashcards} to JSON.
     */
    String serialiseToOutputFormat(List<Flashcard> flashcards) throws JsonProcessingException;

    /**
     * Writes the formatted output of the provided list of {@link Flashcard}
     * objects to a specified file. The exact format of the output (e.g., CSV,
     * JSON) is determined by the specific implementation of this interface.
     *
     * @param flashcards a {@code List} of {@link Flashcard} objects to be
     *                   formatted and written to the file.
     * @param filePath   the path to the file where the output should be saved.
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    void writeToFile(List<Flashcard> flashcards, String filePath) throws IOException;
}
