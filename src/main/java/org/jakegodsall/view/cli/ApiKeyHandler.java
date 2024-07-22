package org.jakegodsall.view.cli;

import org.jakegodsall.config.ApiKeyConfig;
import org.jakegodsall.exceptions.ApiKeyNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;

public class ApiKeyHandler {

    public static void handle(BufferedReader bufferedReader) {
        try {
            String api = ApiKeyConfig.getApiKeyFromJsonFile(ApiKeyConfig.CONFIG_DIR);
            System.out.println("API Key Found: " + api);
        } catch (ApiKeyNotFoundException | IOException exception) {
            System.err.println(exception.getMessage());
            try {
                String newApiKey = promptUserForApiKey(bufferedReader);
                ApiKeyConfig.storeApiKeyInJsonFile(newApiKey, ApiKeyConfig.CONFIG_DIR);
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }

    }

    public static String promptUserForApiKey(BufferedReader bufferedReader) throws IOException {
        System.out.println("Please enter your API key: ");
        return bufferedReader.readLine().trim();
    }
}
