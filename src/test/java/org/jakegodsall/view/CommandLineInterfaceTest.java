package org.jakegodsall.view;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.view.cli.CommandLineInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineInterfaceTest {

    private final Map<String, Language> languages = LanguageConfig.languageMap;
    private CommandLineInterface commandLineInterface;


    @BeforeEach
    void setUp() {
        commandLineInterface = new CommandLineInterface();
    }

    @Test
    void getLanguageFromUser_correctInputLowerCase() {
        String input = "ru";
        Language validLanguage = languages.get(input);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        Language result = commandLineInterface.getLanguageFromUser(scanner);

        assertThat(result).isEqualTo(validLanguage);
    }

    @Test
    void getLanguageFromUser_correctInputUpperCase() {
        String input = "RU";
        Language validLanguage = languages.get(input);

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        Language result = commandLineInterface.getLanguageFromUser(scanner);

        assertThat(result).isEqualTo(validLanguage);
    }
}