package org.jakegodsall.services.input.impl.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.flashcard.FlashcardService;
import org.jakegodsall.services.input.impl.InputService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InputServicePlainTextFileMode implements InputService {

    private final BufferedReader consoleReader;
    private final FlashcardService flashcardService;

    @Override
    public List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();
        System.out.println("Enter the absolute path of the file:");
        String fileName = consoleReader.readLine();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (flashcardType == FlashcardType.WORD)
                    flashcards.add(flashcardService.getWordFlashcard(line, chosenLanguage, selectedOptions));
                if (flashcardType == FlashcardType.SENTENCE)
                    flashcards.add(flashcardService.getSentenceFlashcard(line, chosenLanguage, selectedOptions));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return flashcards;
    }
}
