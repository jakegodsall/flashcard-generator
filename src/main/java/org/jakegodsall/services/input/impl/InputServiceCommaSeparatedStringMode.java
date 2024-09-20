package org.jakegodsall.services.input.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.services.input.InputService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class InputServiceCommaSeparatedStringMode implements InputService {

    private final BufferedReader bufferedReader;

    @Override
    public List<String> getInput() throws IOException {
        // Initialise list of strings
        List<String> words = new ArrayList<>();

        // Get the String from the user
        System.out.println("Enter the words as a String of comma-separated values (or 'exit' to cancel):");
        String wordString = bufferedReader.readLine().trim();

        // Handle empty input
        if (wordString.isEmpty()) {
            System.err.println("Error: Input cannot be empty.");
            return words;
        }
        // Handle 'exit'
        if (wordString.equalsIgnoreCase("exit")) {
            System.out.println("Goodbye");
            return words;
        }

        // Split the provided string into individual words and return List
        return Arrays.stream(wordString.split(","))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .toList();
    }
}
