package net.rankelo.plugin.utils;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.World;

import java.util.List;

public final class WorldUtil {

    private WorldUtil() {
    }

    public static boolean isEnabled(
            RankEloPlugin plugin,
            World world
    ) {
        if (world == null) {
            return false;
        }

        String worldName = world.getName();

        List<String> disabled =
                plugin.getConfig().getStringList("disabled-worlds");

        if (disabled.stream()
                .anyMatch(name -> name.equalsIgnoreCase(worldName))) {
            return false;
        }

        List<String> enabled =
                plugin.getConfig().getStringList("enabled-worlds");

        if (enabled.isEmpty()) {
            return true;
        }

        return enabled.stream()
                .anyMatch(name -> name.equalsIgnoreCase(worldName));
    }
            }
