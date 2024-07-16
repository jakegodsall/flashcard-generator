package org.jakegodsall.services;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;

import java.io.IOException;
import java.util.List;

public interface FlashcardService {
    public List<String> getAvailableModels();
    public String getSentence(String word, Language language, Options options) throws IOException;
}
