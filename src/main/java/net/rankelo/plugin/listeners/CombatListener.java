package net.rankelo.plugin.listeners;

import net.rankelo.plugin.RankEloPlugin;
import net.rankelo.plugin.managers.EloManager;
import net.rankelo.plugin.managers.RankManager;
import net.rankelo.plugin.utils.MessageUtil;
import net.rankelo.plugin.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CombatListener implements Listener {

    private final RankEloPlugin plugin;
    private final EloManager eloManager;
    private final RankManager rankManager;

    public CombatListener(RankEloPlugin plugin) {
        this.plugin = plugin;
        this.eloManager = plugin.getEloManager();
        this.rankManager = new RankManager(plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null || killer.equals(victim)) {
            return;
        }

        if (!WorldUtil.isEnabled(plugin, victim.getWorld())
                || !WorldUtil.isEnabled(plugin, killer.getWorld())) {
            return;
        }

        int killElo = plugin.getConfig().getInt("elo.kill", 25);
        int deathElo = plugin.getConfig().getInt("elo.death", 15);

        int killerBefore = eloManager.getElo(killer.getUniqueId());
        int victimBefore = eloManager.getElo(victim.getUniqueId());

        String killerOldRank = rankManager.getRank(killerBefore);
        String victimOldRank = rankManager.getRank(victimBefore);

        eloManager.addElo(killer.getUniqueId(), killElo);
        eloManager.removeElo(victim.getUniqueId(), deathElo);

        int killerAfter = eloManager.getElo(killer.getUniqueId());
        int victimAfter = eloManager.getElo(victim.getUniqueId());

        String killerNewRank = rankManager.getRank(killerAfter);
        String victimNewRank = rankManager.getRank(victimAfter);

        killer.sendMessage(
                MessageUtil.format(
                        MessageUtil.get(plugin, "messages.kill"),
                        "amount", killElo,
                        "player", victim.getName(),
                        "elo", killerAfter
                )
        );

        victim.sendMessage(
                MessageUtil.format(
                        MessageUtil.get(plugin, "messages.death"),
                        "amount", deathElo,
                        "player", killer.getName(),
                        "elo", victimAfter
                )
        );

        if (!killerOldRank.equals(killerNewRank)) {
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            MessageUtil.get(
                                    plugin,
                                    "messages.promotion"
                            ),
                            "player", killer.getName(),
                            "rank_color",
                            rankManager.getRankColor(killerNewRank),
                            "rank", killerNewRank
                    )
            );
        }

        if (!victimOldRank.equals(victimNewRank)) {
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            MessageUtil.get(
                                    plugin,
                                    "messages.demotion"
                            ),
                            "player", victim.getName(),
                            "rank_color",
                            rankManager.getRankColor(victimNewRank),
                            "rank", victimNewRank
                    )
            );
        }
    }
                        }
