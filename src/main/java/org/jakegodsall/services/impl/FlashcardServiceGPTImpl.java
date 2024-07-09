package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.FlashcardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    public String getSentence(String word, Language language, Options options) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);

        String bearerToken = System.getenv("OPENAI_API_KEY");

        request.setHeader("Authorization", "Bearer " + bearerToken);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json; charset=UTF-8");

        String json = generateRequestBody(generatePrompt(word, language, options));

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        request.setEntity(entity);

        System.out.println(json);

        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        httpClient.close();
        return responseBody;
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
}
