package org.jakegodsall.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ApiKeyConfig {
    private static final String CONFIG_FILE = "/api_config.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getApiKey() {
        try (InputStream inputStream = ApiKeyConfig.class.getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null)
                throw new IOException("Configuration file ("
                        + CONFIG_FILE +") does not exist");
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode apiKey = rootNode.path("apiKey");
            if (!apiKey.isMissingNode() && apiKey.isValueNode())
                return apiKey.asText();
            else
                System.err.println("apiKey is not found in: " + CONFIG_FILE);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return "";
    }
}
