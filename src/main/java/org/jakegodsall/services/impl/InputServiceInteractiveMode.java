package org.jakegodsall.services.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.InputService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InputServiceInteractiveMode implements InputService {

    private final BufferedReader bufferedReader;
    private final FlashcardService flashcardService;

    @Override
    public List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException {
        // Create a Set/List of flashcards for storing as users give word
        List<Flashcard> flashcards = new ArrayList<>();

        // Loop through words from user until -1 provided.
        String word;
        while (!(word = getWordFromUser(bufferedReader)).equals("-1")) {
            Flashcard flashcard = new SentenceFlashcard();
            // add the flashcard
            if (flashcardType == FlashcardType.SENTENCE) {
                flashcard = flashcardService.getSentenceFlashcard(word, chosenLanguage, selectedOptions);
            } else if (flashcardType == FlashcardType.WORD) {
                flashcard = flashcardService.getWordFlashcard(word, chosenLanguage, selectedOptions);
            } else {
                System.err.println("Unknown flashcard type.");
                continue;
            }
            flashcards.add(flashcard);
            System.out.println(flashcard);
        }

        // return list of flashcards
        return flashcards;
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word (or -1 to exit interactive mode):");
        return bufferedReader.readLine().trim();
    }
}
