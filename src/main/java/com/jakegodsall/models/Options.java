package com.jakegodsall.models;

import com.jakegodsall.models.enums.Gender;
import com.jakegodsall.models.enums.Tense;
import lombok.*;

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
