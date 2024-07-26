package org.jakegodsall.view;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.models.Language;
import org.jakegodsall.view.cli.ApiKeyHandler;
import org.jakegodsall.view.cli.CommandLineInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineInterfaceTest {

    private final Map<String, Language> languages = LanguageConfig.languageMap;
    private CommandLineInterface commandLineInterface;


    @BeforeEach
    void setUp() {
        commandLineInterface = new CommandLineInterface(new ApiKeyHandler(new ApiKeyConfigImpl()));
    }

    @Test
    void getLanguageFromUser_correctInputLowerCase() throws IOException {
        String input = "ru";
        Language validLanguage = languages.get(input);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        Language result = commandLineInterface.getLanguageFromUser(bufferedReader);

        assertThat(result).isEqualTo(validLanguage);
    }

    @Test
    void getLanguageFromUser_correctInputUpperCase() throws IOException {
        String input = "RU";
        Language validLanguage = languages.get(input);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Language result = commandLineInterface.getLanguageFromUser(bufferedReader);

        assertThat(result).isEqualTo(validLanguage);
    }
}