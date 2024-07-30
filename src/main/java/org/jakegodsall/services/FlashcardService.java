package org.jakegodsall.services;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.SentencePair;

import java.io.IOException;
import java.util.List;

public interface FlashcardService {
    List<String> getAvailableModels();
    String getSentence(String word, Language language, Options options) throws IOException;
    SentencePair generateSentencePair(String word, Language language, Options options) throws IOException;
}
