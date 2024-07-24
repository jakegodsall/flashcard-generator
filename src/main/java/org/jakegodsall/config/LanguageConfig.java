package org.jakegodsall.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.exceptions.NoSuchLanguageException;
import org.jakegodsall.models.Language;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * The {@code LanguageConfig} class handles the loading and retrieval of language configurations
 * from a JSON file. It provides methods to get language details and names.
 */
public class LanguageConfig {
    private static final String LANGUAGE_CONFIG_FILE = "/language_config.json";
    public static Map<String, Language> languageMap;

    static {
        loadConfig();
    }

    /**
     * Loads the language configuration from the specified JSON file into the {@code languageMap}.
     * This method is called when the class is loaded.
     */
    public static void loadConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = LanguageConfig.class.getResourceAsStream(LANGUAGE_CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + LANGUAGE_CONFIG_FILE);
            }
            languageMap = objectMapper.readValue(inputStream, new TypeReference<Map<String, Language>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the {@code Language} object corresponding to the given language code.
     *
     * @param code the language code
     * @return the {@code Language} object for the given code
     * @throws NoSuchLanguageException if the language code is not found in the {@code languageMap}
     */
    public static Language getLanguage(String code) {
        if (!languageMap.containsKey(code)) {
            throw new NoSuchLanguageException("Language '" + code + "' is not found");
        }
        return languageMap.get(code);
    }

    /**
     * Retrieves a map of all language codes to their corresponding language names.
     *
     * @return a map where the keys are language codes and the values are language names
     */
    public static Map<String, String> getAllLanguageNames() {
        Map<String, String> languageNames = new HashMap<>();
        for (Map.Entry<String, Language> entry : languageMap.entrySet()) {
            languageNames.put(entry.getKey(), entry.getValue().getName());
        }
        return languageNames;
    }
}
