package net.meme20200.dupeplus.commands;


import net.kyori.adventure.audience.Audience;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.meme20200.dupeplus.utilities.types.ModeType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.parser.standard.IntegerParser;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static net.meme20200.dupeplus.utilities.UtilAPIs.isBundleItem;

/**
 * The class for {@code /dupe} (yes)
 */
public class BukkitDupeCommand {
    private final BukkitDupePlus plugin;

    public BukkitDupeCommand(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the /dupe command
     */
    public void registerCommand() {
        boolean customCommand = plugin.getConfigManager().getCustomCommand().enabled() && !plugin.getConfigManager().getCustomCommand().name().isBlank();
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder(customCommand ? UtilAPIs.getWhitespacePattern().matcher(plugin.getConfigManager().getCustomCommand().name()).replaceAll("").toLowerCase(Locale.ROOT) : "dupe", Description.of("Dupe command"), plugin.getConfigManager().getAliases().toArray(new String[0])).senderType(Player.class).optional("multiplier", IntegerParser.integerParser()).handler(this::executeCommand));
    }

    public void unregisterCommand(String oldCommand) {
        plugin.getCommandManager().deleteRootCommand(oldCommand);
    }

    public void reloadCommand(String oldCommand) {
        unregisterCommand(oldCommand);
        registerCommand();
    }

    /**
     * @param context Data used in the command to run. By data, as in the Player who ran it, and here, the attribute
     */
    private void executeCommand(CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);

        // Checks if permission is enabled or not.
        // The permission option check has been moved to the hasDupePermission()
        if (!(plugin.getConfigyml().hasDupePermission(player))) {
            plugin.getConfigyml().dupeNoPermission(player, p);
            return;
        }

        ItemStack item = plugin.getConfigyml().getDupedItem(player);

        // Send message if Dupe message isn't empty
        if (UtilAPIs.isAir(item.getType())) {
            plugin.getConfigyml().nothingMessage(player, p);
            return;
        }

        if (plugin.getConfigManager().getWorlds().enabled()) {
            if (plugin.getConfigManager().getWorlds().modeType() == ModeType.BLACKLIST) {
                if (plugin.getConfigManager().getWorlds().worlds().contains(player.getWorld().getName())) {
                    plugin.getConfigyml().worldBlockedMessage(player, p);
                    return;
                }
            } else {
                if (!plugin.getConfigManager().getWorlds().worlds().contains(player.getWorld().getName())) {
                    plugin.getConfigyml().worldBlockedMessage(player, p);
                    return;
                }
            }
        }

        // Checks if item is in blacklisted items
        if (plugin.getConfigManager().getFilter().enabled()) {
            if (plugin.getConfigManager().getFilter().modeType() == ModeType.BLACKLIST) {
                // BLACKLIST: Item must not be in the filter to dupe
                // Any item in the filter should be blocked

                // minecraft
                if (plugin.getConfigManager().getVanillaFilter().contains(item.getType())) {
                    plugin.getConfigyml().blockedMessage(player, p);
                    return;
                }
                // itemsadder, nexo, oraxen, minehut cosmetics, etc
                if (plugin.getConfigyml().customBlockItem(item)) {
                    plugin.getConfigyml().blockedMessage(player, p);
                    return;
                }
                // Shulker boxes
                if (plugin.getConfigManager().getFilter().shulkerBox() && UtilAPIs.isShulkerItem(item.getType())) {
                    if (plugin.getConfigyml().shulkerBoxFilterCheck(item)) {
                        plugin.getConfigyml().blockedMessage(player, p);
                        return;
                    }
                }
                // Bundles
                if (plugin.getConfigManager().getFilter().bundle() && isBundleItem(item.getType())) {
                    if (plugin.getConfigyml().bundleBlacklist(item)) {
                        plugin.getConfigyml().blockedMessage(player, p);
                        return;
                    }
                }
            } else {
                // WHITELIST: Item must be in the filter to dupe
                // Any item not in the filter should be blocked

                // minecraft
                if (!plugin.getConfigManager().getVanillaFilter().contains(item.getType())) {
                    plugin.getConfigyml().blockedMessage(player, p);
                    return;
                }
                // itemsadder, nexo, oraxen, minehut cosmetics, etc
                if (!plugin.getConfigyml().customBlockItem(item)) {
                    plugin.getConfigyml().blockedMessage(player, p);
                    return;
                }
                // Shulker boxes
                if (!plugin.getConfigManager().getFilter().shulkerBox() && UtilAPIs.isShulkerItem(item.getType())) {
                    if (plugin.getConfigyml().shulkerBoxFilterCheck(item)) {
                        plugin.getConfigyml().blockedMessage(player, p);
                        return;
                    }
                }
                // Bundles
                if (!plugin.getConfigManager().getFilter().bundle() && isBundleItem(item.getType())) {
                    if (plugin.getConfigyml().bundleBlacklist(item)) {
                        plugin.getConfigyml().blockedMessage(player, p);
                        return;
                    }
                }
            }

        }

        if (plugin.getConfigManager().getCooldown().enabled() && plugin.getConfigyml().hasCooldownPermission(player)) {
            if (plugin.getCooldownManager().hasCooldown(player.getUniqueId())) {
                plugin.getConfigyml().cooldownMessage(p, player, formatDuration(plugin.getCooldownManager().getRemainingCooldown(player.getUniqueId())));
                return;
            }
        }

        if (plugin.getConfigManager().getCustomNBTItem().enabled() && plugin.getConfigyml().customNBTItem(item)) {
            plugin.getConfigyml().customNBTItemMessage(player, p);
            return;
        }

