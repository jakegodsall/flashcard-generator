package org.jakegodsall.config.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.jakegodsall.utils.DirectoryUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration utility class for handling API key configuration.
 * Provides methods to read and write the API key from/to a JSON configuration file in the ~/.flashcard-generator directory.
 */
public class ApiKeyConfigImpl implements ApiKeyConfig {
    /**
     * File name of the file containing the API key.
     */
    public static final String CONFIG_FILE_NAME = "api_config.json";

    /**
     * Directory for the configuration file, defaulting to the user's home directory.
     */
    public static final String CONFIG_DIR = System.getProperty("user.home") + "/.flashcard-generator";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(ApiKeyConfigImpl.class.getName());

    /**
     * Reads the API key from the JSON configuration file.
     *
     * @return the API key as a String, or an empty string if the key is not found or an error occurs.
     */
    @Override
    public String getApiKeyFromFile(String configDir) throws ApiKeyNotFoundException, IOException {
        // get API file
        try (InputStream inputStream = getFileStream(configDir + "/" + CONFIG_FILE_NAME)) {
            // Navigate API file
            JsonNode rootNode = objectMapper.readTree(inputStream);
            System.out.println(rootNode);
            JsonNode apiKey = rootNode.path("apiKey");

            // Check to see if field exists
            if (apiKey.isMissingNode() || !apiKey.isValueNode()) {
                logger.log(Level.SEVERE, "API key not found in the file");
                throw new ApiKeyNotFoundException("API key not found in the file");
            }

            // Return the API key
            return apiKey.asText();
        }
    }

    /**
     * Stores the API configuration file in a secret directory in the user's home directory.
     *
     * @param apiKey the key to store in the configuration file.
     */
    @Override
    public void storeApiKeyInFile(String apiKey, String configDir) throws IOException {
        // Check to see if the directory already exists
        if (!DirectoryUtils.hiddenConfigDirectoryExists(configDir)) {
            // if it doesn't exist, create it
            DirectoryUtils.createHiddenConfigDirectory(configDir);
        }
        // put the key into a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(configDir + "/" + CONFIG_FILE_NAME))) {
            String formattedLine = "{\"apiKey\":\"" + apiKey + "\"}";
            bw.write(formattedLine);
        }
    }

    /**
     * Returns an InputStream for the specified configuration file.
     *
     * @param fileName the name of the configuration file.
     * @return an InputStream for the specified file.
     * @throws FileNotFoundException if the file does not exist.
     */
    public static InputStream getFileStream(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException("API file does not exist");
        return new FileInputStream(file);
    }


}
