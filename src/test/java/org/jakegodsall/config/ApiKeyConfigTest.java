package org.jakegodsall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.jakegodsall.utils.DirectoryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ApiKeyConfigTest {

    @TempDir
    File tempDir;

    private File configDir;
    private File configFile;

    @Mock
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        configDir = new File(tempDir, ".flashcard-generator"); // temp-dir/.flashcard-generator/
        configFile = new File(configDir, ApiKeyConfig.CONFIG_FILE_NAME); // temp-dir/.flashcard-generator/api_config.json
    }

    @Test
    public void getApiKeyFromJsonFile_success() throws Exception {
        // Define a test API key
        String testApiKey = "123456";

        // Create the temp-dir/.flashcard-generator/ directory
        if (!configDir.exists())
            configDir.mkdirs();

        assertThat(configDir.isDirectory()).isTrue();
        assertThat(configDir.isHidden()).isTrue();


        // Create the temporary config file and write the API key
        try (BufferedWriter br = new BufferedWriter(new FileWriter(configFile))) {
            br.write("{\"apiKey\": \"" + testApiKey + "\"}");
        } catch (IOException ex) {
            throw new RuntimeException("Failed to write to the config file", ex);
        }

        String actualApiKey = ApiKeyConfig.getApiKeyFromJsonFile(configDir.toString());

        assertThat(configFile.exists()).isTrue();
        assertThat(actualApiKey).isEqualTo(testApiKey);
    }

    @Test
    public void getApiKeyFromJsonFile_fileNotFound() {
        // Create the temp-dir/.flashcard-generator/ directory
        if (!configDir.exists())
            configDir.mkdirs();

        assertThat(configDir.isDirectory()).isTrue();
        assertThat(configDir.isHidden()).isTrue();
        assertThat(configFile.exists()).isFalse();

        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            ApiKeyConfig.getApiKeyFromJsonFile(configDir.toString());
        });

        assertThat(exception.getMessage()).isEqualTo("API file does not exist");
    }

    @Test
    public void getApiKeyFromJsonFile_apiKeyNotFound() throws Exception {
        String jsonContent = "{}";

        // Create the temp-dir/.flashcard-generator/ directory
        if (!configDir.exists())
            configDir.mkdirs();

        assertThat(configDir.isDirectory()).isTrue();
        assertThat(configDir.isHidden()).isTrue();


        // Create the temporary config file and write empty JSON
        try (BufferedWriter br = new BufferedWriter(new FileWriter(configFile))) {
            br.write(jsonContent);
        }

        assertThat(configFile.exists()).isTrue();

        Exception exception = assertThrows(ApiKeyNotFoundException.class, () -> {
            ApiKeyConfig.getApiKeyFromJsonFile(configDir.toString());
        });

        assertThat(exception.getMessage()).isEqualTo("API key not found in the file");
    }

    @Test
    void storeApiKeyInJsonFile_directoryExists() throws Exception {
        String testApiKey ="testApiKey";

        // Create the directory before making call to method under test
        DirectoryUtils.createHiddenConfigDirectory(configDir.toString());

        // Call the method
        ApiKeyConfig.storeApiKeyInJsonFile(testApiKey, configDir.toString());

        // Verify
        assertThat(configDir.exists()).isTrue();
        assertThat(configDir.isDirectory()).isTrue();

        // Verify file contents
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line = br.readLine();
            assertThat(line).isEqualTo("{\"apiKey\":\"testApiKey\"}");
        }
    }

    @Test
    public void storeApiKeyInJsonFile_directoryDoesNotExist() throws Exception {
        String testApiKey ="testApiKey";

        // Call the method
        ApiKeyConfig.storeApiKeyInJsonFile(testApiKey, configDir.toString());

        // Verify
        assertThat(configDir.exists()).isTrue();
        assertThat(configDir.isDirectory()).isTrue();

        // Verify file contents
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line = br.readLine();
            assertThat(line).isEqualTo("{\"apiKey\":\"testApiKey\"}");
        }
    }

    @Test
    public void storeApiKeyInJsonFile_directoryExists_KeyInvalid() throws Exception {
        String testApiKey ="testApiKey";

        // Call the method
        ApiKeyConfig.storeApiKeyInJsonFile(testApiKey, configDir.toString());

        // Verify
        assertThat(configDir.exists()).isTrue();
        assertThat(configDir.isDirectory()).isTrue();

        // Verify file contents
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line = br.readLine();
            assertThat(line).isNotEqualTo("{\"apiKey\":\"invalidKey\"}");
        }
    }
}