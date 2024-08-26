package org.jakegodsall.services.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.InputService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class InputServiceCommaSeparatedStringMode implements InputService {

    private final BufferedReader bufferedReader;
    private final FlashcardService flashcardService;

    @Override
    public List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException {
        // Initialise list of flashcards
        List<Flashcard> flashcards = new ArrayList<>();

        // Get the String from the user
        System.out.println("Enter the words as a String of comma-separated values (or 'exit' to cancel):");
        String wordString = bufferedReader.readLine().trim();

        // Handle empty input
        if (wordString.isEmpty()) {
            System.err.println("Error: Input cannot be empty.");
            return flashcards;
        }
        // Handle 'exit'
        if (wordString.equalsIgnoreCase("exit")) {
            System.out.println("Goodbye");
            return flashcards;
        }

        // Split the provided string into individual words
        List<String> wordList = Arrays.stream(wordString.split(","))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .toList();

        // Generate the flashcards from the words and add to List
        for (int i = 0; i < wordList.size(); i++) {
            if (flashcardType == FlashcardType.SENTENCE) {
                flashcards.add(flashcardService.getSentenceFlashcard(wordList.get(i), chosenLanguage, selectedOptions));
            } else if (flashcardType == FlashcardType.WORD) {
                flashcards.add(flashcardService.getWordFlashcard(wordList.get(i), chosenLanguage, selectedOptions));
            }
            System.out.println("Processed word " + (i + 1) + " of " + wordList.size() + ".");
        }

        // Return the List
        return flashcards;
    }
}
