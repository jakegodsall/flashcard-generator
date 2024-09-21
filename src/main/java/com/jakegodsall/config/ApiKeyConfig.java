package com.jakegodsall.config;

import com.jakegodsall.config.exceptions.ApiKeyNotFoundException;

import java.io.IOException;

public interface ApiKeyConfig {
    String getApiKeyFromFile(String configDir) throws ApiKeyNotFoundException, IOException;
    void storeApiKeyInFile(String apiKey, String configDir) throws IOException;
}
