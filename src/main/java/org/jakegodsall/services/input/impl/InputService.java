package org.jakegodsall.services.input.impl;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.Options;
import org.jakegodsall.models.enums.FlashcardType;
import org.jakegodsall.models.flashcards.Flashcard;

import java.io.IOException;
import java.util.List;

/**
 * The {@code InputService} interface defines the contract for services that
 * handle the input of flashcards. Implementations of this interface should
 * provide a method to retrieve a list of {@link Flashcard} objects from a
 * specified input source.
 *
 * <p>This interface is designed to be flexible, allowing for various input
 * sources such as interactive user input, file-based input, or string-based input.</p>
 */
public interface InputService {
    /**
     * Retrieves a list of {@link Flashcard} objects from the input source.
     * The method of input retrieval (e.g., user input, file reading) is
     * determined by the specific implementation of this interface.
     *
     * @param flashcardType    the type of flashcards to retrieve, which could be
     *                         based on words, sentences, or other types as defined by
     *                         the {@link FlashcardType} enumeration.
     * @param chosenLanguage   the language in which the flashcards should be retrieved,
     *                         specified by the {@link Language} enumeration.
     * @param selectedOptions  additional options that might influence how the
     *                         flashcards are retrieved or processed, encapsulated in the
     *                         {@link Options} object.
     * @return a {@code List} of {@link Flashcard} objects representing the
     *         input data.
     * @throws IOException if an I/O error occurs during input retrieval, such as
     *                     when reading from a file or interacting with user input.
     */
    List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException;
}
