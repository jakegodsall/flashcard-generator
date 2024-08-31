package org.jakegodsall.models.flashcards;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@JsonPropertyOrder({ "nativeWord", "targetWord", "exampleTargetSentence" })
public class WordFlashcard extends Flashcard {

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
