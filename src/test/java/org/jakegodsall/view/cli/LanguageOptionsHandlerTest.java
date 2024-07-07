package org.jakegodsall.view.cli;

import org.jakegodsall.models.Language;
import org.jakegodsall.models.enums.Gender;
import org.jakegodsall.models.enums.Tense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageOptionsHandlerTest {

    private LanguageOptionsHandler languageOptionsHandler;

    @BeforeEach
    void setUp() {
        languageOptionsHandler = new LanguageOptionsHandler(new Language(
            "Spanish", false, List.of(Tense.PAST, Tense.PRESENT, Tense.FUTURE),
            List.of(Gender.MASCULINE, Gender.FEMININE)
        ));
    }

    @Test
    void getStress_correctInputLowerCase() throws IOException {
        String input = "y";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        assertThat(languageOptionsHandler.getStress(bufferedReader))
                .isTrue();
    }

    @Test
    void getStress_correctInputUpperCase() throws IOException {
        String input = "Y";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        assertThat(languageOptionsHandler.getStress(bufferedReader))
                .isTrue();
    }

    @Test
    void getTense_correctInputLowerCase() throws IOException {
        String input = "past";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        assertThat(languageOptionsHandler.getTense(bufferedReader))
                .isEqualTo(Tense.PAST);
    }

    @Test
    void getTense_correctInputUpperCase() throws IOException {
        String input = "FUTURE";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        assertThat(languageOptionsHandler.getTense(bufferedReader))
                .isEqualTo(Tense.FUTURE);
    }
}