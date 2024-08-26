# Flashcard Generator

This project is a flashcard generator application that uses the GPT API to create basic sentences using provided words in different languages. It helps in learning languages by generating example sentences. The application supports various languages and can export the generated sentences to a CSV file.

## Features

- Generate sentences using a specified word in various languages.
- Support for different language-specific options, such as choosing grammatical tense or including stress symbols for Russian.
- Command-line interface for easy interaction.
- Export generated sentences to a CSV file in the format: `native_language_word, foreign_language_word, example_sentence`.
- Easily extendable to support more languages and advanced sentence structures.
- Configuration for language-specific features via a JSON file.

## Requirements

- Java 22 or higher
- Maven
- An OpenAI API key

## Setup

1. Clone the repository

   ```bash
   git clone https://github.com/yourusername/flashcard-generator.git
   cd flashcard-generator
   ```

4. Build the project

    ```bash
    mvn clean package
    ```

5. Navigate to the `target/` directory and run the application.

    ```bash
    cd target/
   java -jar flashcard-generator-1.0-with-dependencies.jar
    ```
   
## API Key Management

When you first run the Flashcard Generator application, you will be prompted to enter your API key.

The Flashcard Generator application requires an OpenAI API key to access the language processing services that power the flashcard generation functionality.

### Storage

On the first run, the application will prompt you to enter your API key.

This key will then be stored in the `api_config.json` file within the `.flashcard-generator` directory located in your home directory.

For subsequent uses, the application will automatically read the API key from this file. You won’t need to enter the API key again unless you choose to modify it.

### Modifying the API Key

If you need to change or update your API key, you can do so by either modifying directly in the file, or deleting the file.

Once you delete this file and run the application again, you will again be prompted to add the API key.

### Important Note

It is important to keep your OpenAI API key secure and not share it with others. 

The API key allows access to your OpenAI account, and usage charges may apply depending on the OpenAI services you use through the application.

## Usage

1. Enter a word

    When prompted, enter the word you want to use in the sentence.

2. Enter the language code

    Provide the language code (e.g., en for English, ru for Russian, es for Spanish).

3. Language-specific options

    If applicable, you will be prompted for additional options such as including stress symbols for Russian.

4. View the generated sentence

    The application will generate and display a sentence using the provided word.

5. Export to CSV

    Generated sentences are saved in sentences.csv.

## Configuration

Language-specific features are configured via the `language_config.json` file located in the `src/main/resources` directory. This file includes options for stress symbols, formality, gender, dialects, politeness levels, and conjugation tenses.

Example `language_config.json`:

  ```json
  {
    "ru": {
      "name": "Russian",
      "supports_stress": true,
      "tenses": ["PAST", "PRESENT", "FUTURE"],
      "genders": ["MASCULINE", "FEMININE", "NEUTER"]
    },
    "pl": {
      "name": "Polish",
      "supports_stress": false,
      "tenses": ["PAST", "PRESENT", "FUTURE"],
      "genders": ["MASCULINE", "FEMININE", "NEUTER"]
    },
    "es": {
      "name": "Spanish",
      "supports_stress": false,
      "tenses": ["PAST", "PRESENT", "FUTURE"],
      "genders": ["MASCULINE", "FEMININE"]
    }
  }
  ```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or new features.

## License

This project is licensed under the MIT License.
