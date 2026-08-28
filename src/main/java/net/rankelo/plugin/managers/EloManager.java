package net.rankelo.plugin.managers;

import net.rankelo.plugin.RankEloPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

public class EloManager {

    private final RankEloPlugin plugin;
    private final RankManager rankManager;

    private final Map<UUID, Integer> eloData = new HashMap<>();

    private final File dataFile;
    private YamlConfiguration dataConfig;

    public EloManager(RankEloPlugin plugin) {
        this.plugin = plugin;
        this.rankManager = new RankManager(plugin);

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.dataFile = new File(
                plugin.getDataFolder(),
                "players.yml"
        );

        this.dataConfig =
                YamlConfiguration.loadConfiguration(dataFile);

        load();
    }

    public int getElo(UUID uuid) {
        return eloData.getOrDefault(
                uuid,
                plugin.getConfig().getInt(
                        "elo.default",
                        1000
                )
        );
    }

    public void setElo(UUID uuid, int elo) {
        int minimum = plugin.getConfig().getInt(
                "elo.min",
                0
        );

        eloData.put(
                uuid,
                Math.max(minimum, elo)
        );
    }

    public void addElo(UUID uuid, int amount) {
        setElo(
                uuid,
                getElo(uuid) + amount
        );
    }

    public void removeElo(UUID uuid, int amount) {
        setElo(
                uuid,
                getElo(uuid) - amount
        );
    }

    public String getRank(int elo) {
        return rankManager.getRank(elo);
    }

    public String getRank(UUID uuid) {
        return rankManager.getRank(
                getElo(uuid)
        );
    }

    public String getRankColor(String rank) {
        return rankManager.getRankColor(rank);
    }

    public String getRankColor(UUID uuid) {
        return rankManager.getRankColor(
                getRank(uuid)
        );
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public void reload() {
        rankManager.load();
        load();
    }

    public void load() {
        eloData.clear();

        if (!dataFile.exists()) {
            return;
        }

        dataConfig =
                YamlConfiguration.loadConfiguration(dataFile);

        if (!dataConfig.isConfigurationSection("players")) {
            return;
        }

        for (String key :
                dataConfig
                        .getConfigurationSection("players")
                        .getKeys(false)) {

            try {
                UUID uuid = UUID.fromString(key);

                int elo = dataConfig.getInt(
                        "players." + key
                );

                eloData.put(uuid, elo);

            } catch (IllegalArgumentException exception) {
                plugin.getLogger().warning(
                        "Invalid UUID in players.yml: "
                                + key
                );
            }
        }
    }

    public void save() {
        dataConfig = new YamlConfiguration();

        for (Map.Entry<UUID, Integer> entry :
                eloData.entrySet()) {

            dataConfig.set(
                    "players." + entry.getKey(),
                    entry.getValue()
            );
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException exception) {
            plugin.getLogger().severe(
                    "Failed to save players.yml: "
                            + exception.getMessage()
            );
        }
    }
}
