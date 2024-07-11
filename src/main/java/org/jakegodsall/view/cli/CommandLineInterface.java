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

public class CommandLineInterface {
    private final FlashcardService flashcardService = new FlashcardServiceGPTImpl();

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    public void main() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            Language chosenLanguage = getLanguageFromUser(bufferedReader);
            LanguageOptionsHandler loh = new LanguageOptionsHandler(chosenLanguage, bufferedReader);
            Options selectedOptions = loh.getOptions();
            String word = getWordFromUser(bufferedReader);

            String sentence = flashcardService.getSentence(word, chosenLanguage, selectedOptions);
            System.out.println(sentence);
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    private void printLanguageOptions() {
        System.out.println("Languages:");
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            System.out.println("[" + entry.getKey() + "] - " + entry.getValue());
        }
    }

    public Language getLanguageFromUser(BufferedReader bufferedReader) throws IOException {
        printLanguageOptions();
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

    private String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word:");
        return bufferedReader.readLine();
    }
}
