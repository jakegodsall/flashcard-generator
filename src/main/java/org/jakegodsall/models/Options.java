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
    private boolean isStress;
    private Tense tense;
    private Gender gender;

    @Override
    public String toString() {
        return "Selected Options:\n"
                + "Stress: ";
    }
}
