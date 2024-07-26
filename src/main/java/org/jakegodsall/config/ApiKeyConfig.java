package org.jakegodsall.config;

import org.jakegodsall.exceptions.ApiKeyNotFoundException;

import java.io.IOException;

public interface ApiKeyConfig {
    String getApiKeyFromJsonFile(String configDir) throws ApiKeyNotFoundException, IOException;
    void storeApiKeyInJsonFile(String apiKey, String configDir) throws IOException;
}
