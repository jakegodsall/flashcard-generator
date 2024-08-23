package org.jakegodsall.view.cli;

import org.jakegodsall.config.impl.ApiKeyConfigImpl;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.enums.InputMode;
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
        BufferedReader bufferedReader = new BufferedReader(new StringReader("1\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.INTERACTIVE, result, "Expected mode to be INTERACTIVE");
    }

    @Test
    void getMode_commaSeparatedStringMode() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("2\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.COMMA_SEPARATED_STRING, result, "Expected mode to be COMMA_SEPARATED_STRING");
    }

    @Test
    void getMode_plainTextFileMode() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("3\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.PLAIN_TEXT_FILE, result, "Expected mode to be PLAIN_TEXT_FILE");
    }

    @Test
    void getMode_invalidThenInteractiveMode() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("4\n1\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.INTERACTIVE, result, "Expected mode to be INTERACTIVE after invalid input");
    }

    @Test
    void getMode_invalidThenFileMode() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("0\n3\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.PLAIN_TEXT_FILE, result, "Expected mode to be PLAIN_TEXT_FILE after invalid input");
    }


    @Test
    void getFlashcardType_word() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("1\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.WORD, result, "Expected flashcard type to be WORD");
    }

    @Test
    void getFlashcardType_sentence() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("2\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.SENTENCE, result, "Expected flashcard type to be SENTENCE");
    }

    @Test
    void getFlashcardType_InvalidInputThenWord() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("3\n1\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.WORD, result, "Expected flashcard type to be WORD after invalid input");
    }

    @Test
    void getFlashcardType_InvalidInputThenSentence() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("0\n2\n"));
        FlashcardType result = commandLineInterface.getFlashcardType(bufferedReader);
        assertEquals(FlashcardType.SENTENCE, result, "Expected flashcard type to be SENTENCE after invalid input");
    }
}