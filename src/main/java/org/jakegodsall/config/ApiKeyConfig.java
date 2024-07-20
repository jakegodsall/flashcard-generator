package org.jakegodsall.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;

import java.io.*;

public class ApiKeyConfig {
    public static final String CONFIG_FILE = "/api_config.json";
    public static final String CONFIG_DIR = System.getProperty("user.home");

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getApiKeyFromJsonFile() {
        // get API file
        try (InputStream inputStream = getFileStream(CONFIG_FILE)) {
            // Navigate API file
            JsonNode rootNode = objectMapper.readTree(inputStream);
            System.out.println("Root Node: " + rootNode);
            JsonNode apiKey = rootNode.path("apiKey");

            // Check to see if field exists
            if (apiKey.isMissingNode() || !apiKey.isValueNode())
                throw new ApiKeyNotFoundException("API key not found");

            // Return the API key
            return apiKey.asText();
        } catch (ApiKeyNotFoundException | IOException exception) {
            System.err.println(exception.getMessage());
        }
        return "";
    }


    public static void setApiKeyInJsonFile (String apiKey) {
    }


    public static InputStream getFileStream(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException("API file does not exist");
        return new FileInputStream(file);
    }
}
