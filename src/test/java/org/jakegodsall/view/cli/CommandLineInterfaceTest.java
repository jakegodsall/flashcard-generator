package org.jakegodsall.view.cli;

import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.enums.InputMode;
import org.jakegodsall.models.enums.OutputMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommandLineInterfaceTest {

    private BufferedReader bufferedReader;
    private CommandLineInterface commandLineInterface;

    @BeforeEach
    void setUp() {
        bufferedReader = Mockito.mock(BufferedReader.class);
        commandLineInterface = CommandLineInterfaceFactory.createGPTCommandLineInterface();
    }

    // GET INPUT MODE

    @Test
    void getInputMode_InteractiveMode() throws IOException {
        bufferedReader = new BufferedReader(new StringReader("1\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.INTERACTIVE, result, "Expected mode to be INTERACTIVE");
    }

    @Test
    void getInputMode_commaSeparatedStringMode() throws IOException {
        bufferedReader = new BufferedReader(new StringReader("2\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.COMMA_SEPARATED_STRING, result, "Expected mode to be COMMA_SEPARATED_STRING");
    }

    @Test
    void getInputMode_plainTextFileMode() throws IOException {
        bufferedReader = new BufferedReader(new StringReader("3\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.PLAIN_TEXT_FILE, result, "Expected mode to be PLAIN_TEXT_FILE");
    }

    @Test
    void getInputMode_invalidThenInteractiveMode() throws IOException {
        bufferedReader = new BufferedReader(new StringReader("4\n1\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.INTERACTIVE, result, "Expected mode to be INTERACTIVE after invalid input");
    }

    @Test
    void getInputMode_invalidThenFileMode() throws IOException {
        bufferedReader = new BufferedReader(new StringReader("0\n3\n"));
        InputMode result = commandLineInterface.getInputMode(bufferedReader);
        assertEquals(InputMode.PLAIN_TEXT_FILE, result, "Expected mode to be PLAIN_TEXT_FILE after invalid input");
    }

    // GET OUTPUT MODE

    @Test
    public void getOutputMode_withValidInputCSV() throws IOException {
        when(bufferedReader.readLine()).thenReturn("1");
        OutputMode result = commandLineInterface.getOutputMode(bufferedReader);
        assertEquals(OutputMode.CSV, result);
        verify(bufferedReader, times(1)).readLine();
    }

    @Test
    public void getOutputMode_withValidInputJSON() throws IOException {
        when(bufferedReader.readLine()).thenReturn("2");
        OutputMode result = commandLineInterface.getOutputMode(bufferedReader);
        assertEquals(OutputMode.JSON, result);
        verify(bufferedReader, times(1)).readLine();
    }

    @Test
    public void getOutputMode_withInvalidInputThenValidCSV() throws IOException {
        // Arrange
        when(bufferedReader.readLine())
                .thenReturn("invalid")
                .thenReturn("1");
        OutputMode result = commandLineInterface.getOutputMode(bufferedReader);
        assertEquals(OutputMode.CSV, result);
        verify(bufferedReader, times(2)).readLine();
    }

    @Test
    public void getOutputMode_withInvalidInputThenValidJSON() throws IOException {
        when(bufferedReader.readLine())
                .thenReturn("invalid")
                .thenReturn("2");
        OutputMode result = commandLineInterface.getOutputMode(bufferedReader);
        assertEquals(OutputMode.JSON, result);
        verify(bufferedReader, times(2)).readLine();
    }

    // GET FLASHCARD TYPE

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