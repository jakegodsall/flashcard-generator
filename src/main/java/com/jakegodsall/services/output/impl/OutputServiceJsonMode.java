package com.jakegodsall.services.output.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakegodsall.models.flashcards.Flashcard;
import com.jakegodsall.services.output.OutputService;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A concrete implementation of the {@link OutputService} interface that writes
 * flashcard data to a JSON file. This class uses the Jackson library to
 * serialize a list of {@link Flashcard} objects into JSON format and save
 * them to a specified file path.
 */
@RequiredArgsConstructor
public class OutputServiceJsonMode implements OutputService {
    private final ObjectMapper objectMapper;


    @Override
    public String serialiseToOutputFormat(List<Flashcard> flashcards) throws JsonProcessingException {
        if (flashcards.size() == 0)
            return "";
        return objectMapper.writeValueAsString(flashcards);
    }

    /**
     * Writes a list of {@link Flashcard} objects to a JSON file at the specified file path.
     *
     * @param flashcards the list of flashcards to be written to the JSON file
     * @param filePath the path of the file where the JSON data will be written
     * @throws IOException if an I/O error occurs during writing to the file
     */
    @Override
    public void writeToFile(List<Flashcard> flashcards, String filePath) throws IOException {
        // Write the list of flashcards to the specified file as JSON
        objectMapper.writeValue(new File(filePath), flashcards);
    }
}