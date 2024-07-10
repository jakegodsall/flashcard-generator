package org.jakegodsall.view.cli;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.impl.FlashcardServiceGPTImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    public void main() {
        printLanguageOptions();
        try {
            Language chosenLanguage = getLanguageFromUser(bufferedReader);
            LanguageOptionsHandler loh = new LanguageOptionsHandler(chosenLanguage);
            Options selectedOptions = loh.getOptions();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public void printLanguageOptions() {
        System.out.println("Languages:");
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            System.out.println("[" + entry.getKey() + "] - " + entry.getValue());
        }
    }

    public Language getLanguageFromUser(BufferedReader bufferedReader) throws IOException {
        boolean validInput = false;
        String input = "";
        while (!validInput) {
            System.out.println("Choose the desired language from the following list (use codes):");
            input = bufferedReader.readLine();
            if (languages.containsKey(input.toLowerCase().trim())) {
                validInput = true;
            }
        }
        Language chosenLanguage = LanguageConfig.getLanguage(input);
        System.out.println("Chosen language: " + chosenLanguage);
        return chosenLanguage;
    }
}
