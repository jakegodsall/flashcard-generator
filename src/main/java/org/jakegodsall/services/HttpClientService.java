package org.jakegodsall.services;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface HttpClientService {
    HttpResponse sendGetRequest(String url) throws Exception;
    HttpResponse sendPostRequest(String url, String payload) throws IOException;
}
