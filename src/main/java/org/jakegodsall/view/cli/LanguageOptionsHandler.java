package org.jakegodsall.view.cli;

import com.fasterxml.jackson.core.JsonToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.Gender;
import org.jakegodsall.models.enums.Tense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Getter
@Setter
@RequiredArgsConstructor
public class LanguageOptionsHandler {
    private final Language selectedLanguage;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Options getOptions() {
        Options options = new Options();
        try {
            if (selectedLanguage.isSupportsStress())
                options.setIsStress(getStress(br));
            options.setTense(getTense(br));
            options.setGender(getGender(br));
        } catch (IOException ioException) {
            System.out.println("exception");
            System.err.println("Error reading input: " + ioException.getMessage());
        }
        System.out.println(options);
        return options;
    }

    public boolean getStress(BufferedReader bufferedReader) throws IOException {
        boolean isStress = false;

        System.out.println("Do you want stress marks on the words?");
        System.out.println("[y] - Yes");
        System.out.println("[n] - No");

        String input = "";
        while (true) {
            input = bufferedReader.readLine();
            if (input != null) {
                if (input.equalsIgnoreCase("y")) {
                    isStress = true;
                    break;
                }
                if (input.equalsIgnoreCase("n")) {
                    break;
                }
            }
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return isStress;
    }

    public Tense getTense(BufferedReader bufferedReader) throws IOException {
        System.out.println("Which tense do you want to use?");
        for (Tense tense : selectedLanguage.getTenses()) {
            System.out.println("[" + tense +"]");
        }

        Tense selectedTense = Tense.PRESENT;

        String input;
        while (true) {
            input = bufferedReader.readLine().trim().toLowerCase();
            switch (input) {
                case "past":
                    selectedTense = Tense.PAST;
                    break;
                case "present":
                    break;
                case "future":
                    selectedTense = Tense.FUTURE;
                    break;
                default:
                    System.out.println("Invalid input");
                    continue;
            }
            break;
        }
        return selectedTense;
    }

    public Gender getGender(BufferedReader bufferedReader) throws IOException {
        System.out.println("Which gender do you want to use?");
        for (Gender gender : selectedLanguage.getGenders()) {
            System.out.println("[" + gender + "]");
        }

        Gender selectedGender = Gender.MASCULINE;
        String input;

        while(true) {
            input = bufferedReader.readLine().trim().toLowerCase();
            switch(input) {
                case "masculine":
                    break;
                case "feminine":
                    selectedGender = Gender.FEMININE;
                    break;
                case "neuter":
                    selectedGender = Gender.NEUTER;
                    break;
                default:
                    System.out.println("Invalid input");
                    continue;
            }
            break;
        }
        return selectedGender;
    }
}
