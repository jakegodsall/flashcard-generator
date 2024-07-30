package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.PromptService;

public class PromptServiceGPTImpl implements PromptService {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String generateRequestBody(String prompt) throws JsonProcessingException {
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 50);

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        messages.add(userMessage);
        requestBody.put("messages", messages);

        return mapper.writeValueAsString(requestBody);
    }

    @Override
    public String generatePrompt(String word, Language language, Options options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Please create a sentence using the following foreign language word and its associated details.")
                .append(" Ensure that the sentence is grammatically correct and incorporates the specified options.")
                .append("\nLanguage: ").append(language.getName())
                .append("\nWord: ").append(word)
                .append("\n\nDetails: ");
        if (options.getIsStress() != null)
            sb.append("\nInclude stress marks");
        sb.append("\nGender: ").append(options.getGender())
                .append("\nGrammatical tense: ").append(options.getTense());

        return sb.toString();
    }
}
