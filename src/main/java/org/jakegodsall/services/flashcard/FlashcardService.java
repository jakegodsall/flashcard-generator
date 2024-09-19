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

    Flashcard generateFlashcard(String targetWord, FlashcardType flashcardType, Language language, Options options);

    List<Flashcard> generateFlashcardsConcurrently(List<String> words, Language language, Options options) throws InterruptedException, ExecutionException;
}