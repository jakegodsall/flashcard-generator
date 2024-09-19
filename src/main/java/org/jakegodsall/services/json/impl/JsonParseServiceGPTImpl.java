package org.jakegodsall.services.json.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.json.JsonParseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of JsonParseService for GPT-specific JSON parsing.
 */
@RequiredArgsConstructor
public class JsonParseServiceGPTImpl implements JsonParseService {
    private final ObjectMapper objectMapper;

    @Override
    public List<String> parseModels(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        List<String> models = new ArrayList<>();

        JsonNode dataNode = rootNode.path("data");
        if (dataNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) dataNode;
            Iterator<JsonNode> elements = arrayNode.elements();
            while (elements.hasNext()) {
                String modelName = elements.next().path("id").asText();
                if (!modelName.isEmpty()) {
                    models.add(modelName);
                }
            }
        } else {
            throw new NoSuchElementException("Missing 'data' field in the JSON response");
        }
        return models;
    }

    @Override
    public Flashcard parseFlashcard(String responseBody, FlashcardType flashcardType) {
        try {
            String content = parseContentFromResponse(responseBody);

            // Parse the JSON int oa JsonNode tree
            JsonNode rootNode = objectMapper.readTree(content);

            // Delegate to specific parsing logic based on FlashcardType
            return switch (flashcardType) {
                case WORD -> parseWordFlashcardFromJson(rootNode);
                case SENTENCE -> parseSentenceFlashcardFromJson(rootNode);
                default -> throw new IllegalArgumentException("Unsupported FlashcardType: " + flashcardType);
            };
        } catch (JsonProcessingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Parses a WordFlashcard from the given JsonNode.
     */
    private WordFlashcard parseWordFlashcardFromJson(JsonNode rootNode) {
        JsonNode nativeWordNode = getJsonNode(rootNode, "nativeWord");
        JsonNode targetWordNode = getJsonNode(rootNode, "targetWord");
        JsonNode targetSentenceNode = getJsonNode(rootNode, "targetSentence");

        return new WordFlashcard(
                nativeWordNode.asText(),
                targetWordNode.asText(),
                targetSentenceNode.asText()
        );
    }

    /**
     * Parses a SentenceFlashcard from the given JsonNode.
     */
    private SentenceFlashcard parseSentenceFlashcardFromJson(JsonNode rootNode) {
        JsonNode nativeSentenceNode = getJsonNode(rootNode, "nativeSentence");
        JsonNode targetSentenceNode = getJsonNode(rootNode, "targetSentence");

        return new SentenceFlashcard(
                nativeSentenceNode.asText(),
                targetSentenceNode.asText()
        );
    }

    /**
     * Retrieves a JsonNode from the root and throws an exception if the field is missing.
     */
    private JsonNode getJsonNode(JsonNode rootNode, String fieldName) {
        JsonNode node = rootNode.path(fieldName);
        if (node.isMissingNode()) {
            throw new NoSuchElementException("Missing '" + fieldName + "' field in the JSON response");
        }
        return node;
    }

    /**
     * Extracts the "content" field from the API response JSON.
     * Throws an exception if any required field is missing.
     */
    private String parseContentFromResponse(String responseBody) throws JsonProcessingException {
        // Parse the JSON into a JsonNode tree
        JsonNode rootNode = objectMapper.readTree(responseBody);

        // Navigate the JSON tree to extract the content
        JsonNode choicesNode = rootNode.path("choices");
        if (choicesNode.isMissingNode() || !choicesNode.isArray() || choicesNode.isEmpty())
            throw new NoSuchElementException("Missing 'choices' field in the JSON response");
        JsonNode firstChoiceNode = choicesNode.get(0);
        if (firstChoiceNode.isMissingNode())
            throw new NoSuchElementException("'choices' array is empty");
        JsonNode messageNode = firstChoiceNode.path("message");
        if (messageNode.isMissingNode())
            throw new NoSuchElementException("Missing 'message' field in the JSON response");
        JsonNode contentNode = messageNode.path("content");
        if (contentNode.isMissingNode())
            throw new NoSuchElementException("Missing 'content' field in the JSON response");

        // Return the response
        String content = "";
        if (contentNode.isObject() || contentNode.isArray())
            content = contentNode.toString();
        if (contentNode.isValueNode())
            content = contentNode.asText();
        return content;
    }
}
