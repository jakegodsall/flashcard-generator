package org.jakegodsall.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.services.HttpClientService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of HttpClientService for GPT-specific HTTP requests.
 */
public class HttpClientServiceGPTImpl implements HttpClientService {
    private String BEARER_TOKEN;

    HttpClientServiceGPTImpl() {
        ApiKeyConfig apiKeyConfig = new ApiKeyConfigImpl();
        try {
            BEARER_TOKEN = apiKeyConfig.getApiKeyFromFile(ApiKeyConfigImpl.CONFIG_DIR);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public HttpResponse sendGetRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", "Bearer " + BEARER_TOKEN);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");
            return httpClient.execute(request);
        }
    }

    @Override
    public HttpResponse sendPostRequest(String url, String payload) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", "Bearer " + BEARER_TOKEN);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");

            StringEntity entity = new StringEntity(payload, StandardCharsets.UTF_8);
            request.setEntity(entity);

            return httpClient.execute(request);
        }
    }
}