# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]
### Added

### Fixed

## [2.1] - 2024-09-21
### Added
- Added `generateFlashcardsConcurrenty` method to `FlashcardService` for concurrent flashcard generation.
- Added `generateFlashcardsInteractively` method to `FlashcardService` for an interactive mode of flashcard generation.
### Fixed
- Fixed the `FilenameUtils.generateFilename()` to test whether the user provided a file extension with preceeding `.`.
- Fixed the `InputServiceInteractiveMode` to return a singleton `List`.

## [2.0] - 2024-09-19
### Added
- Refactored services to use a single method for all `FlashcardType` rather than relying on separate implementations.

## [1.0] - 2024-09-02
### Added
- Initial release of the flashcard generator application.
- GPT API integration.
- InputService for interactive mode, comma-separated string mode and plain-text file mode.
- OutputService for CSV and JSON.