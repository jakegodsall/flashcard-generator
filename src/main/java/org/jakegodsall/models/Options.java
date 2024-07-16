package org.jakegodsall.models;

import lombok.*;
import org.jakegodsall.models.enums.Gender;
import org.jakegodsall.models.enums.Tense;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Options {
    private Boolean isStress;
    private Tense tense;
    private Gender gender;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Selected Options:");
        if (isStress != null) {
            sb.append("\nStress: ").append(isStress);
        }
        sb.append("\nTense: ").append(tense.toString().toLowerCase());
        sb.append("\nGender: ").append(gender.toString().toLowerCase());
        return sb.toString();
    }
}
