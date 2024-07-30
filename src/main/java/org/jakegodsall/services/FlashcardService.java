package org.jakegodsall.services;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.SentencePair;

import java.util.List;

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
     * Generates a sentence using the given word, language, and options.
     *
     * @param word     the word to include in the sentence.
     * @param language the language to use.
     * @param options  additional options for sentence generation.
     * @return a generated sentence.
     */
    String getSentence(String word, Language language, Options options);

    /**
     * Translates a given word from a foreign language to English and provides a sentence using the word in the foreign language.
     *
     * @param word     the word to translate.
     * @param language the language of the word.
     * @return a TranslationResult containing the English word, foreign word, and a sentence.
     */
//    TranslationResult translateWord(String word, Language language);

    /**
     * Generates a pair of sentences: one in English and one in the foreign language, translating the given word.
     *
     * @param word     the word to include in the sentences.
     * @param language the language of the word.
     * @param options  additional options for sentence generation.
     * @return a SentencePair containing the English and foreign language sentences.
     */
    SentencePair generateSentencePair(String word, Language language, Options options);
}