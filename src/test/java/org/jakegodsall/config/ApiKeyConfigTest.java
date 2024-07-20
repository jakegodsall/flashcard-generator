package org.jakegodsall.config;

import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ApiKeyConfigTest {
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
}