package org.jakegodsall.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;

public interface PromptService {
    String generateRequestBody(String prompt) throws JsonProcessingException;
    String generatePrompt(String word, Language language, Options options);
}
