package org.jakegodsall.services.impl;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.flashcard.FlashcardService;
import org.jakegodsall.services.input.impl.InputServiceInteractiveMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InputServiceInteractiveModeTest {

    private BufferedReader bufferedReader;
    private FlashcardService flashcardService;
    private InputServiceInteractiveMode inputService;

    private Language testLanguage;
    private Options testOptions;

    @BeforeEach
    public void setUp() {
        bufferedReader = Mockito.mock(BufferedReader.class);
        flashcardService = Mockito.mock(FlashcardService.class);
        inputService = new InputServiceInteractiveMode(bufferedReader, flashcardService);

        testLanguage = Language.builder().build();
        testOptions = Options.builder().build();
    }

    @Test
    public void getInput_endWithExitCommand() throws IOException {
        // Arrange
        when(bufferedReader.readLine())
                .thenReturn("word1")
                .thenReturn("word2")
                .thenReturn("-1");

        SentenceFlashcard flashcard1 = new SentenceFlashcard();
        SentenceFlashcard flashcard2 = new SentenceFlashcard();

        when(flashcardService.getSentenceFlashcard("word1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getSentenceFlashcard("word2", testLanguage, testOptions))
                .thenReturn(flashcard2);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions);

        // Assert
        assertEquals(2, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard2, flashcards.get(1));

        verify(bufferedReader, times(3)).readLine();
        verify(flashcardService).getSentenceFlashcard("word1", testLanguage, testOptions);
        verify(flashcardService).getSentenceFlashcard("word2", testLanguage, testOptions);
    }

    @Test
    public void getInput_emptyListWhenExitImmediately() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("-1");

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions);

        // Assert
        assertEquals(0, flashcards.size());

        verify(bufferedReader, times(1)).readLine();
        verifyNoInteractions(flashcardService);
    }

    @Test
    public void getInput_handleIOException() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenThrow(new IOException("IO Error"));

        // Act & Assert
        IOException exception = org.junit.jupiter.api.Assertions.assertThrows(
                IOException.class,
                () -> inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions)
        );

        assertEquals("IO Error", exception.getMessage());

        verify(bufferedReader, times(1)).readLine();
        verifyNoInteractions(flashcardService);
    }

    @Test
    public void getInput_withDifferentFlashcardTypes() throws IOException {
        // Arrange
        when(bufferedReader.readLine())
                .thenReturn("word1")
                .thenReturn("-1");

        SentenceFlashcard flashcard = new SentenceFlashcard();

        when(flashcardService.getSentenceFlashcard("word1", testLanguage, testOptions))
                .thenReturn(flashcard);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions);

        // Assert
        assertEquals(1, flashcards.size());
        assertEquals(flashcard, flashcards.getFirst());

        verify(bufferedReader, times(2)).readLine();
        verify(flashcardService).getSentenceFlashcard("word1", testLanguage, testOptions);
    }

    @Test
    public void getInput_withWordFlashcardType() throws IOException {
        // Arrange
        when(bufferedReader.readLine())
                .thenReturn("word1")
                .thenReturn("word2")
                .thenReturn("-1");

        WordFlashcard flashcard1 = new WordFlashcard(); // Assuming you have a no-arg constructor
        WordFlashcard flashcard2 = new WordFlashcard(); // Assuming you have a no-arg constructor

        when(flashcardService.getWordFlashcard("word1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getWordFlashcard("word2", testLanguage, testOptions))
                .thenReturn(flashcard2);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.WORD, testLanguage, testOptions);

        // Assert
        assertEquals(2, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard2, flashcards.get(1));

        verify(bufferedReader, times(3)).readLine();
        verify(flashcardService).getWordFlashcard("word1", testLanguage, testOptions);
        verify(flashcardService).getWordFlashcard("word2", testLanguage, testOptions);
    }
}