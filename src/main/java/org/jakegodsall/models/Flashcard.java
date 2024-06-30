package org.jakegodsall.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class Flashcard {
    String nativeWord;
    String targetWord;
    String targetSentence;
}
