package org.jakegodsall.services.impl;

import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.InputService;

import java.util.List;

public class InputServiceCommaSeparatedStringMode implements InputService {
    @Override
    public List<Flashcard> getInput() {
        return List.of();
    }
}
