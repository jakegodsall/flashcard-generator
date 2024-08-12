package org.jakegodsall.config;

import org.jakegodsall.exceptions.NoSuchLanguageException;
import org.jakegodsall.models.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LanguageConfigTest {

    private static final String MOCK_JSON = "{ \"en\": { \"name\": \"English\" }, \"fr\": { \"name\": \"French\" } }";

    @BeforeAll
    public static void setup() throws Exception {
        InputStream mockInputStream = new ByteArrayInputStream(MOCK_JSON.getBytes());
        LanguageConfig.loadConfig(mockInputStream);
    }

    @Test
    public void testGetLanguage_existingLanguage() {
        Language language = LanguageConfig.getLanguage("en");
        assertThat(language).isNotNull();
        assertThat(language.getName()).isEqualTo("English");
    }

    @Test
    public void testGetLanguage_nonExistingLanguage() {
        Exception exception = assertThrows(NoSuchLanguageException.class, () -> LanguageConfig.getLanguage("es"));
        assertThat(exception.getMessage()).isEqualTo("Language with code 'es' is not found");
    }

    @Test
    public void testGetAllLanguageNames() {
        Map<String, String> languageNames = LanguageConfig.getAllLanguageNames();
        assertThat(languageNames).isNotNull();
        assertThat(languageNames.size()).isEqualTo(2);
        assertThat(languageNames.get("en")).isEqualTo("English");
        assertThat(languageNames.get("fr")).isEqualTo("French");
    }
}