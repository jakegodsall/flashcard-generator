package org.jakegodsall.services.impl;

import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.OutputService;

import java.io.IOException;
import java.util.List;

public class OutputServiceJsonMode implements OutputService {
    @Override
    public void writeToFile(List<Flashcard> flashcards, String filePath) throws IOException {

    }
}
