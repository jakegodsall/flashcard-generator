package org.jakegodsall.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SentencePair {
    private String nativeSentence;
    private String targetSentence;
}
