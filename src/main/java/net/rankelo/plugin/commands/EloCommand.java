package net.rankelo.plugin.commands;

import net.rankelo.plugin.RankEloPlugin;
import net.rankelo.plugin.managers.EloManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EloCommand implements CommandExecutor {

    private final RankEloPlugin plugin;
    private final EloManager eloManager;

    public EloCommand(RankEloPlugin plugin) {
        this.plugin = plugin;
        this.eloManager = plugin.getEloManager();
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
                return true;
            }

            showInfo(player);
            return true;
        }

        if (!sender.hasPermission("rankelo.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set" -> setElo(sender, args);
            case "add" -> addElo(sender, args);
            case "reset" -> resetElo(sender, args);
            case "reload" -> reload(sender);
            default -> sendUsage(sender);
        }

        return true;
    }

    private void showInfo(Player player) {
        int elo = eloManager.getElo(player);
        String rank = eloManager.getRank(player);

        player.sendMessage(ChatColor.DARK_GRAY + "━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage(ChatColor.AQUA + "       RankElo");
        player.sendMessage(ChatColor.GRAY + "Player: " + ChatColor.WHITE + player.getName());
        player.sendMessage(ChatColor.GRAY + "Rank: " + ChatColor.AQUA + rank);
        player.sendMessage(ChatColor.GRAY + "Elo: " + ChatColor.YELLOW + elo);
        player.sendMessage(ChatColor.DARK_GRAY + "━━━━━━━━━━━━━━━━━━━━");
    }

    private void setElo(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /elo set <player> <amount>");
            return;
        }

        Player target = plugin.getServer().getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        try {
            int amount = Integer.parseInt(args[2]);

            eloManager.setElo(target, amount);

            sender.sendMessage(
                    ChatColor.GREEN + "Set " + target.getName()
                            + "'s Elo to " + eloManager.getElo(target) + "."
            );
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.RED + "Invalid Elo amount.");
        }
    }

    private void addElo(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /elo add <player> <amount>");
            return;
        }

        Player target = plugin.getServer().getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        try {
            int amount = Integer.parseInt(args[2]);

            eloManager.addElo(target, amount);

            sender.sendMessage(
                    ChatColor.GREEN + "Added " + amount
                            + " Elo to " + target.getName() + "."
            );
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.RED + "Invalid Elo amount.");
        }
    }

    private void resetElo(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /elo reset <player>");
            return;
        }

        Player target = plugin.getServer().getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        eloManager.setElo(
                target,
                plugin.getConfig().getInt("elo.default", 0)
        );

        sender.sendMessage(
                ChatColor.GREEN + "Reset " + target.getName() + "'s Elo."
        );
    }

    private void reload(CommandSender sender) {
        plugin.reloadConfig();
        eloManager.load();

        sender.sendMessage(ChatColor.GREEN + "RankElo configuration reloaded.");
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "RankElo Commands:");
        sender.sendMessage(ChatColor.GRAY + "/elo");
        sender.sendMessage(ChatColor.GRAY + "/elo info");
        sender.sendMessage(ChatColor.GRAY + "/elo set <player> <amount>");
        sender.sendMessage(ChatColor.GRAY + "/elo add <player> <amount>");
        sender.sendMessage(ChatColor.GRAY + "/elo reset <player>");
        sender.sendMessage(ChatColor.GRAY + "/elo reload");
    }
                                 }
