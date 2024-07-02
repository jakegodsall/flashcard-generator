package org.jakegodsall.view;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.impl.FlashcardServiceGPTImpl;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CommandLineInterface {
    private final Scanner scanner = new Scanner(System.in);

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    public void main() throws IOException {
        printLanguageOptions();
        Language chosenLanguage = getLanguageFromUser(scanner);
        System.out.println("Enter a word: ");

        String word = "Give me a basic Polish sentence using the word rozumiem";

        FlashcardService fs = new FlashcardServiceGPTImpl();

        fs.getSentence(word, chosenLanguage);
    }

    public void printLanguageOptions() {
        System.out.println("Languages:");
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            System.out.println("[" + entry.getKey() + "] - " + entry.getValue());
        }
    }

    public Language getLanguageFromUser(Scanner scanner) {
        boolean validInput = false;
        String input = "";
        while (!validInput) {
            System.out.println("Choose the desired language from the following list (use codes):");
            input = scanner.nextLine();
            if (languages.containsKey(input.toLowerCase().trim())) {
                validInput = true;
            }
        }
        scanner.close();
        Language chosenLanguage = LanguageConfig.getLanguage(input);
        System.out.println("Chosen language: " + chosenLanguage);
        return chosenLanguage;
    }
}
