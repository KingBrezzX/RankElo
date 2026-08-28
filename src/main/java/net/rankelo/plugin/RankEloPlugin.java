package net.rankelo.plugin;

import net.rankelo.plugin.commands.EloCommand;
import net.rankelo.plugin.listeners.CombatListener;
import net.rankelo.plugin.managers.EloManager;
import net.rankelo.plugin.placeholder.RankPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RankEloPlugin extends JavaPlugin {

    private EloManager eloManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        eloManager = new EloManager(this);

        Bukkit.getPluginManager().registerEvents(
                new CombatListener(this),
                this
        );

        if (getCommand("elo") != null) {
            getCommand("elo").setExecutor(
                    new EloCommand(this)
            );
        }

        if (Bukkit.getPluginManager()
                .getPlugin("PlaceholderAPI") != null) {

            new RankPlaceholder(this).register();

            getLogger().info(
                    "PlaceholderAPI hooked successfully."
            );
        }

        getLogger().info("RankElo enabled.");
    }

    @Override
    public void onDisable() {
        if (eloManager != null) {
            eloManager.save();
        }

        getLogger().info("RankElo disabled.");
    }

    public EloManager getEloManager() {
        return eloManager;
    }
}
