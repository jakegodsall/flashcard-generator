package org.jakegodsall.config;

import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.jakegodsall.utils.DirectoryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ApiKeyConfigTest {

    @TempDir
    File tempDir;

    private File configDir;
    private File configFile;

    @BeforeEach
    void setUp() {
        configDir = new File(tempDir, ".flashcard-generator");
        configFile = new File(configDir, ApiKeyConfig.CONFIG_FILE_NAME);
    }

    @Disabled
    @Test
    public void getApiKeyFromJsonFile_fileNotFound() {
        try (MockedStatic<ApiKeyConfig> mockedApiKeyConfig = mockStatic(ApiKeyConfig.class)) {
            mockedApiKeyConfig.when(() -> ApiKeyConfig.getFileStream(anyString()))
                    .thenReturn(null);

            assertThatThrownBy(ApiKeyConfig::getApiKeyFromJsonFile)
                    .isInstanceOf(FileNotFoundException.class)
                    .hasMessageContaining("API file does not exist");
        }
    }

    @Disabled
    @Test
    public void getApiKeyFromJsonFile_apiKeyNotFound() {
        String jsonContent = "{}";
        InputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes());

        try (MockedStatic<ApiKeyConfig> mockedApiKeyConfig = mockStatic(ApiKeyConfig.class)) {
            mockedApiKeyConfig.when(() -> ApiKeyConfig.getFileStream(anyString()))
                    .thenReturn(inputStream);

            assertThatThrownBy(ApiKeyConfig::getApiKeyFromJsonFile)
                    .isInstanceOf(ApiKeyNotFoundException.class)
                    .hasMessageContaining("API key not found in:");
        }
    }

    @Test
    void testStoreApiKeyInJsonFile_directoryExists() throws IOException {
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
    public void storeApiKeyInJsonFile_directoryDoesNotExist() throws IOException {
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
    public void storeApiKeyInJsonFile_directoryExists_KeyInvalid() throws IOException {
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