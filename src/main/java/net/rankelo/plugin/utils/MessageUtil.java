package net.rankelo.plugin.utils;

import org.bukkit.ChatColor;

public final class MessageUtil {

    private MessageUtil() {
    }

    public static String color(String message) {
        if (message == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String replace(String message, String key, Object value) {
        if (message == null) {
            return "";
        }

        return message.replace(
                "%" + key + "%",
                String.valueOf(value)
        );
    }

    public static String format(String message, Object... replacements) {
        if (message == null) {
            return "";
        }

        String result = message;

        for (int i = 0; i + 1 < replacements.length; i += 2) {
            String key = String.valueOf(replacements[i]);
            String value = String.valueOf(replacements[i + 1]);

            result = result.replace(
                    "%" + key + "%",
                    value
            );
        }

        return color(result);
    }
}
