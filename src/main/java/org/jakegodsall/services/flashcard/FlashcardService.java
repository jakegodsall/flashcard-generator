package org.jakegodsall.services.flashcard;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interface for FlashcardService to define methods for interacting with language models.
 */
public interface FlashcardService {

    /**
     * Retrieves a list of available models from the OpenAI API.
     *
     * @return a list of model names.
     */
    List<String> getAvailableModels();

    /**
     * Generates a flashcard for the specified target word.
     *
     * @param targetWord the word for which the flashcard is generated
     * @param flashcardType the type of the flashcard (e.g., multiple choice, fill-in-the-blank)
     * @param language the language of the flashcard content
     * @param options additional options to customize flashcard generation
     * @return the generated flashcard
     */
    Flashcard generateFlashcard(String targetWord, FlashcardType flashcardType, Language language, Options options);

    /**
     * Generates flashcards sequentially for a list of target words.
     *
     * @param targetWords the list of words for which flashcards are generated
     * @param flashcardType the type of the flashcards (e.g., multiple choice, fill-in-the-blank)
     * @param language the language of the flashcards
     * @param options additional options to customize flashcard generation
     * @return a list of generated flashcards
     */
    List<Flashcard> generateFlashcardsSequentially(List<String> targetWords, FlashcardType flashcardType, Language language, Options options);

    /**
     * Generates flashcards concurrently for a list of target words.
     *
     * @param targetWords the list of words for which flashcards are generated
     * @param language the language of the flashcards
     * @param options additional options to customize flashcard generation
     * @return a list of generated flashcards
     * @throws InterruptedException if the execution is interrupted during concurrent generation
     * @throws ExecutionException if an exception occurs during concurrent execution
     */
    List<Flashcard> generateFlashcardsConcurrently(List<String> targetWords, Language language, Options options) throws InterruptedException, ExecutionException;
}