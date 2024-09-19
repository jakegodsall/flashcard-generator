package org.jakegodsall.services.json.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class JsonParseServiceGPTImplTest {

    private ObjectMapper objectMapper;
    private JsonParseServiceGPTImpl jsonParseService;

    @BeforeEach
    public void setUp() {
        objectMapper = Mockito.mock(ObjectMapper.class);
        jsonParseService = new JsonParseServiceGPTImpl(objectMapper);
    }

    // PARSE WORD FLASHCARD

    @Test
    public void parseWordFlashcard_missingNativeWordField() throws Exception {
        String responseBody = "{ \"choices\": [{ \"message\": { \"content\": " +
                "{ \"targetWord\": \"Hola\", \"targetSentence\": \"Hola, ¿cómo estás?\" }" +
                "} }] }";
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseWordFlashcard(responseBody);
        });
        assertEquals("Missing 'nativeWord' field in the JSON response", exception.getMessage());
    }

    @Test
    public void parseWordFlashcard_generalParsingException() throws Exception {
        String responseBody = "{ \"invalid-json\": \"invalid\" }";
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseWordFlashcard(responseBody);
        });
        assertEquals("Missing 'choices' field in the JSON response", exception.getMessage());
    }

    @Test
    public void parseWordFlashcard_missingContentInResponseBody_throwsNoSuchElementException() throws Exception {
        String responseBody = "{ \"choices\": [] }";  // Simulates missing 'content' field
        when(objectMapper.readTree(anyString())).thenThrow(new NoSuchElementException("Missing 'content' field in the JSON response"));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseWordFlashcard(responseBody);
        });
        assertEquals("Missing 'content' field in the JSON response", exception.getMessage());
    }

    @Test
    void testMalformedJson() throws Exception {
        // Malformed JSON input
        String malformedJson = "[{\"nativeWord\": \"dog\", \"targetWord\": \"pies\", \"targetSentence\": \"I have a dog.\"";

        // Mock ObjectMapper to throw a JsonProcessingException when parsing
        when(objectMapper.readTree(Mockito.anyString())).thenThrow(new RuntimeException("Invalid JSON"));

        // Call the method and expect a JsonParseException
        Exception exception = assertThrows(JsonParseException.class, () ->
                jsonParseService.parseWordFlashcardBatch(malformedJson));

        assertTrue(exception.getMessage().contains("Failed to parse batch of WordFlashcard JSON"));
    }

    // PARSE WORD BATCH FLASHCARDS

    @Test
    void parseBatchWordFlashcards_success() throws JsonProcessingException {
        String responseBody = "{ \"choices\": [{ \"message\": { \"content\":" +
                "[{\"nativeWord\": \"dog\", \"targetWord\": \"pies\", \"targetSentence\": \"I have a dog.\"}," +
                "{\"nativeWord\": \"cat\", \"targetWord\": \"kot\", \"targetSentence\": \"I have a cat.\"}]"
                + "} }] }";

        // Mock the JSON tree
        JsonNode rootNode = new ObjectMapper().readTree(responseBody); // use actual ObjectMapper for real parsing

        // Call the method
        List<Flashcard> result = jsonParseService.parseWordFlashcardBatch(responseBody);

        JsonNode rootNodeForResponseBody = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(Mockito.eq(responseBody))).thenReturn(rootNodeForResponseBody);

        System.out.println(result);

        // Assertions
        assertEquals(2, result.size());
        assertEquals("dog", ((WordFlashcard)result.get(0)).getNativeWord());
        assertEquals("kot", ((WordFlashcard)result.get(1)).getTargetWord());
    }

    // PARSE CONTENT FROM RESPONSE

    @Test
    public void parseContentFromResponse_validResponse_returnsContent() throws Exception {
        String responseBody = "{ \"choices\": [{ \"message\": { \"content\": \"This is the content\" } }] }";
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(responseBody)).thenReturn(rootNode);
        String result = jsonParseService.parseContentFromResponse(responseBody);
        assertEquals("This is the content", result);
    }

    @Test
    public void parseContentFromResponse_missingChoicesField_throwsNoSuchElementException() throws Exception {
        String responseBody = "{ \"otherField\": \"value\" }";  // No 'choices' field
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        System.out.println(rootNode);
        when(objectMapper.readTree(responseBody)).thenReturn(rootNode);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseContentFromResponse(responseBody);
        });
        assertEquals("Missing 'choices' field in the JSON response", exception.getMessage());
    }

    @Test
    public void parseContentFromResponse_missingMessageField_throwsNoSuchElementException() throws Exception {
        String responseBody = "{ \"choices\": [{}] }";  // 'choices' field present, but no 'message'
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(responseBody)).thenReturn(rootNode);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseContentFromResponse(responseBody);
        });
        assertEquals("Missing 'message' field in the JSON response", exception.getMessage());
    }

    @Test
    public void parseContentFromResponse_missingContentField_throwsNoSuchElementException() throws Exception {
        String responseBody = "{ \"choices\": [{ \"message\": {} }] }";  // 'message' field present, but no 'content'
        JsonNode rootNode = new ObjectMapper().readTree(responseBody);
        when(objectMapper.readTree(responseBody)).thenReturn(rootNode);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseContentFromResponse(responseBody);
        });
        assertEquals("Missing 'content' field in the JSON response", exception.getMessage());
    }

    @Test
    public void parseContentFromResponse_invalidJson_throwsJsonProcessingException() throws Exception {
        String responseBody = "invalid-json";
        when(objectMapper.readTree(responseBody)).thenThrow(new JsonProcessingException("Invalid JSON") {});
        JsonProcessingException exception = assertThrows(JsonProcessingException.class, () -> {
            jsonParseService.parseContentFromResponse(responseBody);
        });
        assertEquals("Invalid JSON", exception.getMessage());
    }
}