package org.jakegodsall.models;

import lombok.*;
import org.jakegodsall.models.enums.Genders;
import org.jakegodsall.models.enums.Tense;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class Language {
    private String name;
    private boolean supportsStress;
    private List<Tense> tenses;
    private List<Genders> genders;
}
