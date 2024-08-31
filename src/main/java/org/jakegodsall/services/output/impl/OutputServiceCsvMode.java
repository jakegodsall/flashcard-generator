package org.jakegodsall.services.output.impl;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.jakegodsall.models.flashcards.Flashcard;
import org.jakegodsall.services.output.OutputService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class OutputServiceCsvMode implements OutputService {

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
