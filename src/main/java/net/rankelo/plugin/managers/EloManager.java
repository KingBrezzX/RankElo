package net.rankelo.plugin.managers;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EloManager {

    private final RankEloPlugin plugin;
    private final Map<UUID, Integer> eloData = new HashMap<>();

    public EloManager(RankEloPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public int getElo(Player player) {
        return getElo(player.getUniqueId());
    }

    public int getElo(UUID uuid) {
        return eloData.getOrDefault(uuid, plugin.getConfig().getInt("elo.default", 0));
    }

    public void setElo(Player player, int elo) {
        setElo(player.getUniqueId(), elo);
    }

    public void setElo(UUID uuid, int elo) {
        int minimum = plugin.getConfig().getInt("elo.min", 0);
        eloData.put(uuid, Math.max(minimum, elo));
    }

    public void addElo(Player player, int amount) {
        setElo(player, getElo(player) + amount);
    }

    public void removeElo(Player player, int amount) {
        setElo(player, getElo(player) - amount);
    }

    public String getRank(Player player) {
        return getRank(getElo(player));
    }

    public String getRank(int elo) {
        FileConfiguration config = plugin.getConfig();

        String currentRank = config.getString("unranked-name", "Unranked");

        if (!config.isConfigurationSection("ranks")) {
            return currentRank;
        }

        for (String rank : config.getConfigurationSection("ranks").getKeys(false)) {
            int requiredElo = config.getInt("ranks." + rank);

            if (elo >= requiredElo) {
                currentRank = rank;
            }
        }

        return currentRank;
    }

    public void load() {
        eloData.clear();

        FileConfiguration data = plugin.getConfig();

        if (!data.isConfigurationSection("players")) {
            return;
        }

        for (String uuidString : data.getConfigurationSection("players").getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                int elo = data.getInt("players." + uuidString);
                eloData.put(uuid, elo);
            } catch (IllegalArgumentException ignored) {
                plugin.getLogger().warning("Invalid UUID in Elo data: " + uuidString);
            }
        }
    }

    public void save() {
        FileConfiguration data = plugin.getConfig();

        data.set("players", null);

        for (Map.Entry<UUID, Integer> entry : eloData.entrySet()) {
            data.set("players." + entry.getKey(), entry.getValue());
        }

        plugin.saveConfig();
    }
            }
