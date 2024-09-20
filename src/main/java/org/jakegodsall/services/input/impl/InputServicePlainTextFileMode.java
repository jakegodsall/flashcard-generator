package org.jakegodsall.services.input.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.services.input.InputService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InputServicePlainTextFileMode implements InputService {

    private final BufferedReader consoleReader;

    @Override
    public List<String> getInput() throws IOException {
        List<String> words = new ArrayList<>();
        System.out.println("Enter the absolute path of the file:");
        String fileName = consoleReader.readLine();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return words;
    }
}
