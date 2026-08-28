package net.rankelo.plugin.utils;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.ChatColor;

public final class MessageUtil {

    private MessageUtil() {
    }

    public static String color(String text) {
        if (text == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String get(
            RankEloPlugin plugin,
            String path
    ) {
        return color(
                plugin.getConfig().getString(path, "")
        );
    }

    public static String get(
            RankEloPlugin plugin,
            String path,
            String key,
            Object value
    ) {
        return replace(
                get(plugin, path),
                key,
                value
        );
    }

    public static String replace(
            String text,
            String key,
            Object value
    ) {
        if (text == null) {
            return "";
        }

        return text.replace(
                "%" + key + "%",
                String.valueOf(value)
        );
    }

    public static String format(
            String text,
            Object... values
    ) {
        if (text == null) {
            return "";
        }

        String result = text;

        for (int i = 0; i + 1 < values.length; i += 2) {
            result = result.replace(
                    "%" + values[i] + "%",
                    String.valueOf(values[i + 1])
            );
        }

        return color(result);
    }

    public static String prefix(
            RankEloPlugin plugin
    ) {
        return get(
                plugin,
                "messages.prefix"
        );
    }
}
