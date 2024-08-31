package org.jakegodsall.services.prompt;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;

/**
 * Interface for prompt generator to create prompts for the OpenAI API.
 */
public interface PromptService {
    /**
     * Generates the request body for the OpenAI API call using a provided prompt.
     *
     * @param prompt the prompt to include in the request.
     * @return the JSON string representing the request body.
     * @throws JsonProcessingException if an error occurs while processing JSON.
     */
    String generateRequestBody(String prompt) throws JsonProcessingException;

    /**
     * Generates the prompt for the API to return the desired format for a {@link org.jakegodsall.models.flashcards.WordFlashcard}.
     *
     * @param targetWord     the word in the target language to include in the sentence.
     * @param language the language to use.
     * @param options  additional options for sentence generation.
     * @return the generated prompt.
     */
    String generatePromptForWordFlashcard(String targetWord, Language language, Options options);

    /**
     * Generates the prompt for the API to return the desired format for a {@link org.jakegodsall.models.flashcards.SentenceFlashcard}.
     *
     * @param targetWord     the word in the target language to include in the sentences.
     * @param language the language to use.
     * @param options  additional options for sentence generation.
     * @return the generated prompt. Format: {"nativeSentence": <sentence in native language>, "targetSentence": <sentence in target language>}
     */
    String generatePromptForSentenceFlashcard(String targetWord, Language language, Options options);

    String generatePromptForSubsequentWord(String targetWord);
}