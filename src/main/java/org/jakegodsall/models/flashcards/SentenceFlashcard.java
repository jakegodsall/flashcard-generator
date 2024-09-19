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
@JsonPropertyOrder({ "nativeSentence", "targetSentence" })
public class SentenceFlashcard extends Flashcard {
    public static final String JSON_STRUCTURE_FOR_PROMPT = "{\n" +
        StringUtils.createJsonComponent("nativeSentence", "<sentence in native English language>") + ",\n" +
        StringUtils.createJsonComponent("targetSentence", "<sentence in target language>") + "\n" +
        "}\n";

    @CsvBindByName(column = "nativeSentence")
    @CsvBindByPosition(position = 0)
    private String nativeSentence;

    @CsvBindByName(column = "targetSentence")
    @CsvBindByPosition(position = 1)
    private String targetSentence;

    @Override
    public String toString() {
        return nativeSentence + ", " + targetSentence;
    }
}
