package org.jakegodsall.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for directory-related operations.
 * Provides utilities for creating a hidden directory in the user's home directory.
 */
public class DirectoryUtils {

    private static final Logger logger = Logger.getLogger(DirectoryUtils.class.getName());

    /**
     * Creates a hidden directory in the user's home directory called ~/.flashcard-generator.
     *
     * @throws IOException if an I/O error occurs while creating the directory.
     */
    public static void createHiddenConfigDirectory(String configDir) throws IOException {
        File hiddenDir = new File(configDir);
        if (!hiddenDir.exists()) {
            if (!hiddenDir.mkdirs()) {
                logger.log(Level.SEVERE, "Failed to create hidden directory at: " + hiddenDir.getAbsolutePath());
                throw new IOException("Failed to create hidden directory at: " + hiddenDir);
            } else {
                logger.log(Level.INFO, "Hidden directory created at: " + hiddenDir.getAbsolutePath());
            }
        } else {
            logger.log(Level.INFO, "Hidden directory already exists at: " + hiddenDir.getAbsolutePath());
        }
    }

    /**
     * Tests whether the configuration directory already exists.
     *
     * @param configDir The path to the configuration directory.
     * @return a boolean representing whether the directory exists or not.
     */
    public static boolean hiddenConfigDirectoryExists(String configDir) {
        File hiddenDir = new File(configDir);
        return hiddenDir.exists();
    }
}
