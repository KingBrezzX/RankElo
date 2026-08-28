package net.rankelo.plugin.utils;

public final class NumberUtil {

    private NumberUtil() {
    }

    public static boolean isInteger(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static int parseInt(String value, int fallback) {
        if (!isInteger(value)) {
            return fallback;
        }

        return Integer.parseInt(value);
    }

    public static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