        int multiplier = context.getOrDefault("multiplier", 0);

        boolean economyChargePlayer = false;
        boolean economyEnabledForPlayer = plugin.getConfigManager().getEconomy().enabled() && !plugin.getConfigyml().hasBypassEconomyPermission(player);

        if (economyEnabledForPlayer) {
            if (plugin.getConfigManager().getMultiplier().enabled() && multiplier != 0 && plugin.getConfigManager().getEconomy().chargePerDupe()) {
                economyChargePlayer = true;
                if (!plugin.getConfigyml().hasEnoughCurrency(player, plugin.getConfigManager().getEconomy().price() * multiplier)) {
                    plugin.getConfigyml().notEnoughCurrencyMessage(player, p, plugin.getConfigManager().getEconomy().price() * multiplier);
                    return;
                }
            } else {
                if (!plugin.getConfigyml().hasEnoughCurrency(player, plugin.getConfigManager().getEconomy().price())) {
                    plugin.getConfigyml().notEnoughCurrencyMessage(player, p, plugin.getConfigManager().getEconomy().price());
                    return;
                }
            }
        }

        // Check if the time is enabled
        if (plugin.getConfigManager().getMultiplier().enabled() && multiplier > 0) {
            multiplier(player, p, multiplier, item, economyChargePlayer);
            return;
        }

        plugin.getConfigyml().dupeMessage(player, p);
        dupe(player, item);

        if (plugin.getConfigManager().getCooldown().enabled() && plugin.getConfigyml().hasCooldownPermission(player)) {
            plugin.getCooldownManager().setCooldown(player.getUniqueId(), Duration.ofSeconds(plugin.getConfigManager().getCooldown().seconds()));
        }
        if (plugin.getConfigManager().getSoundFeedback().enabled() && plugin.getConfigyml().hasSoundFeedbackPermission(player)) {
            plugin.getConfigyml().playDupeSuccessfulSound(p);
        }
        if (plugin.getConfigManager().getEconomy().enabled() && !plugin.getConfigyml().hasBypassEconomyPermission(player)) {
            plugin.getConfigyml().deductCurrency(player);
            plugin.getConfigyml().successfulMessage(player, p, plugin.getConfigManager().getEconomy().price());
        }
    }

    private void multiplier(Player player, Audience p, int multiplier, ItemStack item, boolean economyChargePlayer) {
        if (plugin.getConfigManager().getMultiplier().permission()) {
            if (!(plugin.getConfigyml().hasMultiplierPermission(player))) {
                plugin.getConfigyml().multiplierNoPermission(player, p);
                return;
            }
        }
        if (multiplier <= 0) {
            plugin.getConfigyml().multiplierErrorMessage(player, p, multiplier);
            return;
        }

        if (!player.hasPermission("dupeplus.multiplier.max.unlimited") && !(plugin.getConfigyml().multiplierMax(player) == 0) && (multiplier > plugin.getConfigyml().multiplierMax(player))) {
            plugin.getConfigyml().multiplierMaxMessage(player, p, multiplier);
            return;
        }
        if (!player.hasPermission("dupeplus.multiplier.min.unlimited") && !(plugin.getConfigManager().getMultiplier().min() == 0) && (multiplier < plugin.getConfigManager().getMultiplier().min())) {
            plugin.getConfigyml().multiplierMinMessage(player, p, multiplier);
            return;
        }

        if (UtilAPIs.isAir(item.getType())) {
            plugin.getConfigyml().nothingMessage(player, p);
            return;
        }

        final int amount = (int) Math.min((long) item.getAmount() << multiplier, UtilAPIs.INVENTORY_LIMIT);
        dupe(player, item, amount);

        if (economyChargePlayer) plugin.getConfigyml().deductCurrencyMultiplier(player, multiplier);

        if (plugin.getConfigManager().getMultiplier().oneTimeMessage() || multiplier == 1) {
            plugin.getConfigyml().dupeMessage(player, p);
        } else {
            plugin.getConfigyml().dupeMessage(player, p, multiplier);
        }

        if (economyChargePlayer) {
            plugin.getConfigyml().successfulMessage(player, p, plugin.getConfigManager().getEconomy().price() * multiplier);
            return;
        } else if (!plugin.getConfigManager().getEconomy().chargePerDupe()) {
            plugin.getConfigyml().deductCurrency(player);
            plugin.getConfigyml().successfulMessage(player, p, plugin.getConfigManager().getEconomy().price());
            return;
        }

        if (plugin.getConfigManager().getCooldown().enabled() && plugin.getConfigyml().hasCooldownPermission(player)) {
            plugin.getCooldownManager().setCooldown(player.getUniqueId(), Duration.ofSeconds(plugin.getConfigManager().getCooldown().seconds()));
        }
    }

    private void dupe(Player player, ItemStack item) {
        if (plugin.getConfigManager().getLore().enabled() && plugin.getConfigyml().lorePermission(player)) {
            item = plugin.getConfigyml().addLore(item, player);
        }
        player.getInventory().addItem(item);
    }

    private void dupe(Player player, ItemStack item, int amount) {
        ItemStack clonedItem = item.clone();
        clonedItem.setAmount(amount - clonedItem.getAmount());
        if (plugin.getConfigManager().getLore().enabled() && plugin.getConfigyml().lorePermission(player)) {
            clonedItem = plugin.getConfigyml().addLore(item, player);
        }
        player.getInventory().addItem(clonedItem);
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
