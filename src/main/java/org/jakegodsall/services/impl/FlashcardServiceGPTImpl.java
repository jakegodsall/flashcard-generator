package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.io.HttpResponseParser;
import org.apache.http.util.EntityUtils;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.FlashcardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_MODELS_URL = "https://api.openai.com/v1/models";
    private static final String API_CHAT_URL = "https://api.openai.com/v1/chat/completions";

    private static final Logger logger = Logger.getLogger(HttpResponseParser.class.getName());

    @Override
    public List<String> getAvailableModels() {
        List<String> models = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_MODELS_URL);
            String bearerToken = System.getenv("OPENAI_API_KEY");

            request.setHeader("Authorization", "Bearer " + bearerToken);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");

            HttpResponse response = httpClient.execute(request);

            models = parseModelsFromHttpResponse(response);
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        return models;
    }

    @Override
    public String getSentence(String word, Language language, Options options) {
        String sentence = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(API_CHAT_URL);

            String bearerToken = System.getenv("OPENAI_API_KEY");

            request.setHeader("Authorization", "Bearer " + bearerToken);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");

            String json = generateRequestBody(generatePrompt(word, language, options));
            StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);

            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            sentence = parseSentenceFromHttpResponse(response);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return sentence;
    }

    public String generateRequestBody(String prompt) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 50);

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        messages.add(userMessage);
        requestBody.put("messages", messages);

        return mapper.writeValueAsString(requestBody);
    }

    public String generatePrompt(String word, Language language, Options options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Please create a sentence using the following foreign language word and its associated details." +
                " Ensure that the sentence is grammatically correct and incorporates the specified options.");
        sb.append("\nLanguage").append(language.getName());
        sb.append("\nWord: ").append(word);
        sb.append("\n\nDetails: ");
        if (options.getIsStress() != null)
            sb.append("\nInclude stress marks");
        sb.append("\nGender: ").append(options.getGender());
        sb.append("\nGrammatical tense").append(options.getTense());

        return sb.toString();
    }

    public List<String> parseModelsFromHttpResponse(HttpResponse response) throws IOException {
        if (response == null) {
            logger.log(Level.SEVERE, "HttpResponse is null");
            throw new IllegalArgumentException("HttpResponse is null");
        }

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            logger.log(Level.SEVERE, "HttpEntity is null");
            throw new NoSuchElementException("HttpEntity is null");
        }

        String result = EntityUtils.toString(entity);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode;

        try {
            rootNode = objectMapper.readTree(result);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error parsing JSON response");
            throw new IOException("Error parsing JSON response", ex);
        }

        List<String> models = new ArrayList<>();

        JsonNode dataNode = rootNode.path("data");
        if (dataNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) dataNode;
            Iterator<JsonNode> elements = arrayNode.elements();
            while (elements.hasNext()) {
                String modelName = elements.next().path("id").asText();
                if (modelName.isEmpty()) {
                    continue;
                }
                models.add(modelName);
            }
        } else {
            logger.log(Level.SEVERE, "Missing 'data' field in the JSON response");
            throw new NoSuchElementException("Missing 'data' field in the JSON response");
        }

        return models;
    }

    public String parseSentenceFromHttpResponse(HttpResponse response) throws IOException {
        if (response == null) {
            logger.log(Level.SEVERE, "HttpResponse is null");
            throw new IllegalArgumentException("HttpResponse cannot be null");
        }

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            logger.log(Level.SEVERE, "HttpEntity is null");
            throw new NoSuchElementException("HttpEntity is null");
        }

        String result = EntityUtils.toString(entity);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(result);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error parsing JSON response");
            throw new IOException("Error parsing JSON response", ex);
        }

        JsonNode choicesNode = rootNode.path("choices");
        if (!choicesNode.isArray() || choicesNode.isEmpty()) {
            logger.log(Level.SEVERE, "Missing or empty 'choices' field in the JSON response");
            throw new NoSuchElementException("Missing or empty 'choices' field in the JSON response");
        }

        JsonNode messageNode = choicesNode.get(0).path("message");
        if (messageNode.isMissingNode()) {
            logger.log(Level.SEVERE, "Missing 'message' field in the JSON response");
            throw new NoSuchElementException("Missing 'message' field in the JSON response");
        }

        JsonNode contentNode = messageNode.path("content");
        if (contentNode.isMissingNode()) {
            logger.log(Level.SEVERE, "Missing 'content' field in the JSON response");
            throw new NoSuchElementException("Missing 'content' field in the JSON response");
        }

        return contentNode.toString();
    }
}
