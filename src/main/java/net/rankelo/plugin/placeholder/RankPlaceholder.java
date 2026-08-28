package net.rankelo.plugin.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.rankelo.plugin.RankEloPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankPlaceholder extends PlaceholderExpansion {

    private final RankEloPlugin plugin;

    public RankPlaceholder(RankEloPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rankelo";
    }

    @Override
    public @NotNull String getAuthor() {
        return "RankElo";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("elo")) {
            return String.valueOf(plugin.getEloManager().getElo(player));
        }

        if (params.equalsIgnoreCase("rank")) {
            return plugin.getEloManager().getRank(player);
        }

        return null;
    }
}
