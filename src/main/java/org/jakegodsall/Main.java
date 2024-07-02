package org.jakegodsall;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;
import org.jakegodsall.view.CommandLineInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Language polish = LanguageConfig.getLanguage("pl");
//        if (polish != null) {
//            System.out.println("Language: " + polish.getName());
//            System.out.println("Supports Stress: " + polish.isSupportsStress());
//            System.out.println("Conjugation Tenses: " + polish.getTenses());
//            System.out.println("Grammatical Gender: " + polish.getGenders());
//        } else {
//            System.out.println("Language code not found.");
//        }
        CommandLineInterface cli = new CommandLineInterface();
        cli.main();
    }
}