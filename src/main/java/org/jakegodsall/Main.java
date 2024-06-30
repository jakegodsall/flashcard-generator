package org.jakegodsall;

import org.jakegodsall.config.LanguageConfig;
import org.jakegodsall.models.Language;

public class Main {
    public static void main(String[] args) {
        Language polish = LanguageConfig.getLanguage("pl");
        if (polish != null) {
            System.out.println("Language: " + polish.getName());
            System.out.println("Supports Stress: " + polish.isSupportsStress());
            System.out.println("Conjugation Tenses: " + polish.getTenses());
            System.out.println("Grammatical Gender: " + polish.getGenders());
        } else {
            System.out.println("Language code not found.");
        }
    }
}