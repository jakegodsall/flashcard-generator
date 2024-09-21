package com.jakegodsall.services.http;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Interface for HTTP client service to handle HTTP requests.
 */
public interface HttpClientService {
    /**
     * Sends a GET request to the specified URL.
     *
     * @param url the URL to send the GET request to.
     * @return the HTTP response.
     * @throws IOException if an I/O error occurs.
     */
    HttpResponse sendGetRequest(String url) throws IOException;

    /**
     * Sends a POST request to the specified URL with the given JSON body.
     *
     * @param url the URL to send the POST request to.
     * @param payload the JSON body to include in the POST request.
     * @return the HTTP response.
     * @throws IOException if an I/O error occurs.
     */
    HttpResponse sendPostRequest(String url, String payload) throws IOException;

    /**
     * Releases the Http client.
     *
     * @throws IOException if the connection is not open.
     */
    void close() throws IOException;
}