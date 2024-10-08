package com.jakegodsall.services.output.impl;

import com.jakegodsall.models.flashcards.Flashcard;
import com.jakegodsall.services.output.OutputService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A concrete implementation of the {@link OutputService} interface that writes
 * flashcard data to a CSV file. This class utilizes the `StatefulBeanToCsv`
 * utility from the OpenCSV library to serialize a list of {@link Flashcard}
 * objects into CSV format and save them to a specified file path.
 */
@RequiredArgsConstructor
public class OutputServiceCsvMode implements OutputService {

    @Override
    public String serialiseToOutputFormat(List<Flashcard> flashcards) {
        return "";
    }

    /**
     * Writes a list of {@link Flashcard} objects to a CSV file at the specified file path.
     * This method uses the `StatefulBeanToCsv` class to convert the list of flashcards into
     * CSV format and write it to the file.
     *
     * @param flashcards the list of flashcards to be written to the CSV file
     * @param filePath the path of the file where the CSV data will be written
     * @throws IOException if an I/O error occurs during writing to the file
     */
    @Override
    public void writeToFile(List<Flashcard> flashcards, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<Flashcard> beanToCsv = new StatefulBeanToCsvBuilder<Flashcard>(writer).build();

            beanToCsv.write(flashcards);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println(e.getMessage());
        }
    }
}
