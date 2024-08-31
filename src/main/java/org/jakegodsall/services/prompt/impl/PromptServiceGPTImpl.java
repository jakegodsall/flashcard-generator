package org.jakegodsall.services.prompt.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.services.prompt.PromptService;
import org.jakegodsall.utils.StringUtils;


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
    public String generatePromptForWordFlashcard(String targetWord, Language language, Options options) {
        return generateOriginalPromptTemplate() +
                "The word translated to the native language of English.\nThe target word itself.\nA very basic sentence in the target language.\nThe structure should be:\n" +
                "{\n" +
                StringUtils.createJsonComponent("nativeWord", "<word in native language>") + ",\n" +
                StringUtils.createJsonComponent("targetWord", "<word in target language>") + ",\n" +
                StringUtils.createJsonComponent("targetSentence", "<sentence in target language>") + "\n" +
                "}\n" +
                "\n\nThe word is " + targetWord + " and the target language is " + language.getName() + ".\n";
    }

    @Override
    public String generatePromptForSentenceFlashcard(String targetWord, Language language, Options options) {
        return generateOriginalPromptTemplate() +
                "The word translated to the native language of English.\nA very basic sentence in the target language using the provided word.\nThat same sentence translated into the native language of English\nThe structure should be:\n" +
                "{\n" +
                StringUtils.createJsonComponent("nativeSentence", "<sentence in native English language>") + ",\n" +
                StringUtils.createJsonComponent("targetSentence", "<sentence in target language>") + "\n" +
                "}\n" +
                "\n\nThe word is " + targetWord + " and the target language is " + language.getName() + ".\n";
    }

    @Override
    public String generatePromptForSubsequentWord(String targetWord) {
        return "The next word is " + targetWord;
    }

    private String generateOriginalPromptTemplate() {
        return "Given a word in a target language generate the following JSON.\n\"The JSON should include:\n";
    }
}