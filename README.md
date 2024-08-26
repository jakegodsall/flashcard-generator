# Flashcard Generator

This project is a flashcard generator application that uses the GPT API to create basic sentences using provided words in different languages. It helps in learning languages by generating example sentences. The application supports various languages and can export the generated sentences to a CSV file.

## Features

- Generate flashcards using a specified word or set of words in various languages.
- Support for different language-specific options, such as choosing grammatical tense or including stress symbols.
- Command-line interface for easy interaction.
- Configuration for language-specific features via a JSON file.
- Export generated flashcards to multiple file formats (CSV, JSON).
- Easily extendable to support more languages..

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

## Application Modes

The Flashcard Generator application is designed to be flexible and user-friendly, offering multiple modes for both inputting data and exporting the generated flashcards. 
This flexibility allows you to choose the mode that best fits your workflow and preferences.

### Input Modes

1. **Interactive Mode:**  Interactive Mode is designed for step-by-step interaction. In this mode, the application prompts you to enter words or phrases one at a time. After you input each word or phrase, the application generates a flashcard for it using the OpenAI API. Each flashcard is printed to the console and stored in memory for exporting later.
2. **Comma Separated String Mode:** In Comma Separated String Mode, you provide a single string containing multiple words or phrases separated by commas. The application will then process each item in the string and generate flashcards for each one. Each flashcard is stored in memory for exporting later.
3. **Plain Text File Mode:** In Plain Text File Mode, you can input data by providing a plain text file where each line contains a word or phrase. The application reads the file and generates flashcards for each line. Each flashcard is stored in memory for exporting later.

### Output Modes

1. **CSV Mode:** Each flashcard is a separate line in a CSV file where fields are separated by commas.
2. **JSON Mode:** Flashcards are exported as a JSON file.

## Language Configuration

The Flashcard Generator application allows you to tailor the flashcard generation process to the nuances of different languages.

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

The choice of languages and the specific features that can be configured are heavily dependent on the capabilities of the underlying language model that the application uses. The language model's ability to accurately generate content in different languages and handle linguistic nuances like stress, gender, and tense is a key factor in determining how well the application can support each language.

## Future Plans

As the Flashcard Generator application continues to evolve, several exciting enhancements and new features are planned to improve its functionality and expand its capabilities. These future plans aim to make the application more versatile, user-friendly, and effective for a wider range of language learners.

- GUI: The next iteration of the application will include a GUI to further improve the user experience of the application.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or new features.

## License

This project is licensed under the MIT License.
