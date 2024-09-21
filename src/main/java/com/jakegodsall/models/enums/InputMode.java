package com.jakegodsall.models.enums;

/**
 * This class represents the input mode for the flashcard generator.
 */
public enum InputMode {
    /**
     * Represents interactive input mode.
     */
    INTERACTIVE,

    /**
     * Represents input mode where data is provided as a comma-separated string.
     */
    COMMA_SEPARATED_STRING,

    /**
     * Represents input mode where data is provided from a plain text file.
     */
    PLAIN_TEXT_FILE
}