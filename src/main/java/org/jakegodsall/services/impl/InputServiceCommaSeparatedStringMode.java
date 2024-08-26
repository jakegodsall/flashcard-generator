package org.jakegodsall.services.impl;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.InputService;

import java.io.IOException;
import java.util.List;

public class InputServiceCommaSeparatedStringMode implements InputService {
    @Override
    public List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException {
        return List.of();
    }
}
