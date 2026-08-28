package net.rankelo.plugin.listeners;

import net.rankelo.plugin.RankEloPlugin;
import net.rankelo.plugin.managers.EloManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CombatListener implements Listener {

    private final RankEloPlugin plugin;
    private final EloManager eloManager;

    public CombatListener(RankEloPlugin plugin) {
        this.plugin = plugin;
        this.eloManager = plugin.getEloManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null || killer.equals(victim)) {
            return;
        }

        int killElo = plugin.getConfig().getInt("elo.kill", 100);
        int deathElo = plugin.getConfig().getInt("elo.death", 100);

        eloManager.addElo(killer, killElo);
        eloManager.removeElo(victim, deathElo);

        int killerElo = eloManager.getElo(killer);
        int victimElo = eloManager.getElo(victim);

        killer.sendMessage(
                ChatColor.GREEN + "+" + killElo + " Elo "
                        + ChatColor.GRAY + "for killing "
                        + ChatColor.WHITE + victim.getName()
                        + ChatColor.GRAY + ". Your Elo: "
                        + ChatColor.AQUA + killerElo
        );

        victim.sendMessage(
                ChatColor.RED + "-" + deathElo + " Elo "
                        + ChatColor.GRAY + "because you were killed by "
                        + ChatColor.WHITE + killer.getName()
                        + ChatColor.GRAY + ". Your Elo: "
                        + ChatColor.AQUA + victimElo
        );
    }
}
