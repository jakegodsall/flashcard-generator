package org.jakegodsall.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryUtilsTest {

    @TempDir
    Path tempDir;

    private String configDirPath;

    @BeforeEach
    void setUp() {
        configDirPath = tempDir.toString() + "/.flashcard-generator";
    }

    @Test
    void createHiddenConfigDirectory_doesNotAlreadyExist() {
        File hiddenDir = new File(configDirPath);

        // Test that the directory is created successfully
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(configDirPath);
        });

        assertTrue(hiddenDir.exists(), "The hidden directory should be created");
        assertTrue(hiddenDir.isDirectory(), "The hidden directory should be a directory");

        // Test that the method does not throw an exception if the directory already exists
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(configDirPath);
        });;
    }

    @Test
    void createHiddenConfigDirectory_alreadyExists() {
        File hiddenDir = new File(configDirPath);

        // create the directory, and then create it again (with it already existing)
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(configDirPath);
            DirectoryUtils.createHiddenConfigDirectory(configDirPath); // Call again to test existing directory case
        });

        assertTrue(hiddenDir.exists(), "The hidden directory should exist");
        assertTrue(hiddenDir.isDirectory(), "The hidden directory should be a directory");
    }

    @Test
    void hiddenConfigDirectoryExists_exists() {
        File hiddenDir = new File(configDirPath);

        // Create the directory
        assertDoesNotThrow(() -> {
            DirectoryUtils.createHiddenConfigDirectory(configDirPath);
        });

        // Test that the directory exists
        assertThat(DirectoryUtils.hiddenConfigDirectoryExists(configDirPath))
                .isTrue();
    }

    @Test
    void hiddenConfigDirectoryExists_doesNotExist() {
        assertThat(DirectoryUtils.hiddenConfigDirectoryExists(configDirPath))
                .isFalse();
    }
}