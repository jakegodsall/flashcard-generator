package org.jakegodsall.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

public class FlashcardServiceGPTImpl implements FlashcardService {
    private static final String API_URL = "https://api.openai.com/v1/completions";

    @Override
    public String getSentence(String word, Language language) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(API_URL);

        String bearerToken = System.getenv("BEARER_TOKEN");

        request.setHeader("Authorization", "Bearer " + bearerToken);
        request.setHeader("Accept", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("prompt", "Tell me a joke.");
        requestBody.put("max_tokens", 50);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestBody);

        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);

        System.out.println(json);

        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);

        httpClient.close();
        return "";
    }
}
