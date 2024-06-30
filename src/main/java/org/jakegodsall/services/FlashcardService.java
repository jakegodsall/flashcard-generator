package org.jakegodsall.services;

import org.jakegodsall.models.Language;

public interface FlashcardService {
    public String getSentence(String word, Language language);
}
