package org.jakegodsall.services.flashcard;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;

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
     * Generates a flashcard for a specific word in a target language.
     *
     * @param targetWord The word in the target language for which the flashcard should be generated.
     * @param language   The language object that contains information about the target language.
     * @param options    Additional options for customizing the flashcard generation.
     * @return A {@link WordFlashcard} object containing the native word, the target word, and an example sentence using the target word.
     */
    Flashcard getWordFlashcard(String targetWord, Language language, Options options);

    /**
     * Generates a flashcard for a specific word in a target language, using full sentences.
     *
     * @param targetWord The word in the target language for which the sentence flashcard should be generated.
     * @param language   The language object that contains information about the target language.
     * @param options    Additional options for customizing the flashcard generation.
     * @return A {@link SentenceFlashcard} object containing a native sentence and the corresponding sentence in the target language using the target word.
     */
    Flashcard getSentenceFlashcard(String targetWord, Language language, Options options);

    Flashcard generateFlashcard(String targetWord, FlashcardType flashcardType, Language language, Options options);

    List<Flashcard> generateFlashcardsConcurrently(List<String> words, Language language, Options options) throws InterruptedException, ExecutionException;
}