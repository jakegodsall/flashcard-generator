package org.jakegodsall.services.input.impl;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.models.flashcards.SentenceFlashcard;
import org.jakegodsall.models.flashcards.WordFlashcard;
import org.jakegodsall.services.flashcard.FlashcardService;
import org.jakegodsall.services.input.impl.impl.InputServiceCommaSeparatedStringMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InputServiceCommaSeparatedStringModeTest {
    private BufferedReader bufferedReader;
    private FlashcardService flashcardService;
    private InputServiceCommaSeparatedStringMode inputService;
    private Language testLanguage;
    private Options testOptions;

    @BeforeEach
    public void setUp() {
        bufferedReader = Mockito.mock(BufferedReader.class);
        flashcardService = Mockito.mock(FlashcardService.class);
        inputService = new InputServiceCommaSeparatedStringMode(bufferedReader, flashcardService);

        testLanguage = Language.builder().build();
        testOptions = Options.builder().build();
    }

    @Test
    public void getInput_withValidInputWords() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("word1, word2, word3");

        WordFlashcard flashcard1 = new WordFlashcard();
        WordFlashcard flashcard2 = new WordFlashcard();
        WordFlashcard flashcard3 = new WordFlashcard();

        when(flashcardService.getWordFlashcard("word1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getWordFlashcard("word2", testLanguage, testOptions))
                .thenReturn(flashcard2);
        when(flashcardService.getWordFlashcard("word3", testLanguage, testOptions))
                .thenReturn(flashcard3);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.WORD, testLanguage, testOptions);

        // Assert
        assertEquals(3, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard2, flashcards.get(1));
        assertEquals(flashcard3, flashcards.get(2));

        verify(bufferedReader, times(1)).readLine();
        verify(flashcardService).getWordFlashcard("word1", testLanguage, testOptions);
        verify(flashcardService).getWordFlashcard("word2", testLanguage, testOptions);
        verify(flashcardService).getWordFlashcard("word3", testLanguage, testOptions);
    }

    @Test
    public void getInput_withEmptyInput() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("");

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.WORD, testLanguage, testOptions);

        // Assert
        assertEquals(0, flashcards.size());

        verify(bufferedReader, times(1)).readLine();
        verifyNoInteractions(flashcardService);
    }

    @Test
    public void getInput_withExitCommand() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("exit");

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.WORD, testLanguage, testOptions);

        // Assert
        assertEquals(0, flashcards.size());

        verify(bufferedReader, times(1)).readLine();
        verifyNoInteractions(flashcardService);
    }

    @Test
    public void getInput_withMixedValidAndEmptyWords() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("word1, , word3");

        WordFlashcard flashcard1 = new WordFlashcard();
        WordFlashcard flashcard3 = new WordFlashcard();

        when(flashcardService.getWordFlashcard("word1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getWordFlashcard("word3", testLanguage, testOptions))
                .thenReturn(flashcard3);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.WORD, testLanguage, testOptions);

        // Assert
        assertEquals(2, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard3, flashcards.get(1));

        verify(bufferedReader, times(1)).readLine();
        verify(flashcardService).getWordFlashcard("word1", testLanguage, testOptions);
        verify(flashcardService).getWordFlashcard("word3", testLanguage, testOptions);
        verify(flashcardService, never()).getWordFlashcard("", testLanguage, testOptions);
    }

    @Test
    public void getInput_withValidSentenceFlashcardInput() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("sentence1, sentence2");

        SentenceFlashcard flashcard1 = new SentenceFlashcard(); // Assuming a no-arg constructor exists
        SentenceFlashcard flashcard2 = new SentenceFlashcard(); // Assuming a no-arg constructor exists

        when(flashcardService.getSentenceFlashcard("sentence1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getSentenceFlashcard("sentence2", testLanguage, testOptions))
                .thenReturn(flashcard2);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions);

        // Assert
        assertEquals(2, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard2, flashcards.get(1));

        verify(bufferedReader, times(1)).readLine();
        verify(flashcardService).getSentenceFlashcard("sentence1", testLanguage, testOptions);
        verify(flashcardService).getSentenceFlashcard("sentence2", testLanguage, testOptions);
    }

    @Test
    public void getInput_withMixedValidAndEmptyWordsSentenceType() throws IOException {
        // Arrange
        when(bufferedReader.readLine()).thenReturn("sentence1, , sentence3");

        SentenceFlashcard flashcard1 = new SentenceFlashcard(); // Assuming a no-arg constructor exists
        SentenceFlashcard flashcard3 = new SentenceFlashcard(); // Assuming a no-arg constructor exists

        when(flashcardService.getSentenceFlashcard("sentence1", testLanguage, testOptions))
                .thenReturn(flashcard1);
        when(flashcardService.getSentenceFlashcard("sentence3", testLanguage, testOptions))
                .thenReturn(flashcard3);

        // Act
        List<Flashcard> flashcards = inputService.getInput(FlashcardType.SENTENCE, testLanguage, testOptions);

        // Assert
        assertEquals(2, flashcards.size());
        assertEquals(flashcard1, flashcards.get(0));
        assertEquals(flashcard3, flashcards.get(1));

        verify(bufferedReader, times(1)).readLine();
        verify(flashcardService).getSentenceFlashcard("sentence1", testLanguage, testOptions);
        verify(flashcardService).getSentenceFlashcard("sentence3", testLanguage, testOptions);
        verify(flashcardService, never()).getSentenceFlashcard("", testLanguage, testOptions);
    }
}