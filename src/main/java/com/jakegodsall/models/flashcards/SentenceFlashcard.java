package com.jakegodsall.models.flashcards;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import com.jakegodsall.utils.StringUtils;

/**
 * The {@code SentenceFlashcard} class represents a flashcard containing
 * a sentence in both the native language (e.g., English) and the target language.
 * It extends the {@link Flashcard} class and provides fields for the native
 * and translated sentences, with support for CSV binding and JSON serialization.
 *
 * <p>The class is annotated for use with libraries like OpenCSV for CSV handling,
 * Lombok for automatic getter, setter, and constructor generation, and Jackson
 * for JSON serialization with a specified property order.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonPropertyOrder({ "nativeSentence", "targetSentence" })
public class SentenceFlashcard extends Flashcard {
    /**
     * A description of the flashcard's content.
     */
    public static final String DESCRIPTION_OF_CONTENT = "A basic sentence in the native English language, and that sentence translated into the target language";

    /**
     * A JSON structure template for creating flashcards.
     * It uses the {@link StringUtils#createJsonComponent} utility to generate the components.
     */
    public static final String JSON_STRUCTURE_FOR_PROMPT = "{\n" +
        StringUtils.createJsonComponent("nativeSentence", "<sentence in native English language>") + ",\n" +
        StringUtils.createJsonComponent("targetSentence", "<sentence in target language>") + "\n" +
        "}\n";

    /**
     * The sentence in the native language (e.g., English).
     */
    @CsvBindByName(column = "nativeSentence")
    @CsvBindByPosition(position = 0)
    private String nativeSentence;

    /**
     * The sentence translated into the target language.
     */
    @CsvBindByName(column = "targetSentence")
    @CsvBindByPosition(position = 1)
    private String targetSentence;

    /**
     * Returns a string representation of the flashcard,
     * displaying the native and target sentences.
     */
    @Override
    public String toString() {
        return nativeSentence + ", " + targetSentence;
    }
}
