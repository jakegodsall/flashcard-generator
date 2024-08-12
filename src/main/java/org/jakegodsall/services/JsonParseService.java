package org.jakegodsall.services;

import com.fasterxml.jackson.core.JsonParseException;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;

import java.io.IOException;
import java.util.List;

/**
 * Interface for JSON parser to handle parsing of JSON responses.
 */
public interface JsonParseService {
    /**
     * Parses the list of model names from the given JSON string.
     *
     * @param json the JSON string to parse.
     * @return a list of model names.
     * @throws IOException if an error occurs while parsing the JSON.
     */
    List<String> parseModels(String json) throws IOException;

    /**
     * Parses the JSON response body into a {@link WordFlashcard} object.
     *
     * @param responseBody The JSON response body from the API.
     * @return A {@link WordFlashcard} object containing the parsed data.
     * @throws JsonParseException If the parsing fails due to invalid JSON or unexpected format.
     */
    WordFlashcard parseWordFlashcard(String responseBody) throws JsonParseException;

    /**
     * Parses the JSON response body into a {@link SentenceFlashcard} object.
     *
     * @param responseBody The JSON response body from the API.
     * @return A {@link SentenceFlashcard} object containing the parsed data.
     * @throws JsonParseException If the parsing fails due to invalid JSON or unexpected format.
     */
    SentenceFlashcard parseSentenceFlashcard(String responseBody) throws JsonParseException;
}
