package org.jakegodsall.view.cli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jakegodsall.models.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class LanguageOptionsHandler {
    private final Language selectedLanguage;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Map<String, Object> getOptions() {
        System.out.println(selectedLanguage.toStringVerbose());
        try {
            boolean isStress = getStress();
            System.out.println("Stress marks selected: " + isStress);
        } catch (IOException ioException) {
            System.out.println("exception");
            System.err.println("Error reading input: " + ioException.getMessage());
        }

        return null;
    }

    private boolean getStress() throws IOException {
        boolean isStress = false;

        System.out.println("Do you want stress marks on the words?");
        System.out.println("[y] - Yes");
        System.out.println("[n] - No");

        String input = "";
        while (true) {
            input = br.readLine();
            if (input != null) {
                if (input.equals("y")) {
                    isStress = true;
                    break;
                }
                if (input.equals("n")) {
                    break;
                }
            }
            System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
        }
        return isStress;
    }


}
