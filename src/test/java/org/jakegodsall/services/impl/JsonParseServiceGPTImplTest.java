package org.jakegodsall.services.impl;

import com.fasterxml.jackson.core.JsonParseException;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.JsonParseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class JsonParseServiceGPTImplTest {

    private JsonParseService jsonParseService;

    @BeforeEach
    public void setUp() {
        jsonParseService = new JsonParseServiceGPTImpl();
    }

    @Test
    public void testParseWordFlashcard_Success() throws JsonParseException {
        String json = "{ \"nativeWord\": \"hello\", \"targetWord\": \"hola\", \"targetSentence\": \"Hola, ¿cómo estás?\" }";

        WordFlashcard flashcard = jsonParseService.parseWordFlashcard(json);

        assertNotNull(flashcard);
        assertEquals("hello", flashcard.getNativeWord());
        assertEquals("hola", flashcard.getTargetWord());
        assertEquals("Hola, ¿cómo estás?", flashcard.getExampleTargetSentence());
    }

    @Test
    public void testParseWordFlashcard_MissingNativeWord() {
        String json = "{ \"targetWord\": \"hola\", \"targetSentence\": \"Hola, ¿cómo estás?\" }";

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseWordFlashcard(json);
        });

        assertEquals("Missing 'nativeWord' field in the JSON response", exception.getMessage());
    }

    @Test
    public void testParseWordFlashcard_InvalidJson() {
        String invalidJson = "{ \"nativeWord\": \"hello\", \"targetWord\": \"hola\", ";

        Exception exception = assertThrows(JsonParseException.class, () -> {
            jsonParseService.parseWordFlashcard(invalidJson);
        });

        assertEquals("Failed to parse WordFlashcard JSON", exception.getMessage());
    }

    // Test for parseSentenceFlashcard

    @Test
    public void testParseSentenceFlashcard_Success() throws JsonParseException {
        String json = "{ \"nativeSentence\": \"Hello, how are you?\", \"targetSentence\": \"Hola, ¿cómo estás?\" }";

        SentenceFlashcard flashcard = jsonParseService.parseSentenceFlashcard(json);

        assertNotNull(flashcard);
        assertEquals("Hello, how are you?", flashcard.getNativeSentence());
        assertEquals("Hola, ¿cómo estás?", flashcard.getTargetSentence());
    }

    @Test
    public void testParseSentenceFlashcard_MissingNativeSentence() {
        String json = "{ \"targetSentence\": \"Hola, ¿cómo estás?\" }";

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            jsonParseService.parseSentenceFlashcard(json);
        });

        assertEquals("Missing 'nativeSentence' field in the JSON response", exception.getMessage());
    }

    @Test
    public void testParseSentenceFlashcard_InvalidJson() {
        String invalidJson = "{ \"nativeSentence\": \"Hello, how are you?\", ";

        Exception exception = assertThrows(JsonParseException.class, () -> {
            jsonParseService.parseSentenceFlashcard(invalidJson);
        });

        assertEquals("Failed to parse SentenceFlashcard JSON", exception.getMessage());
    }
}