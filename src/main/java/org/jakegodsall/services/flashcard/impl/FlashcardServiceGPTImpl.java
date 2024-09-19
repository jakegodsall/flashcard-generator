package org.jakegodsall.services.flashcard.impl;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.flashcard.FlashcardService;
import org.jakegodsall.services.http.HttpClientService;
import org.jakegodsall.services.json.JsonParseService;
import org.jakegodsall.services.prompt.PromptService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
    public Flashcard getWordFlashcard(String targetWord, Language language, Options options) {
        try {
            // Generate the prompt to get JSON in the correct format for populating WordFlashcard bean
            String prompt = promptGenerator.generatePrompt(targetWord, FlashcardType.WORD, language, options);
            // Generate HTTP POST request body
            String requestBody = promptGenerator.generateRequestBody(prompt);
            // Send the POST request to the GPT API
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            // Get the result
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            // Parse the result to a WordFlashcard
            return jsonParseService.parseFlashcard(result, FlashcardType.WORD);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public Flashcard getSentenceFlashcard(String targetWord, Language language, Options options) {
        try {
            // Generate the prompt to get JSON in the correct format for populating WordFlashcard bean
            String prompt = promptGenerator.generatePrompt(targetWord, FlashcardType.SENTENCE, language, options);
            // Generate HTTP POST request body
            String requestBody = promptGenerator.generateRequestBody(prompt);
            // Send the POST request to the GPT API
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            // Get the result
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            // Parse the result to a WordFlashcard
            return jsonParseService.parseFlashcard(result, FlashcardType.SENTENCE);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public Flashcard generateFlashcard(String targetWord, FlashcardType flashcardType, Language language, Options options) {
        try {
            // Generate the prompt based on the flashcard type
            String prompt = promptGenerator.generatePrompt(targetWord, flashcardType, language, options);
            // Generate HTTP POST request body
            String requestBody = promptGenerator.generateRequestBody(prompt);
            // Send the POST request to the GPT API
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            // Get the result
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            // Parse the result based on the flashcard type
            return jsonParseService.parseFlashcard(result, flashcardType);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Flashcard> generateFlashcardsConcurrently(List<String> words, Language language, Options options) throws InterruptedException, ExecutionException {
        List<Future<Flashcard>> futures = new ArrayList<>();

        // Submit a task for each word
        for (String word : words) {
            futures.add(executorService.submit(() -> getWordFlashcard(word, language, options)));
        }

        // Collect the results
        List<Flashcard> flashcards = new ArrayList<>();
        for (Future<Flashcard> future : futures) {
            flashcards.add(future.get());
        }

        return flashcards;
    }

    public void shutdownService() {
        executorService.shutdown();
    }
}