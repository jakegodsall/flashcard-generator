package com.jakegodsall.utils;

import com.jakegodsall.models.enums.Tense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenseUtils {

    public static Map<String, Tense> getTenseMap(List<Tense> tenses) {
        Map<String, Tense> tenseMap = new HashMap<>();
        for (Tense tense : tenses) {
            tenseMap.put(tense.name().toLowerCase(), tense);
        }
        return tenseMap;
    }

    public static void printTenses(List<Tense> tenses) {
        for (Tense tense : tenses) {
            System.out.println("[" + StringUtils.capitalizeFirstLetter(tense.name()) + "]");
        }
    }
}
