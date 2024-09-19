package org.jakegodsall.services.http.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jakegodsall.services.http.HttpClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpClientServiceGPTImplTest {
    private CloseableHttpClient mockHttpClient;
    private HttpClientService httpClientService;

    private static final String BEARER_TOKEN = "your_bearer_token";
    private static final String TEST_URL = "http://example.com";

    @BeforeEach
    public void setUp() {
        mockHttpClient = mock(CloseableHttpClient.class);
        httpClientService = new HttpClientServiceGPTImpl(mockHttpClient);
    }

    @Test
    public void testSendGetRequest_success() throws IOException {
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        // Mock the response entity to avoid issues when interacting with the response
        when(mockResponse.getEntity()).thenReturn(mockEntity);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        // Act
        HttpResponse response = httpClientService.sendGetRequest(TEST_URL);

        // Assert
        assertNotNull(response);
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }

    @Test
    public void testSendGetRequest_failure() throws IOException {
        when(mockHttpClient.execute(any(HttpGet.class))).thenThrow(new IOException("Network error"));
        assertThrows(IOException.class, () -> {
            httpClientService.sendGetRequest(TEST_URL);
        });
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }
}