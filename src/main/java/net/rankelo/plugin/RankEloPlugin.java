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

        this.eloManager = new EloManager(this);

        Bukkit.getPluginManager().registerEvents(new CombatListener(this), this);

        EloCommand eloCommand = new EloCommand(this);
        getCommand("elo").setExecutor(eloCommand);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new RankPlaceholder(this).register();
            getLogger().info("PlaceholderAPI terdeteksi, placeholder %rank_lvl% & %rank_elo% aktif.");
        } else {
            getLogger().warning("PlaceholderAPI tidak ditemukan, placeholder %rank_lvl% & %rank_elo% tidak akan berfungsi.");
        }

        getLogger().info("RankElo berhasil diaktifkan.");
    }

    @Override
    public void onDisable() {
        if (eloManager != null) {
            eloManager.save();
        }
        getLogger().info("RankElo dinonaktifkan.");
    }

    public EloManager getEloManager() {
        return eloManager;
    }
}
