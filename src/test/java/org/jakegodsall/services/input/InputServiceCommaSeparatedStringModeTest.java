package org.jakegodsall.services.input;

import org.jakegodsall.services.input.impl.InputServiceCommaSeparatedStringMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class InputServiceCommaSeparatedStringModeTest {
    private BufferedReader bufferedReader;
    private InputServiceCommaSeparatedStringMode inputService;

    @BeforeEach
    public void setUp() {
        bufferedReader = Mockito.mock(BufferedReader.class);
        inputService = new InputServiceCommaSeparatedStringMode(bufferedReader);
    }

    @Test
    public void getInput_withValidInputWords() throws IOException {
        given(bufferedReader.readLine()).willReturn("word1, word2, word3");

        List<String> words = inputService.getInput();

        assertThat(words).hasSize(3);
        assertThat(words.getFirst()).isEqualTo("word1");
        assertThat(words.get(1)).isEqualTo("word2");
        assertThat(words.getLast()).isEqualTo("word3");

        verify(bufferedReader, times(1)).readLine();
    }

    @Test
    public void getInput_withEmptyInput() throws IOException {
        given(bufferedReader.readLine()).willReturn("");

        List<String> words = inputService.getInput();

        assertThat(words).hasSize(0);

        verify(bufferedReader, times(1)).readLine();
    }

    @Test
    public void getInput_withExitCommand() throws IOException {
        given(bufferedReader.readLine()).willReturn("exit");

        List<String> words = inputService.getInput();

        assertThat(words).hasSize(0);

        verify(bufferedReader, times(1)).readLine();
    }

    @Test
    public void getInput_withMixedValidAndEmptyWords() throws IOException {
        given(bufferedReader.readLine()).willReturn("word1, , word3");

        List<String> words = inputService.getInput();

        assertThat(words).hasSize(2);
        assertThat(words.getFirst()).isEqualTo("word1");
        assertThat(words.getLast()).isEqualTo("word3");

        verify(bufferedReader, times(1)).readLine();
    }
}