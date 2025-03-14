package com.galaxydevnetwork.Bukkit.Utilities;

import dev.lone.itemsadder.api.CustomStack;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.galaxydevnetwork.Bukkit.BukkitDupePlus.*;


public class BukkitConfigyml {
    public static FileConfiguration config = getPlugin().getConfig();

    public static String ConsoleMessage() {
        return config.getString("dupe.console-message", "DupePlus | You can't do that!");
    }
    public static void reloadConfig() {
        getPlugin().reloadConfig();
        config = getPlugin().getConfig();
    }

    public static boolean dupingnothingmessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.duping-nothing-message", "").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }

    public static boolean DupePermissionOption() {
        return config.getBoolean("dupe.permission", false);
    }

    public static boolean isDupeMessageEmpty() {
        return config.getString("dupe.message", "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>").isEmpty();
    }
    public static boolean hasDupePermission(Player player) {
        return player.hasPermission("dupeplus.dupe");
    }
    public static ItemStack getDupedItem(Player player) {
        if (config.getString("dupe.dupe-on", "MainHand").equals("MainHand")) {
            return player.getInventory().getItemInMainHand();
        } else if (config.getString("dupe.dupe-on", "MainHand").equals("OffHand")) {
            return player.getInventory().getItemInOffHand();
        } else {
            return player.getInventory().getItemInMainHand();
        }
    }

    public static boolean blacklistEnabled() {
        return config.getBoolean("dupe.blacklist.enabled", false);
    }
    public static List<Material> blacklistedItems() {
        List<String> blacklistItemsSTR = config.getStringList("dupe.blacklist.items");
        List<Material> blacklistedItems = new ArrayList<>();
        for (String itemName : blacklistItemsSTR) {
            if (itemName.startsWith("minecraft:")) {
                try {
                    Material material = Material.valueOf(itemName.replaceAll("minecraft:", "").toUpperCase());
                    blacklistedItems.add(material);
                } catch (IllegalArgumentException e) {
                    getPlugin().getLogger().warning("Invalid Minecraft Item name in config: " + itemName);
                }
            }
        }
        return blacklistedItems;
    }

    public static boolean CustomBlacklistItems(ItemStack itemStack) {
        List<String> blacklistItemsSTR = config.getStringList("dupe.blacklist.items");
        for (String itemName : blacklistItemsSTR) {
            if (isItemsadderInstalled && itemName.startsWith("itemsadder:")) {
                CustomStack customStack = CustomStack.byItemStack(itemStack);
                if (customStack != null) {
                    return Objects.equals(customStack.getId(), itemName.replaceAll("itemsadder:", ""));
                } else return false;
            }
        }
        return false;
    }

    public static boolean timingsEnabled() {
        return config.getBoolean("dupe.times.enabled", false);
    }
    public static int timesMax(Player player) {
        ConfigurationSection maxValues = config.getConfigurationSection("dupe.times.max-values");
        if (maxValues == null) return 5;
        int maxAllowed = maxValues.getInt("default", 5);
        for (String key : maxValues.getKeys(false)) {
            getPlugin().getLogger().warning("Checking key: " + key);
            String permission = "dupeplus.times.max." + key;
            if (player.hasPermission(permission)) {
                getPlugin().getLogger().warning("Player has key named: " + key);
                int value = maxValues.getInt(key);
                if (value == 0) return 0;
                maxAllowed = Math.max(maxAllowed, value);
            }
        }
        return maxAllowed;
    }
    public static int timesMini() {
        return config.getInt("dupe.times.mini", 0);
    }
    public static boolean timesMaxMessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.times.max-message", "%prefix% <dark_gray>|</dark_gray> <red>This is above maximum! Do something lower than %max%</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%max%", String.valueOf(timesMax(player))).replaceAll("%min%", String.valueOf(timesMini())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }
    public static boolean timesMiniMessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.times.min-message", "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%max%", String.valueOf(timesMax(player))).replaceAll("%min%", String.valueOf(timesMini())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }


