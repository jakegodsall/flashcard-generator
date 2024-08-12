package org.jakegodsall.services.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
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
public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_MODELS_URL = "https://api.openai.com/v1/models";
    private static final String API_CHAT_URL = "https://api.openai.com/v1/chat/completions";
    private static final Logger logger = Logger.getLogger(FlashcardServiceGPTImpl.class.getName());

    private final HttpClientService httpClientService = new HttpClientServiceGPTImpl();
    private final JsonParseService jsonParseService = new JsonParseServiceGPTImpl();
    private final PromptService promptGenerator = new PromptServiceGPTImpl();

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
    public String getSentence(String word, Language language, Options options) {
        try {
            String prompt = promptGenerator.generatePromptForSingleSentence(word, language, options);
            String requestBody = promptGenerator.generateRequestBody(prompt);
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            return jsonParseService.parseSentence(result);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
    }

    @Override
    public SentenceFlashcard generateSentencePair(String word, Language language, Options options) {
        try {
            String prompt = promptGenerator.generatePromptForSentencePair(word, language, options);
            String requestBody = promptGenerator.generateRequestBody(prompt);
            HttpResponse response = httpClientService.sendPostRequest(API_CHAT_URL, requestBody);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            String englishSentence = ""; // Extract English sentence from result
            String foreignSentence = ""; // Extract foreign sentence from result

            httpClientService.close();
            return new SentenceFlashcard(englishSentence, foreignSentence);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }
}