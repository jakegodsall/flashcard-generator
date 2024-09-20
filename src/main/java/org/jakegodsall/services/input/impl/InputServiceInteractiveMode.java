package org.jakegodsall.services.input.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.services.flashcard.FlashcardService;
import org.jakegodsall.services.input.InputService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class InputServiceInteractiveMode implements InputService {

    private final BufferedReader bufferedReader;
    private final FlashcardService flashcardService;

    @Override
    public List<String> getInput() throws IOException {
       return Collections.singletonList(getWordFromUser(bufferedReader));
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word (or -1 to exit interactive mode):");
        return bufferedReader.readLine().trim();
    }
}
