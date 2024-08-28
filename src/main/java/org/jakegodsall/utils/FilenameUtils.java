package org.jakegodsall.utils;

import org.jakegodsall.models.Language;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilenameUtils {
    /**
     * Generates a filename based on the provided language, flashcard type, and datetime.
     *
     * @param language       the language of the flashcards.
     * @param extension      the file extension (e.g., "csv", "json").
     * @return a formatted filename string.
     */
    public static String generateFilename(Language language, String extension) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String timestamp = currentTime.format(formatter);

        return String.format("%s_%s_%s.%s",
                timestamp,
                language.getName().toLowerCase(),
                "flashcards",
                extension);
    }
}
