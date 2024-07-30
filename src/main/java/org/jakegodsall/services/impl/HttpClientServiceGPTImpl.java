package org.jakegodsall.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jakegodsall.services.HttpClientService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpClientServiceGPTImpl implements HttpClientService {
    private static final String BEARER_TOKEN = System.getenv("OPENAI_API_KEY");

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
    public HttpResponse sendPostRequest(String url, String json) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", "Bearer " + BEARER_TOKEN);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");

            StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
            request.setEntity(entity);

            return httpClient.execute(request);
        }
    }
}
