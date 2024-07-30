package org.jakegodsall.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SentencePair {
    private String nativeSentence;
    private String targetSentence;
}
