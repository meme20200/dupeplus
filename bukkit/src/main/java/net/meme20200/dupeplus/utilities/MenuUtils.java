package net.meme20200.dupeplus.utilities;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.menus.config.ConfigMenu;
import net.meme20200.dupeplus.menus.config.filter.FilterMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;

public class MenuUtils {
    public static final String SPIGOTMC_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGUxYjk5ODlkZjYyNzA2NTYwZWJjMWExNzM1OTk0Y2FjNzEzMGZjMDY3Yzc5MmNiM2VjNTU0NDk2NDFmZmZiZiJ9fX0=";
    public static final String MODRINTH_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE4YjdkYmRjZjRlZjliMzY1NzcwYzlmZDEwY2JiYTFiMGU4MWU1NjdmN2Q4ZTg3ODFiN2YyMTUwNzk4MTE4MyJ9fX0=";
    // CONTROLS
    public static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3M2NmNjZkMzFiODNjZDhiODY0NGMxNTk1OGMxYjczYzhkOTczMjNiODAxMTcwYzFkODg2NGJiNmE4NDZkIn19fQ==";
    public static final String NEXT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJjYTkwZWJhZmRmNTdkZjRjMzgwY2YyNjU1YWE3YjRlYzZhNGJkYmQxNTUxNmViZmRlNDMyN2ExZTI3In19fQ==";
    public static final String PREVIOUS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM5MjYyM2NhZmRmNzQ1YmZjYjMxZDA1ZGJjZDI0N2Q1NzFkODk3ZjIyYWNlMzhlZTU0ZWVkM2ZiOTYzNiJ9fX0=";

    public static @NonNull GuiItem generateSectionItem(String section, String path, Material material, GuiAction<InventoryClickEvent> action) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(section, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#" + path).color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("Section").color(NamedTextColor.WHITE)), "", MessagesUtil.configureLore));
            itemStack.setItemMeta(itemMeta);
        }
        return ItemBuilder.from(itemStack).asGuiItem(action);
    }

    public static @NonNull GuiItem generateToggleFilterMode(String name, String path, boolean toggle, GuiAction<InventoryClickEvent> action) {
        return ItemBuilder.from(generateToggleFilterMode(name, path, toggle)).asGuiItem(action);
    }

    public static @NonNull ItemStack generateToggleFilterMode(String name, String path, boolean toggle) {
        ItemStack itemStack = new ItemStack(toggle ? Material.BLACK_WOOL : Material.WHITE_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(name, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#" + path).color(NamedTextColor.GRAY)), toggle ? MessagesUtil.blacklistLore : MessagesUtil.whitelistLore, "", MessagesUtil.configureLore));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static @NonNull GuiItem generateTextItem(String name, String path, String description, String value, Material material, GuiAction<InventoryClickEvent> action) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(name, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#" + path).color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text(description).color(NamedTextColor.WHITE)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Value: ").color(NamedTextColor.GRAY).append(Component.text(value).color(NamedTextColor.WHITE))), LegacyComponentSerializer.legacySection().serialize(Component.text("Click to change").color(NamedTextColor.DARK_GRAY))));
            itemStack.setItemMeta(itemMeta);
        }
        return ItemBuilder.from(itemStack).asGuiItem(action);
    }

    public static @NonNull GuiItem generateBooleanItem(String name, String path, String description, boolean toggle, GuiAction<InventoryClickEvent> action) {
        return generateBooleanItem(name, path, description, toggle, toggle ? Material.LIME_DYE : UtilAPIs.redDye(), action);
    }

    public static @NonNull GuiItem generateBooleanItem(String name, String path, String description, boolean toggle, Material material, GuiAction<InventoryClickEvent> action) {
        return ItemBuilder.from(generateBooleanItem(name, path, description, material, toggle)).asGuiItem(action);
    }

    public static @NonNull ItemStack generateBooleanItem(String name, String path, String description, boolean toggle) {
        return generateBooleanItem(name, path, description, toggle ? Material.LIME_DYE : UtilAPIs.redDye(), toggle);
    }

    public static @NonNull ItemStack generateBooleanItem(String name, String path, String description, Material material, boolean toggle) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(name, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#" + path).color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text(description).color(NamedTextColor.WHITE)), "", toggle ? MessagesUtil.enabled : MessagesUtil.disabled, MessagesUtil.toggleOption));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    /**
     * This is a template, used for menus that directly go back to the config menu, to avoid duplicate (HAHAHAHAHA Ironic) code
     *
     * @param gui The GUI used in the menu; it will be modified.
     */
    public static void mainTemplate(BukkitDupePlus plugin, Gui gui) {
        ItemStack backHead = ItemUtils.generatePlayerHead(BACK_HEAD);
        ItemMeta backMeta = backHead.getItemMeta();
        if (backMeta != null)
            backMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(3, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            new ConfigMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
    }

    /**
     * This is a template to avoid duplicate (HAHAHAHAHA Ironic) code
     *
     * @param gui The GUI used in the menu; it will be modified.
     */
    public static void filterTemplate(BukkitDupePlus plugin, Gui gui) {
        ItemStack backHead = ItemUtils.generatePlayerHead(BACK_HEAD);
        ItemMeta backMeta = backHead.getItemMeta();
        if (backMeta != null)
            backMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(3, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            new FilterMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));

        gui.setCloseGuiAction(event -> {
            if (!(event.getPlayer() instanceof Player)) return;
            Player player = (Player) event.getPlayer();
            ItemStack leftover = gui.getInventory().getItem(13);
            if (leftover == null || UtilAPIs.isAir(leftover.getType())) return;
            gui.getInventory().setItem(13, null); // Clear the slot
            // Give the player the item
            HashMap<Integer, ItemStack> remaining = player.getInventory().addItem(leftover);
            // If inventory is full, drop it at their feet
            remaining.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
        });
    }
}
