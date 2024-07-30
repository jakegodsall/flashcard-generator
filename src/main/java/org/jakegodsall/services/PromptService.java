package org.jakegodsall.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;

/**
 * Interface for prompt generator to create prompts for the OpenAI API.
 */
public interface PromptService {
    /**
     * Generates the request body for the OpenAI API call.
     *
     * @param prompt the prompt to include in the request.
     * @return the JSON string representing the request body.
     * @throws JsonProcessingException if an error occurs while processing JSON.
     */
    String generateRequestBody(String prompt) throws JsonProcessingException;

    /**
     * Generates the prompt for sentence creation.
     *
     * @param word     the word to include in the sentence.
     * @param language the language to use.
     * @param options  additional options for sentence generation.
     * @return the generated prompt.
     */
    String generatePromptForSentence(String word, Language language, Options options);

    /**
     * Generates the prompt for sentence pair creation.
     *
     * @param word     the word to include in the sentences.
     * @param language the language to use.
     * @param options  additional options for sentence generation.
     * @return the generated prompt.
     */
    String generatePromptForSentencePair(String word, Language language, Options options);
}