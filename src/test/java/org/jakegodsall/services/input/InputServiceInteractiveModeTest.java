package org.jakegodsall.services.input;

import org.jakegodsall.services.input.impl.InputServiceInteractiveMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InputServiceInteractiveModeTest {

    private BufferedReader bufferedReader;
    private InputServiceInteractiveMode inputService;

    @BeforeEach
    void setUp() {
        // Mock the BufferedReader for the tests
        bufferedReader = mock(BufferedReader.class);
        inputService = new InputServiceInteractiveMode(bufferedReader);
    }

    @Test
    void testNormalInput() throws IOException {
        // Simulate normal user input
        given(bufferedReader.readLine()).willReturn("test");

        List<String> result = inputService.getInput();

        // Verify that the correct input is returned in a singleton list
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo("test");
    }

    @Test
    void testInputWithLeadingAndTrailingSpaces() throws IOException {
        // Simulate input with leading and trailing spaces
        given(bufferedReader.readLine()).willReturn("  hello world  ");

        List<String> result = inputService.getInput();

        // Verify that the input is trimmed properly
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo("hello world");
    }

    @Test
    void testExitCommand() throws IOException {
        // Simulate the user entering the exit command
        given(bufferedReader.readLine()).willReturn("-1");

        List<String> result = inputService.getInput();

        // Verify that the exit command is returned
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo("-1");
    }

    @Test
    void testEmptyInput() throws IOException {
        // Simulate the user pressing Enter without input
        given(bufferedReader.readLine()).willReturn("");

        List<String> result = inputService.getInput();

        // Verify that an empty string is returned
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo("");
    }

    @Test
    void testIOException() throws IOException {
        // Simulate an IOException being thrown by BufferedReader
        given(bufferedReader.readLine()).willThrow(new IOException("Test IO Exception"));

        // Verify that the IOException is propagated
        assertThrows(IOException.class, () -> inputService.getInput());
    }
}