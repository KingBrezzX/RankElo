package net.rankelo.plugin.utils;

import java.text.NumberFormat;
import java.util.Locale;

public final class FormatUtil {

    private FormatUtil() {
    }

    public static String number(int number) {
        return NumberFormat
                .getNumberInstance(Locale.US)
                .format(number);
    }

    public static String number(long number) {
        return NumberFormat
                .getNumberInstance(Locale.US)
                .format(number);
    }

    public static String compact(int number) {
        if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0);
        }

        if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000.0);
        }

        return String.valueOf(number);
    }
}
