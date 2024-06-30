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

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/flashcard-generator.git
   cd flashcard-generator
   ```

2. Add your OpenAI API key
   
      Replace `your_open_api_key` in `GPTService.java` with your actual OpenAI API key.

4. Build the project

    ```bash
    mvn clean install
    ```

5. Run the application

    ```bash
    mvn exec:java -Dexec.mainClass="org.jakegodsall.MainCLI"
    ```

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
