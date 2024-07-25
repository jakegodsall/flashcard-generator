package org.jakegodsall.view.cli;

import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

class ApiKeyHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setOut(originalErr);
    }

    @Test
    void handle_apiKeyFound() throws Exception {
        try (MockedStatic<ApiKeyConfig> apiKeyConfigMock = Mockito.mockStatic(ApiKeyConfig.class)) {
            // set up the ApiKeyConfig.getApiKeyFromJsonFile mock
            given(ApiKeyConfig.getApiKeyFromJsonFile(ApiKeyConfig.CONFIG_DIR))
                    .willReturn("validApiKey");

            // call the handle method
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            ApiKeyHandler.handle(br);

            // verify the output
            assertThat(outContent.toString()).isEqualTo("API Key Found: validApiKey\n");

            // clean up
            Mockito.clearAllCaches();
        }
    }

    @Disabled
    @Test
    void handle_apiKeyNotFound() throws Exception {
        // Create a static mock of ApiKeyConfig
        Mockito.mockStatic(ApiKeyConfig.class);
        // set up the ApiKeyConfig.getApiKeyFromJsonFile mock
        given(ApiKeyConfig.getApiKeyFromJsonFile(ApiKeyConfig.CONFIG_DIR))
                .willThrow(new ApiKeyNotFoundException("API key not found"));

        // Mock the static method storeApiKeyInJsonFile
        doNothing().when(ApiKeyConfig.class);
        ApiKeyConfig.storeApiKeyInJsonFile(anyString(), eq(ApiKeyConfig.CONFIG_DIR));

        // Mock the static method storeApiKeyInJsonFile
        Mockito.mockStatic(ApiKeyHandler.class);
        given(ApiKeyHandler.promptUserForApiKey(any(BufferedReader.class))).willReturn("newApiKey");

        // Prepare the input for BufferedReader
        String simulatedUserInput = "newApiKey\n";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(simulatedUserInput.getBytes())));

        // Call the handle method
        ApiKeyHandler.handle(bufferedReader);

        // Verify the error output and prompt interaction
        assertThat(errContent.toString().contains("API key not found"));
        assertThat(outContent.toString().contains("API Key Found: newApiKey"));

        // Clean up
        Mockito.clearAllCaches();

    }

    @Test
    void promptUserForApiKey_whitespaceRemoved() throws Exception {
        String inputString = "testapikey     ";
        InputStream input = new ByteArrayInputStream(inputString.getBytes());

        System.setIn(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String result = ApiKeyHandler.promptUserForApiKey(br);

        assertThat(result).isEqualTo("testapikey");
    }
}