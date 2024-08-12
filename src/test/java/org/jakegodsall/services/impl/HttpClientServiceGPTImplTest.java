package org.jakegodsall.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jakegodsall.services.HttpClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpClientServiceGPTImplTest {
    @Mock
    private CloseableHttpClient mockHttpClient;

    @InjectMocks
    private HttpClientService httpClientService;

    private static final String BEARER_TOKEN = "your_bearer_token";
    private static final String TEST_URL = "http://example.com";

    @BeforeEach
    public void setUp() {
        httpClientService = new HttpClientServiceGPTImpl();
    }

    @Test
    public void testSendGetRequest_success() throws IOException {
        // Arrange
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        // Act
        HttpResponse response = httpClientService.sendGetRequest(TEST_URL);

        // Assert
        assertNotNull(response);
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }

    @Test
    public void testSendGetRequest_failure() throws IOException {
        // Arrange
        when(mockHttpClient.execute(any(HttpGet.class))).thenThrow(new IOException("Network error"));

        // Act & Assert
        assertThrows(IOException.class, () -> {
            httpClientService.sendGetRequest(TEST_URL);
        });
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }

    @Test
    public void testSendGetRequest_headersSetCorrectly() throws IOException {
        // Arrange
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        // Act
        httpClientService.sendGetRequest(TEST_URL);

        // Assert
        verify(mockHttpClient, times(1)).execute(argThat(request -> {
            assertTrue(request instanceof HttpGet);
            HttpGet getRequest = (HttpGet) request;
            assertEquals("Bearer " + BEARER_TOKEN, getRequest.getFirstHeader("Authorization").getValue());
            assertEquals("application/json", getRequest.getFirstHeader("Accept").getValue());
            assertEquals("application/json; charset=UTF-8", getRequest.getFirstHeader("Content-Type").getValue());
            return true;
        }));
    }
}