package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.JsonParseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of JsonParseService for GPT-specific JSON parsing.
 */
public class JsonParseServiceGPTImpl implements JsonParseService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
    public WordFlashcard parseWordFlashcard(String responseBody) throws JsonParseException {
        try {
            // Parse the JSON into a JsonNode tree
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Navigate the JSON tree to extract the required values
            JsonNode nativeWordNode = rootNode.path("nativeWord");
            if (nativeWordNode.isMissingNode())
                throw new NoSuchElementException("Missing 'nativeWord' field in the JSON response");
            JsonNode targetWordNode = rootNode.path("targetWord");
            if (targetWordNode.isMissingNode())
                throw new NoSuchElementException("Missing 'targetWord' field in the JSON response");
            JsonNode targetSentenceNode = rootNode.path("targetSentence");
            if (targetSentenceNode.isMissingNode())
                throw new NoSuchElementException("Missing 'targetSentence' field in the JSON response");

            // Encapsulate in WordFlashcard object and return
            return new WordFlashcard(
                    nativeWordNode.asText(),
                    targetWordNode.asText(),
                    targetSentenceNode.asText()
            );
        } catch (Exception ex) {
            throw new JsonParseException("Failed to parse WordFlashcard JSON");
        }
    }

    @Override
    public SentenceFlashcard parseSentenceFlashcard(String responseBody) throws JsonParseException {
        try {
            // Parse the JSON into a JsonNode tree
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Navigate the JSON tree to extract the required values
            JsonNode nativeSentenceNode = rootNode.path("nativeSentence");
            if (nativeSentenceNode.isMissingNode())
                throw new NoSuchElementException("Missing 'nativeSentence' field in the JSON response");
            JsonNode targetSentenceNode = rootNode.path("targetSentence");
            if (targetSentenceNode.isMissingNode())
                throw new NoSuchElementException("Missing 'targetSentence' field in the JSON response");

            // Encapsulate in SentenceFlashcard object and return
            return new SentenceFlashcard(
                    nativeSentenceNode.asText(),
                    targetSentenceNode.asText()
            );
        } catch (Exception ex) {
            throw new JsonParseException("Failed to parse SentenceFlashcard JSON");
        }
    }
}
