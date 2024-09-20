package org.jakegodsall.models.flashcards;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import org.jakegodsall.utils.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@JsonPropertyOrder({ "nativeWord", "targetWord", "exampleTargetSentence" })
public class WordFlashcard extends Flashcard {
    public static final String DESCRIPTION_OF_CONTENT = "The word translated into the native language of English, the word itself, and a very basic sentence using the target word in the target language";
    public static final String JSON_STRUCTURE_FOR_PROMPT = "{\n" +
        StringUtils.createJsonComponent("nativeWord", "<word in native language>") + ",\n" +
        StringUtils.createJsonComponent("targetWord", "<word in target language>") + ",\n" +
        StringUtils.createJsonComponent("targetSentence", "<sentence in target language>") + "\n" +
        "}\n";

    @CsvBindByName(column = "nativeWord")
    @CsvBindByPosition(position = 0)
    String nativeWord;

    @CsvBindByName(column = "targetWord")
    @CsvBindByPosition(position = 1)
    String targetWord;

    @CsvBindByName(column = "exampleTargetSentence")
    @CsvBindByPosition(position = 2)
    String exampleTargetSentence;

    @Override
    public String toString() {
        return nativeWord + " - " + targetWord + " - " + exampleTargetSentence;
    }
}
