package net.rankelo.plugin.utils;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.configuration.ConfigurationSection;

public final class ConfigUtil {

    private ConfigUtil() {
    }

    public static int getInt(
            RankEloPlugin plugin,
            String path,
            int fallback
    ) {
        if (!plugin.getConfig().contains(path)) {
            return fallback;
        }

        return plugin.getConfig().getInt(path, fallback);
    }

    public static String getString(
            RankEloPlugin plugin,
            String path,
            String fallback
    ) {
        return plugin.getConfig().getString(path, fallback);
    }

    public static boolean hasSection(
            RankEloPlugin plugin,
            String path
    ) {
        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection(path);

        return section != null;
    }
}
