package org.jakegodsall.utils;

import org.jakegodsall.config.ApiKeyConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryUtilsTest {

    private static String CONFIG_DIR = ApiKeyConfig.CONFIG_DIR;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        CONFIG_DIR = tempDir.toString() + "/.flashcard-generator";
    }

    @Test
    void createHiddenConfigDirectory_doesNotExist() {
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(CONFIG_DIR);
        });

        File hiddenDir = new File(CONFIG_DIR);
        assertTrue(hiddenDir.exists(), "The hidden directory should be created");
        assertTrue(hiddenDir.isDirectory(), "The hidden directory should be a directory");
    }

    @Test
    void createHiddenConfigDirectory_alreadyExists() {
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(CONFIG_DIR);
            DirectoryUtils.createHiddenConfigDirectory(CONFIG_DIR); // Call again to test existing directory case
        });

        File hiddenDir = new File(CONFIG_DIR);
        assertTrue(hiddenDir.exists(), "The hidden directory should exist");
        assertTrue(hiddenDir.isDirectory(), "The hidden directory should be a directory");
    }
}