package com.galaxydevnetwork.Bukkit.Commands;

import com.galaxydevnetwork.Bukkit.Utilities.BukkitConfigyml;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.galaxydevnetwork.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(BukkitConfigyml.ConsoleMessage());
            return true;
        }
        Player player = (Player) commandSender;
        Audience p = getPlugin().adventure().player(player);

        // Checks if item is in blacklisted items
        if (BukkitConfigyml.blacklistEnabled()) {
            if (BukkitConfigyml.blacklistedItems().contains(BukkitConfigyml.getDupedItem(player).getType())) {
                return BukkitConfigyml.blockedmessage(player);
            }
        }
        // Checks if permission is enabled or not.
        if (BukkitConfigyml.DupePermissionOption()) {
            if (!(BukkitConfigyml.hasDupePermission(player))) {
                return BukkitConfigyml.DupeNoPermission(player);
            }
        }
        // Send message if Dupe message isn't empty
        if (BukkitConfigyml.getDupedItem(player).getType() == Material.AIR) {
            return BukkitConfigyml.dupingnothingmessage(player);
        }
        // Check if the time is enabled
        if (BukkitConfigyml.timingsEnabled() && args.length >= 1) {

            if (BukkitConfigyml.TimesPermissionOption()) {

                if (!(BukkitConfigyml.hasTimesPermission(player))) {
                    return BukkitConfigyml.TimesNoPermission(player);
                }

            }

            try {
                int a = Integer.parseInt(args[0]);
                if (!(BukkitConfigyml.timesMax() == 0)) {
                    if (!(player.hasPermission("dupeplus.times.max.unlimited"))) {
                        if (Integer.parseInt(args[0]) > BukkitConfigyml.timesMax()) {
                            return BukkitConfigyml.timesMaxMessage(player);
                        }
                    }
                }
                if (!(BukkitConfigyml.timesMini() == 0)) {
                    if (!(player.hasPermission("dupeplus.times.min.unlimited"))) {
                        if (Integer.parseInt(args[0]) < BukkitConfigyml.timesMini()) {
                            return BukkitConfigyml.timesMiniMessage(player);
                        }
                    }
                }

                if (BukkitConfigyml.getDupedItem(player).getType() == Material.AIR) {
                    return BukkitConfigyml.dupingnothingmessage(player);
                }

                for (int i = 0; i < a; i++) {
                    if (!(BukkitConfigyml.isDupeMessageEmpty())) {
                        p.sendMessage(BukkitConfigyml.DupeMessage(player));
                    }
                    player.getInventory().addItem(BukkitConfigyml.getDupedItem(player));
                }
                return true;
            } catch (NumberFormatException e) {
                Component a = BukkitConfigyml.getPrefix().append(MiniMessage.miniMessage().deserialize(" <dark_gray>|</dark_gray> <red>Enter a number next time!</red>"));
                p.sendMessage(a);
                return true;
            }


        }
        if (!BukkitConfigyml.isDupeMessageEmpty()) {
            p.sendMessage(BukkitConfigyml.DupeMessage(player));
        }
        player.getInventory().addItem(BukkitConfigyml.getDupedItem(player));
        return true;
    }
}
