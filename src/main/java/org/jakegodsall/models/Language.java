package org.jakegodsall.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.jakegodsall.models.enums.Genders;
import org.jakegodsall.models.enums.Tense;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Language {
    private String name;

    @JsonProperty("supports_stress")
    private boolean supportsStress;
    private List<Tense> tenses;
    private List<Genders> genders;

    @Override
    public String toString() {
        return "Language: " + this.name + "\n";
    }
}
