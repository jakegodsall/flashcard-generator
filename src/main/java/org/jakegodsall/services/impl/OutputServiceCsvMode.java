package org.jakegodsall.services.impl;

import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.OutputService;

import java.io.IOException;
import java.util.List;

public class OutputServiceCsvMode implements OutputService {
    @Override
    public String generateOutput(List<Flashcard> flashcards) {
        return "";
    }

    @Override
    public void writeToFile(List<Flashcard> flashcards, String filePath) throws IOException {

    }
}