    public static boolean hasTimesPermission(Player player) {
        return player.hasPermission("dupeplus.times");
    }
    public static boolean TimesNoPermission(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }
    public static boolean TimesPermissionOption() {
        return config.getBoolean("dupe.times.permission", false);
    }
    public static boolean OneTimeMessage() {
        return config.getBoolean("dupe.times.one-time-message", true);
    }
    public static @NotNull Component DupeMessage(Player player) {
        ItemStack item = getDupedItem(player);
        return MiniMessage.miniMessage().deserialize(config.getString("dupe.message", "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%item_name%", itemStackName(item))
                .replaceAll("%item_type%", item.getType().name())
                .replaceAll("%old_item_amount%", String.valueOf(item.getAmount()))
                .replaceAll("%new_item_amount%", String.valueOf(Math.min(item.getAmount() * 2, 64)))
        );
    }
    public static @NotNull Component getPrefix() {
        return MiniMessage.miniMessage().deserialize(config.getString("dupe.prefix", "<green>DupePlus</green>"));
    }
    public static boolean DupeNoPermission(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        String b = config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()));
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(b.isEmpty())) p.sendMessage(a);
        return true;
    }

    public static boolean blockedmessage(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        ItemStack item = getDupedItem(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.blacklist.blocked-message", "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%item_name%", itemStackName(item))
                .replaceAll("%item_type%", item.getType().name())
                .replaceAll("%item_amount%", String.valueOf(item.getAmount())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }

    public static boolean isCheckUpdateAllowed() {
        return config.getBoolean("updates.checkupdate", true);
    }

    public static boolean isCustomNBTAllowed() {
        return config.getBoolean("dupe.custom-nbt-item.enabled", true);
    }


    /**
     * If true then SpigotMC else it's Modrinth
     * @return boolean
     */
    public static boolean isSpigotMC() {
        return config.getString("updates.api", "modrinth").equalsIgnoreCase("spigotmc");
    }

    public static boolean isPlayerNotifyAllowed() {
        return config.getBoolean("updates.notify.player-notify", true);
    }

    public static boolean isConsoleNotifyAllowed() {
        return config.getBoolean("updates.notify.console-notify", true);
    }

    public static void updatePlayerMessage(@NotNull Player player, @NotNull String newversion) {
        Audience p = getPlugin().adventure().player(player);
        String link = isSpigotMC() ? "https://www.spigotmc.org/resources/dupeplus.110621/" : "https://modrinth.com/plugin/dupeplus";
        String b = config.getString("updates.notify.notify-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%link%", link)
                .replaceAll("%currentversion%", version)
                .replaceAll("%newversion%", newversion);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("updates.notify.notify-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%link%", link)
                .replaceAll("%currentversion%", version)
                .replaceAll("%newversion%", newversion));
        if (!(b.isEmpty())) p.sendMessage(a);
    }

    public static void updateConsoleMessage(@NotNull JavaPlugin plugin, @NotNull String newversion) {
        String link = isSpigotMC() ? "https://www.spigotmc.org/resources/dupeplus.110621/" : "https://modrinth.com/plugin/dupeplus";
        String b = config.getString("updates.notify.console-notify-message", "%prefix% | Update DupePlus at %link%")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%link%", link)
                .replaceAll("%currentversion%", version)
                .replaceAll("%newversion%", newversion);
        if (!(b.isEmpty())) plugin.getLogger().info(b);
    }

    public static boolean isCooldownEnabled() {
        return config.getBoolean("dupe.cooldown.enabled", true);
    }


    public static long cooldownSeconds() {
        return config.getLong("dupe.cooldown.seconds", 3);
    }

    public static boolean Cooldownmessage(Player player, String timeleft) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.cooldown.wait-message", "%prefix% <dark_gray>|</dark_gray> <red>Please wait %duration%s.</red>")
                .replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()))
                .replaceAll("%duration%", timeleft));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }

    public static boolean customNBTItem(Player player, ItemStack item) {
        if (NBTEditor.contains(item, NBTEditor.CUSTOM_DATA, "dupenotallowed")) {
            return NBTEditor.getBoolean(getDupedItem(player), NBTEditor.CUSTOM_DATA, "dupenotallowed");
        } else return false;
    }

    private static String itemStackName(ItemStack itemStack) {
        if (itemStack.getItemMeta().hasDisplayName()) return itemStack.getItemMeta().getDisplayName();
        return formatItemStackName(itemStack.getType().name());
    }

    private static String formatItemStackName(String dupeTypeName) {
        String[] words = dupeTypeName.toLowerCase().split("_");
        StringBuilder dupeItem = new StringBuilder();

        for (String word : words) {
            dupeItem.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        // Trim the extra space at the end
        return dupeItem.toString().trim();
    }

    public static boolean customNBTItemMessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.custom-nbt-item.blocked-message", "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
        return true;
    }

    public static boolean isLoreEnabled() {
        return config.getBoolean("dupe.lore.enabled", true);
    }

    public static String getLoreMode() {
        return config.getString("dupe.lore.mode", "set");
    }

    public static String getTextLore() {
        return convertMiniMessageToLegacy(config.getString("dupe.lore.text", "<dark_red>*</dark_red> <red>Duplicated Item</red>"));
    }

    public static ItemStack addLore(ItemStack itemStack) {
        // Clone the item stack to avoid modifying the original one
        ItemStack newItem = itemStack.clone();
        ItemMeta meta = newItem.getItemMeta();

        // Ensure meta is not null before proceeding
        if (meta == null) {
            return newItem;  // Return the item unchanged if the meta is null
        }

        // Get the lore mode and text from your helper methods
        String loreMode = getLoreMode();
        String textLore = getTextLore();

        // Check if textLore is empty or null
        if (textLore == null || textLore.trim().isEmpty()) {
            return newItem;  // No lore to add, return the item as is
        }

        // Split textLore into lines (based on new lines)
        List<String> newLore = new ArrayList<>(Arrays.asList(textLore.split("\n", -1)));

        if (loreMode.equalsIgnoreCase("set")) {
            // Set the lore only if the item does not already have the exact same lore
            if (!meta.hasLore() || !meta.getLore().equals(newLore)) {
                meta.setLore(newLore);
            }
        } else if (loreMode.equalsIgnoreCase("append")) {
            // Append to existing lore if not already present
            List<String> existingLore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

            // Append the new lore if not already present
            for (String line : newLore) {
                if (!existingLore.contains(line)) {
                    existingLore.add(line);
                }
            }
            meta.setLore(existingLore);
        }

        // Apply the modified meta to the new item stack
        newItem.setItemMeta(meta);

        return newItem;
    }

    private static String convertMiniMessageToLegacy(String miniMessageString) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component component = miniMessage.deserialize(miniMessageString);
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
}

