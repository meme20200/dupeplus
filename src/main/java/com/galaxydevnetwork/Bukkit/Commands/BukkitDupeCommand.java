package com.galaxydevnetwork.Bukkit.Commands;

import com.galaxydevnetwork.Bukkit.Utilities.BukkitConfigyml;
import com.galaxydevnetwork.Bukkit.Utilities.CooldownManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.galaxydevnetwork.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupeCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();

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
        if (BukkitConfigyml.isCooldownEnabled()) {
            if (cooldownManager.hasCooldown(player.getUniqueId())) {
                return BukkitConfigyml.Cooldownmessage(player, formatDuration(cooldownManager.getRemainingCooldown(player.getUniqueId())));
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
                    dupe(player);
                }
                cooldownManager.setCooldown(player.getUniqueId(), Duration.ofSeconds(BukkitConfigyml.cooldownSeconds()));
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
        dupe(player);
        cooldownManager.setCooldown(player.getUniqueId(), Duration.ofSeconds(BukkitConfigyml.cooldownSeconds()));
        return true;
    }

    private void dupe(Player player) {
        player.getInventory().addItem(BukkitConfigyml.getDupedItem(player));
    }

    private String formatDuration(Duration duration) {
        List<String> parts = new ArrayList<>();
        long days = duration.toDaysPart();
        if (days > 0) {
            parts.add(plural(days, "day"));
        }
        int hours = duration.toHoursPart();
        if (hours > 0 || !parts.isEmpty()) {
            parts.add(plural(hours, "hour"));
        }
        int minutes = duration.toMinutesPart();
        if (minutes > 0 || !parts.isEmpty()) {
            parts.add(plural(minutes, "minute"));
        }
        int seconds = duration.toSecondsPart();
        parts.add(plural(seconds, "second"));
        return String.join(", ", parts);
    }

    private String plural(long num, String unit) {
        return num + " " + unit + (num == 1 ? "" : "s");
    }
}
