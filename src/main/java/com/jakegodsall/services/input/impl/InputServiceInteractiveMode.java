package com.jakegodsall.services.input.impl;

import com.jakegodsall.services.input.InputService;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class InputServiceInteractiveMode implements InputService {

    private final BufferedReader bufferedReader;

    @Override
    public List<String> getInput() throws IOException {
        return Collections.singletonList(getWordFromUser(bufferedReader));
    }

    public String getWordFromUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter a word (or -1 to exit interactive mode):");
        return bufferedReader.readLine().trim();
    }
}
