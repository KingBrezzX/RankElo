package net.rankelo.plugin.managers;

import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;

public class RankManager {

    private final RankEloPlugin plugin;
    private final Map<String, Integer> ranks = new LinkedHashMap<>();
    private final Map<String, String> colors = new LinkedHashMap<>();

    public RankManager(RankEloPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        ranks.clear();
        colors.clear();

        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("ranks");

        if (section == null) {
            return;
        }

        for (String rank : section.getKeys(false)) {
            ConfigurationSection rankSection =
                    section.getConfigurationSection(rank);

            if (rankSection == null) {
                continue;
            }

            ranks.put(
                    rank,
                    rankSection.getInt("elo")
            );

            colors.put(
                    rank,
                    rankSection.getString("color", "&7")
            );
        }
    }

    public String getRank(int elo) {
        String currentRank =
                plugin.getConfig().getString(
                        "unranked.name",
                        "Unranked"
                );

        for (Map.Entry<String, Integer> entry : ranks.entrySet()) {
            if (elo >= entry.getValue()) {
                currentRank = entry.getKey();
            } else {
                break;
            }
        }

        return currentRank;
    }

    public String getRankColor(String rank) {
        String color = colors.getOrDefault(rank, "&7");

        return ChatColor.translateAlternateColorCodes(
                '&',
                color
        );
    }

    public String getFormattedRank(int elo) {
        String rank = getRank(elo);

        if (!ranks.containsKey(rank)) {
            String color = plugin.getConfig().getString(
                    "unranked.color",
                    "&7"
            );

            return ChatColor.translateAlternateColorCodes(
                    '&',
                    color
            ) + rank;
        }

        return getRankColor(rank) + rank;
    }

    public int getRequiredElo(String rank) {
        return ranks.getOrDefault(rank, 0);
    }

    public Map<String, Integer> getRanks() {
        return new LinkedHashMap<>(ranks);
    }

    public Map<String, String> getColors() {
        return new LinkedHashMap<>(colors);
    }
            }
