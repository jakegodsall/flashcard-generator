package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.PromptService;


/**
 * Implementation of PromptService for GPT-specific prompt generation.
 */
public class PromptServiceGPTImpl implements PromptService {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String generateRequestBody(String prompt) throws JsonProcessingException {
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 500);

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        messages.add(userMessage);
        requestBody.put("messages", messages);

        return mapper.writeValueAsString(requestBody);
    }

    @Override
    public String generatePromptForSingleSentence(String word, Language language, Options options) {
        return "Please create a sentence using the following foreign language word and its associated details." +
                " Ensure that the sentence is grammatically correct and incorporates the specified options." +
                "\nLanguage: " + language.getName() +
                "\nWord: " + word +
                "\n\nDetails: ";
    }

    @Override
    public String generatePromptForSentencePair(String word, Language language, Options options) {
        // Create JSON payload
        return "Generate a sentence pair in JSON format. The JSON should include a very basicd sentence in the target language and its translation in the native language. The structure should be:\n" +
                "{\n" + "  \"nativeSentence\": \"<sentence in native language>\"\n" + "  \"targetSentence\": \"<sentence in target language>\"\n" + "}\n" +
                "\nThe word is " + word + " and the target language is " + language.getName() + ".\n";
    }
}