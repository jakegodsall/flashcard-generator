package com.jakegodsall.utils;

import com.jakegodsall.models.enums.Tense;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TenseUtilsTest {

    @Test
    void getTenseMap() {
        List<Tense> input = List.of(Tense.PAST, Tense.PRESENT, Tense.FUTURE);

        Map<String, Tense> expected = new HashMap<>();
        expected.put("past", Tense.PAST);
        expected.put("present", Tense.PRESENT);
        expected.put("future", Tense.FUTURE);

        Map<String, Tense> actual = TenseUtils.getTenseMap(input);

        assertThat(actual).isEqualTo(expected);
    }
}