package com.jakegodsall.utils;

public class StringUtils {
    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase()
                + string.substring(1).toLowerCase();
    }

    public static String createJsonComponent(String key, String value) {
        return "\"" + key + "\": \"" + value + "\"";
    }
}
