package net.rankelo.plugin.managers;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;

public class RankManager {

    private final RankEloPlugin plugin;
    private final Map<String, Integer> ranks = new LinkedHashMap<>();

    public RankManager(RankEloPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        ranks.clear();

        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("ranks");

        if (section == null) {
            return;
        }

        for (String rank : section.getKeys(false)) {
            ranks.put(rank, section.getInt(rank));
        }
    }

    public String getRank(int elo) {
        String currentRank =
                plugin.getConfig().getString("unranked-name", "Unranked");

        for (Map.Entry<String, Integer> entry : ranks.entrySet()) {
            if (elo >= entry.getValue()) {
                currentRank = entry.getKey();
            } else {
                break;
            }
        }

        return currentRank;
    }

    public int getRequiredElo(String rank) {
        return ranks.getOrDefault(rank, 0);
    }

    public Map<String, Integer> getRanks() {
        return new LinkedHashMap<>(ranks);
    }
  }
