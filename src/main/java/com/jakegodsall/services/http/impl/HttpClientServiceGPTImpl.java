package com.jakegodsall.services.http.impl;

import com.jakegodsall.services.http.HttpClientService;
import com.jakegodsall.impl.ApiKeyConfigImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import com.jakegodsall.config.ApiKeyConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of HttpClientService for GPT-specific HTTP requests.
 */
public class HttpClientServiceGPTImpl implements HttpClientService {
    private final CloseableHttpClient httpClient;
    private String BEARER_TOKEN;

    public HttpClientServiceGPTImpl(CloseableHttpClient httpClient) {
        ApiKeyConfig apiKeyConfig = new ApiKeyConfigImpl();
        try {
            BEARER_TOKEN = apiKeyConfig.getApiKeyFromFile(ApiKeyConfigImpl.CONFIG_DIR);
            System.out.println("BEARER TOKEN: " + BEARER_TOKEN);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse sendGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setHeader("Authorization", "Bearer " + BEARER_TOKEN);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json; charset=UTF-8");
        return httpClient.execute(request);
    }

    @Override
    public HttpResponse sendPostRequest(String url, String payload) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Authorization", "Bearer " + BEARER_TOKEN);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json; charset=UTF-8");

        StringEntity entity = new StringEntity(payload, StandardCharsets.UTF_8);
        request.setEntity(entity);

        return httpClient.execute(request);
    }

    public void close() throws IOException {
        httpClient.close();
    }
}