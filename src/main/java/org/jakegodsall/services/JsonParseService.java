package org.jakegodsall.services;

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
     * Parses a sentence from the given JSON string.
     *
     * @param json the JSON string to parse.
     * @return the parsed sentence.
     * @throws IOException if an error occurs while parsing the JSON.
     */
    String parseSentence(String json) throws IOException;
}