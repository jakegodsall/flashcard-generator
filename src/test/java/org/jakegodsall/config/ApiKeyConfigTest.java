package org.jakegodsall.config;

import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyConfigTest {

    @TempDir
    File tempDir;

    File mockedFile;

    FileWriter mockedFileWriter;

    @BeforeEach
    void setUp() {
        mockedFile = mock(File.class);
        mockedFileWriter = mock(FileWriter.class);
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
    public void storeApiKeyInJsonFile_directoryExists() throws IOException {
        String testApiKey ="testApiKey";

        // Setup mocks
        when(mockedFile.exists()).thenReturn(true);
        when(mockedFile.isDirectory()).thenReturn(true);
        when(mockedFile.getAbsolutePath()).thenReturn(tempDir.getAbsolutePath());

        // Call the method
        ApiKeyConfig.storeApiKeyInJsonFile(testApiKey);

        // Verify
        verify(mockedFile, times(1)).exists();
        verify(mockedFile, never()).mkdir();
        verify(mockedFileWriter, times(1)).write("{\"apiKey\":\"testApiKey\"}");
        verify(mockedFileWriter, times(1)).close();
    }

    @Test
    void testStoreApiKeyInJsonFile_directoryDoesNotExist() throws IOException {
        String testApiKey ="testApiKey";

        // Setup mocks
        when(mockedFile.exists()).thenReturn(false);
        when(mockedFile.mkdir()).thenReturn(true);
        when(mockedFile.isDirectory()).thenReturn(true);
        when(mockedFile.getAbsolutePath()).thenReturn(tempDir.getAbsolutePath());

        // Call the method under test
        ApiKeyConfig.storeApiKeyInJsonFile(testApiKey);

        // Verify interactions
        verify(mockedFile, times(1)).exists();
        verify(mockedFile, times(1)).mkdir();
        verify(mockedFileWriter, times(1)).write("{\"apiKey\":\"testApiKey\"}");
        verify(mockedFileWriter, times(1)).close();
    }
}