package org.jakegodsall.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void capitalizeFirstLetter() {
        String input = "this is a test string";
        String expectedOutput = "This is a test string";

        String actualOutput = StringUtils.capitalizeFirstLetter(input);

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void createJsonComponent() {
        String key = "mykey";
        String value = "myvalue";

        String actualOutput = StringUtils.createJsonComponent(key, value);

        assertThat(actualOutput).isEqualTo("\"mykey\": \"myvalue\"");
    }
}