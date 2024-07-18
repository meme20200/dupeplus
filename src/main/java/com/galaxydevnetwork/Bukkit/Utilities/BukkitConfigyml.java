package com.galaxydevnetwork.Bukkit.Utilities;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.galaxydevnetwork.Bukkit.BukkitDupePlus.getPlugin;


public class BukkitConfigyml {
    public static FileConfiguration config = getPlugin().getConfig();

    public static String ConsoleMessage() {
        return config.getString("dupe.console-message", "atlas20200 | You can't do that!");
    }
    public static void reloadConfig() {
        getPlugin().reloadConfig();
        config = getPlugin().getConfig();
    }

    public static boolean dupingnothingmessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("duping-nothing-message", "").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).equals(""))) p.sendMessage(a);
        return true;
    }

    public static boolean DupePermissionOption() {
        return config.getBoolean("dupe.permission", false);
    }

    public static boolean isDupeMessageEmpty() {
        return config.getString("dupe.message", "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>").equals("");
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
            try {
                Material material = Material.valueOf(itemName.toUpperCase());
                blacklistedItems.add(material);
            } catch (IllegalArgumentException e) {
                getPlugin().getLogger().warning("Invalid Minecraft Item name in config: " + itemName);
            }
        }
        return blacklistedItems;
    }

    public static boolean timingsEnabled() {
        return config.getBoolean("dupe.times.enabled", false);
    }
    public static int timesMax() {
        return config.getInt("dupe.times.max", 5);
    }
    public static int timesMini() {
        return config.getInt("dupe.times.mini", 0);
    }
    public static boolean timesMaxMessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.times.max-message", "%prefix% <dark_gray>|</dark_gray> <red>This is above maximum! Do something lower than %max%</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%max%", String.valueOf(timesMax())).replaceAll("%min%", String.valueOf(timesMini())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).equals(""))) p.sendMessage(a);
        return true;
    }
    public static boolean timesMiniMessage(Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.times.min-message", "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%max%", String.valueOf(timesMax())).replaceAll("%min%", String.valueOf(timesMini())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).equals(""))) p.sendMessage(a);
        return true;
    }


    public static boolean hasTimesPermission(Player player) {
        return player.hasPermission("dupeplus.times");
    }
    public static boolean TimesNoPermission(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).equals(""))) p.sendMessage(a);
        return true;
    }
    public static boolean TimesPermissionOption() {
        return config.getBoolean("dupe.times.permission", false);
    }
    public static @NotNull Component DupeMessage(Player player) {
        return MiniMessage.miniMessage().deserialize(config.getString("dupe.message", "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%dupe_item%", getDupedItem(player).getType().name()));
    }
    public static @NotNull Component getPrefix() {
        return MiniMessage.miniMessage().deserialize(config.getString("dupe.prefix", "<green>DupePlus</green>"));
    }
    public static boolean DupeNoPermission(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        String b = config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix()));
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.permission-message", "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())));
        if (!(b.equals(""))) p.sendMessage(a);
        return true;
    }

    public static boolean blockedmessage(@NotNull Player player) {
        Audience p = getPlugin().adventure().player(player);
        Component a = MiniMessage.miniMessage().deserialize(config.getString("dupe.blacklist.blocked-message", "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>").replaceAll("%prefix%", MiniMessage.miniMessage().serialize(getPrefix())).replaceAll("%dupe_item%", getDupedItem(player).getType().name()));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).equals(""))) p.sendMessage(a);
        return true;
    }
}
