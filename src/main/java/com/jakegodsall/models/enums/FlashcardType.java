package com.jakegodsall.models.enums;

/**
 * Enum representing the type of flashcard.
 */
public enum FlashcardType {

    /**
     * A flashcard for learning new vocabulary words, represented by {@link com.jakegodsall.models.flashcards.WordFlashcard}.
     */
    WORD,

    /**
     * A flashcard for learning language in context, represented by {@link com.jakegodsall.models.flashcards.SentenceFlashcard}.
     */
    SENTENCE
}