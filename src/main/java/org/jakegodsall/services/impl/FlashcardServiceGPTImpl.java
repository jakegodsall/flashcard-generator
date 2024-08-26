package org.jakegodsall.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.HttpClientService;
import org.jakegodsall.services.JsonParseService;
import org.jakegodsall.services.PromptService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the FlashcardService using OpenAI's GPT API.
 */
@RequiredArgsConstructor
public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_MODELS_URL = "https://api.openai.com/v1/models";
    private static final String API_CHAT_URL = "https://api.openai.com/v1/chat/completions";
    private static final Logger logger = Logger.getLogger(FlashcardServiceGPTImpl.class.getName());

    private final HttpClientService httpClientService;
    private final JsonParseService jsonParseService;
    private final PromptService promptGenerator;

    @Override
    public List<String> getAvailableModels() {
        try {
            HttpResponse response = httpClientService.sendGetRequest(API_MODELS_URL);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return jsonParseService.parseModels(result);
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, ioException.getMessage(), ioException);
            return List.of();
        }
    }

    @Override
    public WordFlashcard getWordFlashcard(String targetWord, Language language, Options options) {
        try {
            // Generate the prompt to get JSON in the correct format for populating WordFlashcard bean
            String prompt = promptGenerator.generatePromptForWordFlashcard(targetWord, language, options);
            System.out.println("PROMPT");
            System.out.println(prompt);
            // Generate HTTP POST request body
            String requestBody = promptGenerator.generateRequestBody(prompt);
            // Send the POST request to the GPT API
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            // Get the result
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            System.out.println("API RESPONSE");
            System.out.println(result);
            // Parse the result to a WordFlashcard
            return jsonParseService.parseWordFlashcard(result);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public SentenceFlashcard getSentenceFlashcard(String targetWord, Language language, Options options) {
        try {
            // Generate the prompt to get JSON in the correct format for populating WordFlashcard bean
            String prompt = promptGenerator.generatePromptForSentenceFlashcard(targetWord, language, options);
            System.out.println("PROMPT");
            System.out.println(prompt);
            // Generate HTTP POST request body
            String requestBody = promptGenerator.generateRequestBody(prompt);
            // Send the POST request to the GPT API
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            // Get the result
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            System.out.println("API RESPONSE");
            System.out.println(result);
            // Parse the result to a WordFlashcard
            return jsonParseService.parseSentenceFlashcard(result);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println(ex.getMessage());
        }
        return null;
    }
}