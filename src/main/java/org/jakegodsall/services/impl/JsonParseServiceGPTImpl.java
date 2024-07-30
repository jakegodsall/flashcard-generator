package org.jakegodsall.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.jakegodsall.services.JsonParseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
    public String parseSentence(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode choicesNode = rootNode.path("choices");

        if (!choicesNode.isArray() || choicesNode.isEmpty()) {
            throw new NoSuchElementException("Missing or empty 'choices' field in the JSON response");
        }

        JsonNode messageNode = choicesNode.get(0).path("message");
        if (messageNode.isMissingNode()) {
            throw new NoSuchElementException("Missing 'message' field in the JSON response");
        }

        JsonNode contentNode = messageNode.path("content");
        if (contentNode.isMissingNode()) {
            throw new NoSuchElementException("Missing 'content' field in the JSON response");
        }

        return contentNode.asText();
    }
}
