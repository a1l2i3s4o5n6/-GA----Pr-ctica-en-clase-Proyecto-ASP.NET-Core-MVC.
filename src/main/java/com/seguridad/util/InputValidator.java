package com.seguridad.util;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern SQL_INJECTION_PATTERN =
            Pattern.compile("(?i).*('|--|;|\\b(UNION|DROP|DELETE|INSERT|UPDATE|SELECT|ALTER|CREATE|TRUNCATE|EXEC|XP_CMDSHELL)\\b).*");

    private static final Pattern SAFE_STRING = Pattern.compile("^[a-zA-Z0-9áéíóúñ\\s@.,_-]{1,200}$");

    public static boolean isSqlInjection(String input) {
        if (input == null) return false;
        return SQL_INJECTION_PATTERN.matcher(input).matches();
    }

    public static boolean isSafeString(String input) {
        if (input == null) return false;
        return SAFE_STRING.matcher(input).matches();
    }

    public static String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("[';\"\\\\]", "").trim();
    }
}
