package org.jakegodsall.services;

import org.jakegodsall.models.Language;

import java.io.IOException;

public interface FlashcardService {
    public String getSentence(String word, Language language) throws IOException;
}
