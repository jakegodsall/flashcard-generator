package org.jakegodsall.services;

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
     * Retrieves a List of {@link Flashcard} objects from the input source.
     * The method of input retrieval (e.g., user input, file reading) is
     * determined by the specific implementation of this interface.
     *
     * @return a {@code List} of {@link Flashcard} objects representing the
     *         input data.
     */
    List<Flashcard> getInput(FlashcardType flashcardType, Language chosenLanguage, Options selectedOptions) throws IOException;
}
