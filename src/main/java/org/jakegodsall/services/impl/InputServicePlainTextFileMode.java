package org.jakegodsall.services.impl;

import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.FlashcardService;
import org.jakegodsall.services.InputService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class InputServicePlainTextFileMode implements InputService {

    private final BufferedReader bufferedReader;
    private final FlashcardService flashcardService;

    @Override
    public List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException {
        return List.of();
    }
}
