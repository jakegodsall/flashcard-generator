package org.jakegodsall.models.flashcards;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class WordFlashcard {
    String nativeWord;
    String targetWord;
    String exampleTargetSentence;

    @Override
    public String toString() {
        return nativeWord + ": " + targetWord;
    }
}
