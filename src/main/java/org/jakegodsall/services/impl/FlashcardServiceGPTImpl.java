package org.jakegodsall.services.impl;

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
import org.jakegodsall.services.FlashcardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    public String getSentence(String word, Language language) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);

        String bearerToken = System.getenv("OPENAI_API_KEY");

        request.setHeader("Authorization", "Bearer " + bearerToken);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json; charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 50);

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", "Generate a Polish sentence using the word źdźbło");

        messages.add(userMessage);
        requestBody.put("messages", messages);

        String json = mapper.writeValueAsString(requestBody);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        request.setEntity(entity);

        System.out.println(json);

        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        httpClient.close();
        return "";
    }
}
