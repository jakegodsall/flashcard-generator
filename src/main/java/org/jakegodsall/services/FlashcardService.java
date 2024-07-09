package org.jakegodsall.services;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;

import java.io.IOException;

public interface FlashcardService {
    public String getSentence(String word, Language language, Options options) throws IOException;
}
