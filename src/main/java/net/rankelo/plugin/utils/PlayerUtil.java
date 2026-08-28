package net.rankelo.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerUtil {

    private PlayerUtil() {
    }

    public static Player find(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        Player player = Bukkit.getPlayerExact(name);

        if (player != null) {
            return player;
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().equalsIgnoreCase(name)) {
                return online;
            }
        }

        return null;
    }

    public static Player find(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        return Bukkit.getPlayer(uuid);
    }
}
