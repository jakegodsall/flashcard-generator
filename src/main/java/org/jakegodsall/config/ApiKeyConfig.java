package org.jakegodsall.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ApiKeyConfig {
    public static final String CONFIG_FILE = "/api_config.json";
    public static final String CONFIG_DIR = System.getProperty("user.home");

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getApiKeyFromJsonFile() throws Exception {
        // get API file
        InputStream inputStream = getResourceAsStream(CONFIG_FILE);
        if (inputStream == null)
            throw new FileNotFoundException("API file does not exist");

        // Navigate API file
        JsonNode rootNode = objectMapper.readTree(inputStream);
        JsonNode apiKey = rootNode.path("apiKey");

        // Check to see if field exists
        if (apiKey.isMissingNode() || apiKey.isValueNode())
            throw new ApiKeyNotFoundException("API key not found");

        // Return the API key
        return apiKey.asText();
    }

    public static void setApiKeyInJsonFile(String apiKey) {
    }

    public static InputStream getResourceAsStream(String fileName) {
        return ApiKeyConfig.class.getResourceAsStream(fileName);
    }
}
