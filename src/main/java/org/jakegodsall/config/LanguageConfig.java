package org.jakegodsall.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.models.Language;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LanguageConfig {
    private static final String LANGUAGE_CONFIG_FILE = "/language_config.json";
    private static Map<String, Language> languageMap = new HashMap<>();

    static {
        loadConfig();
    }

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

    public static Language getLanguage(String code) {
        return languageMap.get(code);
    }

    public static Map<String, String> getAllLanguageNames() {
        Map<String, String> languageNames = new HashMap<>();
        for (Map.Entry<String, Language> entry : languageMap.entrySet()) {
            languageNames.put(entry.getKey(), entry.getValue().getName());
        }
        return languageNames;
    }
}
