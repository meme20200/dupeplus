package net.meme20200.Bukkit.Commands;

import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import net.meme20200.Bukkit.Utilities.CooldownManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.parser.standard.IntegerParser;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;
import static net.meme20200.Bukkit.Utilities.BukkitConfigyml.dupeAliases;

public class BukkitDupeCommand {


    public void registerCommand() {

        if (BukkitConfigyml.isCustomCommandEnabled() & !BukkitConfigyml.customCommandName().isEmpty()) {
            if (!dupeAliases().isEmpty()) {
                getPlugin().getCommandManager().command(
                        getPlugin().getCommandManager().commandBuilder(BukkitConfigyml.customCommandName(), Description.of("Dupe Command"), BukkitConfigyml.dupeAliases().toArray(new String[0]))
                                .senderType(Player.class)
                                .optional("times", IntegerParser.integerParser())
                                .handler(this::executeCommand)
                );
            } else {
                getPlugin().getCommandManager().command(
                        getPlugin().getCommandManager().commandBuilder(BukkitConfigyml.customCommandName(), Description.of("Dupe Command"))
                                .senderType(Player.class)
                                .optional("times", IntegerParser.integerParser())
                                .handler(this::executeCommand)
                );
            }
        } else {
            if (!dupeAliases().isEmpty()) {
                getPlugin().getCommandManager().command(
                        getPlugin().getCommandManager().commandBuilder("dupe", Description.of("Dupe Command"), BukkitConfigyml.dupeAliases().toArray(new String[0]))
                                .senderType(Player.class)
                                .optional("times", IntegerParser.integerParser())
                                .handler(this::executeCommand)
                );
            } else {
                getPlugin().getCommandManager().command(
                        getPlugin().getCommandManager().commandBuilder("dupe", Description.of("Dupe Command"))
                                .senderType(Player.class)
                                .optional("times", IntegerParser.integerParser())
                                .handler(this::executeCommand)
                );
            }
        }
    }

    public void unregisterCommand() {
        if (BukkitConfigyml.isCustomCommandEnabled() & !BukkitConfigyml.customCommandName().isEmpty()) {
            getPlugin().getCommandManager().deleteRootCommand(BukkitConfigyml.customCommandName());
        } else {
            getPlugin().getCommandManager().deleteRootCommand("dupe");
        }
    }

    private final CooldownManager cooldownManager = new CooldownManager();

    private void executeCommand(CommandContext<Player> context) {


        Player player = context.sender();
        Audience p = getPlugin().adventure().player(player);

        // Checks if permission is enabled or not.
        if (BukkitConfigyml.DupePermissionOption()) {
            if (!(BukkitConfigyml.hasDupePermission(player))) {
                BukkitConfigyml.DupeNoPermission(player, p);
                return;
            }
        }

        if (BukkitConfigyml.isWorldsEnabled()) {
            if (BukkitConfigyml.getWorldsMode().equals("blacklist")) {
                if (BukkitConfigyml.getWorlds().contains(player.getWorld().getName())) {
                    BukkitConfigyml.WorldBlockedMessage(player, p);
                    return;
                }
            } else if (BukkitConfigyml.getWorldsMode().equals("whitelist")) {
                if (!BukkitConfigyml.getWorlds().contains(player.getWorld().getName())) {
                    BukkitConfigyml.WorldBlockedMessage(player, p);
                    return;
                }
            }
        }

        ItemStack item = BukkitConfigyml.getDupedItem(player);

        // Send message if Dupe message isn't empty
        if (item.getType() == Material.AIR) {
            BukkitConfigyml.dupingnothingmessage(player, p);
            return;
        }

        // Checks if item is in blacklisted items
        if (BukkitConfigyml.listEnabled()) {
            if (BukkitConfigyml.isblacklistEnabled()) {
                // minecraft
                if (BukkitConfigyml.listedItems().contains(item.getType())) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
                // itemsadder
                if (BukkitConfigyml.CustomBlacklistItems(item)) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
                if (BukkitConfigyml.customBlacklistedNamespace(item)) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
            } else {
                if (!BukkitConfigyml.listedItems().contains(item.getType())) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
                // itemsadder
                if (!BukkitConfigyml.CustomBlacklistItems(item)) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
                if (!BukkitConfigyml.customBlacklistedNamespace(item)) {
                    BukkitConfigyml.blockedmessage(player, p);
                    return;
                }
            }

        }

        if (BukkitConfigyml.isCooldownEnabled()) {
            if (cooldownManager.hasCooldown(player.getUniqueId())) {
                BukkitConfigyml.Cooldownmessage(p, player, formatDuration(cooldownManager.getRemainingCooldown(player.getUniqueId())));
                return;
            }
        }
        if (BukkitConfigyml.isCustomNBTAllowed()) {
            if (BukkitConfigyml.customNBTItem(player, item)) {
                BukkitConfigyml.customNBTItemMessage(player, p);
                return;
            }
        }

        // Check if the time is enabled
        int times = context.getOrDefault("times", 0);
        if (BukkitConfigyml.timingsEnabled() && times != 0) {

            if (BukkitConfigyml.TimesPermissionOption()) {
                if (!(BukkitConfigyml.hasTimesPermission(player))) {
                    BukkitConfigyml.TimesNoPermission(player, p);
                    return;
                }
            }

            try {
                if (!(BukkitConfigyml.timesMax(player) == 0)) {
                    if (!(player.hasPermission("dupeplus.times.max.unlimited"))) {
                        if (times > BukkitConfigyml.timesMax(player)) {
                            BukkitConfigyml.timesMaxMessage(player, p);
                            return;
                        }
                    }
                }
                if (!(BukkitConfigyml.timesMini() == 0)) {
                    if (!(player.hasPermission("dupeplus.times.min.unlimited"))) {
                        if (times < BukkitConfigyml.timesMini()) {
                            BukkitConfigyml.timesMiniMessage(player, p);
                            return;
                        }
                    }
                }

                if (item.getType() == Material.AIR) {
                    BukkitConfigyml.dupingnothingmessage(player, p);
                    return;
                }

                for (int i = 0; i < times; i++) {
                    if (!BukkitConfigyml.isDupeMessageEmpty() &&
                            !BukkitConfigyml.OneTimeMessage()) {
                        BukkitConfigyml.DupeMessage(player, p);
                    }
                    dupe(player, item);
                }
                if (!(BukkitConfigyml.isDupeMessageEmpty()) &&
                        BukkitConfigyml.OneTimeMessage()) {
                    BukkitConfigyml.DupeMessage(player, p);
                }

                if (BukkitConfigyml.isCooldownEnabled()) {
                    cooldownManager.setCooldown(player.getUniqueId(), Duration.ofSeconds(BukkitConfigyml.cooldownSeconds()));
                }
                return;
            } catch (NumberFormatException e) {
                Component a = BukkitConfigyml.getPrefix().append(MiniMessage.miniMessage().deserialize(" <dark_gray>|</dark_gray> <red>Enter a number next time!</red>"));
                p.sendMessage(a);
                return;
            }
        }


        if (!BukkitConfigyml.isDupeMessageEmpty()) {
            BukkitConfigyml.DupeMessage(player, p);
        }
        dupe(player, item);
        if (BukkitConfigyml.isCooldownEnabled()) {
            cooldownManager.setCooldown(player.getUniqueId(), Duration.ofSeconds(BukkitConfigyml.cooldownSeconds()));
        }
    }

    private void dupe(Player player, ItemStack item) {
        if (BukkitConfigyml.isLoreEnabled()) {
            item = BukkitConfigyml.addLore(item);
        }
        player.getInventory().addItem(item);
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
