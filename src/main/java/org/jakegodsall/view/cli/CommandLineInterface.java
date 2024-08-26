package org.jakegodsall.view.cli;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.enums.InputMode;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.impl.FlashcardServiceGPTImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@RequiredArgsConstructor
public class CommandLineInterface {
    private final FlashcardService flashcardService = new FlashcardServiceGPTImpl();

    Map<String, String> languages = LanguageConfig.getAllLanguageNames();

    private final ApiKeyHandler apiKeyHandler;

    public void main() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            // API Key Handling
            apiKeyHandler.handle(bufferedReader);

            // Options handling
            Language chosenLanguage = getLanguageFromUser(bufferedReader);
            // LanguageOptionsHandler loh = new LanguageOptionsHandler(chosenLanguage, bufferedReader);
            // Options selectedOptions = loh.getOptions();
            Options selectedOptions = Options.builder().build();

            // Get flashcard type
            FlashcardType flashcardType = getFlashcardType(bufferedReader);

            // Get input mode
            InputMode inputMode = getInputMode(bufferedReader);



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
        printLanguageOptions();
        boolean validInput = false;
        String input = "";
        while (!validInput) {
            System.out.println("Choose the desired language from the following list (use codes):");
            input = bufferedReader.readLine();
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            if (languages.containsKey(input.toLowerCase().trim())) {
                validInput = true;
            }
        }
        Language chosenLanguage = LanguageConfig.getLanguage(input);
        System.out.println("Chosen language: " + chosenLanguage);
        return chosenLanguage;
    }

    public InputMode getInputMode(BufferedReader bufferedReader) throws IOException {
        System.out.println("Choose an input mode:");
        System.out.println("[1] Interactive Mode");
        System.out.println("[2] Comma-separated String Mode");
        System.out.println("[3] Plain Text File Mode");

        boolean validInput = false;
        String input = "";
        InputMode result = InputMode.INTERACTIVE;  // Default to Interactive Mode

        while (!validInput) {
            input = bufferedReader.readLine();
            if (input == null) {
                throw new IllegalArgumentException("Input cannot be null");
            }
            switch (input) {
                case "1":
                    result = InputMode.INTERACTIVE;
                    validInput = true;
                    break;
                case "2":
                    result = InputMode.COMMA_SEPARATED_STRING;
                    validInput = true;
                    break;
                case "3":
                    result = InputMode.PLAIN_TEXT_FILE;
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid option:");
                    System.out.println("[1] Interactive Mode");
                    System.out.println("[2] Comma-separated String Mode");
                    System.out.println("[3] Plain Text File Mode");
                    break;
            }
        }
        return result;
    }

    public FlashcardType getFlashcardType(BufferedReader bufferedReader) throws IOException {
        System.out.println("Choose a flashcard type:");
        System.out.println("[1] Word Flashcard");
        System.out.println("[2] Sentence Flashcard");
        boolean validInput = false;
        String input = "";
        FlashcardType result = FlashcardType.WORD;
        while (!validInput) {
            input = bufferedReader.readLine();
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            if (input.equals("1")) {
                validInput = true;
            }
            if (input.equals("2")) {
                result = FlashcardType.SENTENCE;
                validInput = true;
            }
        }
        return result;
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word:");
        return bufferedReader.readLine();
    }
}
