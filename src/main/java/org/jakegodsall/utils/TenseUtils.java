package org.jakegodsall.utils;

import org.jakegodsall.models.enums.Tense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenseUtils {

    public static Map<String, Tense> getTenseMap(List<Tense> tenses) {
        Map<String, Tense> tenseMap = new HashMap<>();
        for (Tense tense : tenses) {
            String name = StringUtils.capitalizeFirstLetter(tense.name());
            tenseMap.put(name, tense);
        }
        return tenseMap;
    }
}
