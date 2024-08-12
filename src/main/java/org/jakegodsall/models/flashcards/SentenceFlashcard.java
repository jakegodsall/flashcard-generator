package org.jakegodsall.models.flashcards;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SentenceFlashcard {
    private String nativeSentence;
    private String targetSentence;

    @Override
    public String toString() {
        return nativeSentence + ", " + targetSentence;
    }
}
