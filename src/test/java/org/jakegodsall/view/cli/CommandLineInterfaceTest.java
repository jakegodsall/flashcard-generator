package org.jakegodsall.view.cli;

import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.enums.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLineInterfaceTest {

    private CommandLineInterface commandLineInterface;

    @BeforeEach
    void setUp() {
        commandLineInterface = new CommandLineInterface(new ApiKeyHandler(new ApiKeyConfigImpl()));
    }

    @Test
    void getMode_InteractiveMode() throws IOException {
        // Setup input as "1" to select Interactive Mode
        BufferedReader bufferedReader = new BufferedReader(new StringReader("1\n"));
        Mode result = commandLineInterface.getMode(bufferedReader);
        assertEquals(Mode.INTERACTIVE, result, "Expected mode to be INTERACTIVE");
    }

    @Test
    void getMode_FileMode() throws IOException {
        // Setup input as "2" to select File Mode
        BufferedReader bufferedReader = new BufferedReader(new StringReader("2\n"));
        Mode result = commandLineInterface.getMode(bufferedReader);
        assertEquals(Mode.FILE, result, "Expected mode to be FILE");
    }

    @Test
    void getMode_invalidThenInteractiveMode() throws IOException {
        // Setup input as invalid ("3") then valid ("1")
        BufferedReader bufferedReader = new BufferedReader(new StringReader("3\n1\n"));
        Mode result = commandLineInterface.getMode(bufferedReader);
        assertEquals(Mode.INTERACTIVE, result, "Expected mode to be INTERACTIVE after invalid input");
    }

    @Test
    void getMode_invalidThenFileMode() throws IOException {
        // Setup input as invalid ("0") then valid ("2")
        BufferedReader bufferedReader = new BufferedReader(new StringReader("0\n2\n"));
        Mode result = commandLineInterface.getMode(bufferedReader);
        assertEquals(Mode.FILE, result, "Expected mode to be FILE after invalid input");
    }


    @Test
    void getFlashcardType_word() throws IOException {
        // Setup input as "1" to select Word Flashcard
        BufferedReader bufferedReader = new BufferedReader(new StringReader("1\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.WORD, result, "Expected flashcard type to be WORD");
    }

    @Test
    void getFlashcardType_sentence() throws IOException {
        // Setup input as "2" to select Sentence Flashcard
        BufferedReader bufferedReader = new BufferedReader(new StringReader("2\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.SENTENCE, result, "Expected flashcard type to be SENTENCE");
    }

    @Test
    void getFlashcardType_InvalidInputThenWord() throws IOException {
        // Setup input as invalid ("3") then valid ("1")
        BufferedReader bufferedReader = new BufferedReader(new StringReader("3\n1\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.WORD, result, "Expected flashcard type to be WORD after invalid input");
    }

    @Test
    void getFlashcardType_InvalidInputThenSentence() throws IOException {
        // Setup input as invalid ("0") then valid ("2")
        BufferedReader bufferedReader = new BufferedReader(new StringReader("0\n2\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.SENTENCE, result, "Expected flashcard type to be SENTENCE after invalid input");
    }
}