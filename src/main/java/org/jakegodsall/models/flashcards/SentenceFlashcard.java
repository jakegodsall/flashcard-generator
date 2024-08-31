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
@JsonPropertyOrder({ "nativeSentence", "targetSentence" })
public class SentenceFlashcard extends Flashcard {

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
