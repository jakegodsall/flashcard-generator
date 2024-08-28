package org.jakegodsall.utils;

import org.jakegodsall.models.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mockStatic;

class FilenameUtilsTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private Language language;

    @BeforeEach
    public void setUp() {
        language = Language.builder().name("english").build();
    }

    @Test
    public void testGenerateFilename_withValidInputs() {
        // Arrange
        String extension = "csv";
        LocalDateTime fixedTime = LocalDateTime.of(2023, 8, 28, 15, 45, 30);

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedTime);

            // Act
            String filename = FilenameUtils.generateFilename(language, extension);

            // Assert
            String expectedTimestamp = fixedTime.format(FORMATTER);
            String expectedFilename = String.format("%s_english_flashcards.csv", expectedTimestamp);
            assertThat(filename).isEqualTo(expectedFilename);
        }
    }

    @Test
    public void testGenerateFilename_withDifferentLanguage() {
        // Arrange
        String extension = "json";
        Language differentLanguage = Language.builder().name("spanish").build();
        LocalDateTime fixedTime = LocalDateTime.of(2023, 8, 28, 15, 45, 30);

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedTime);

            // Act
            String filename = FilenameUtils.generateFilename(differentLanguage, extension);

            // Assert
            String expectedTimestamp = fixedTime.format(FORMATTER);
            String expectedFilename = String.format("%s_spanish_flashcards.json", expectedTimestamp);
            assertThat(filename).isEqualTo(expectedFilename);
        }
    }

    @Test
    public void testGenerateFilename_withDifferentExtension() {
        // Arrange
        String extension = "xlsx";
        LocalDateTime fixedTime = LocalDateTime.of(2023, 8, 28, 15, 45, 30);

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedTime);

            // Act
            String filename = FilenameUtils.generateFilename(language, extension);

            // Assert
            String expectedTimestamp = fixedTime.format(FORMATTER);
            String expectedFilename = String.format("%s_english_flashcards.xlsx", expectedTimestamp);
            assertThat(filename).isEqualTo(expectedFilename);
        }
    }
}