package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.types.LoreMode;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.generateBooleanItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;
import static net.meme20200.dupeplus.utilities.UtilAPIs.parseCustomText;

public class LoreMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Lore Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, MenuUtils.generateBooleanItem("Lore Option", "dupe.lore.enabled", "If the lore feature should be enabled or not.", plugin.getConfigManager().getLore().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getLore().enabled(!plugin.getConfigManager().getLore().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Lore Option", "dupe.lore.enabled", "If the lore feature should be enabled or not.", plugin.getConfigManager().getLore().enabled()));
        }));

        gui.setItem(1, generateLoreText(plugin, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (event.getWhoClicked() instanceof Player) {
                new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                    String userInput = stateSnapshot.getText();
                    if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                    plugin.getConfigManager().getLore().text(userInput);
                    return List.of(AnvilGUI.ResponseAction.close());
                }).itemLeft(generateItemStack(Material.PAPER, plugin.getConfigManager().getLore().text(), MessagesUtil.loreTextLore)).title("Lore Text").text(plugin.getConfigManager().getLore().text()).plugin(plugin).open((Player) event.getWhoClicked());
            }
        }));

        gui.setItem(2, ItemBuilder.from(generateModeItem(plugin.getConfigManager().getLore().mode())).asGuiItem(event -> {
            event.setCancelled(true);
            final LoreMode nextMode = plugin.getConfigManager().getLore().mode() == LoreMode.SET ? LoreMode.APPEND : LoreMode.SET;
            plugin.getConfigManager().getLore().mode(nextMode);
            gui.updateItem(2, generateModeItem(nextMode));
        }));

        gui.setItem(3, generateBooleanItem("Lore permission", "dupe.lore.permission", "Permission: dupeplus.lore", plugin.getConfigManager().getLore().isPermissionEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getLore().setPermissionEnabled(!plugin.getConfigManager().getLore().isPermissionEnabled());
            gui.updateItem(3, generateBooleanItem("Lore permission", "dupe.lore.permission", "Permission: dupeplus.lore", plugin.getConfigManager().getLore().isPermissionEnabled()));
        }));

        mainTemplate(plugin, gui);
        return gui;
    }

    private @NonNull ItemStack generateModeItem(LoreMode mode) {
        ItemStack item = new ItemStack(mode == LoreMode.SET ? Material.WRITABLE_BOOK : Material.HOPPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(mode == LoreMode.SET ? "Set" : "Append").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.WHITE)));
            meta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text(mode == LoreMode.SET ? "Replaces the lore in the duplicated item with the text" : "Adds the lore to the duplicated item").color(NamedTextColor.GRAY)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to toggle the mode between Set and Append").color(NamedTextColor.DARK_GRAY))));
            item.setItemMeta(meta);
        }
        return item;
    }

    private @NonNull GuiItem generateLoreText(BukkitDupePlus plugin, GuiAction<InventoryClickEvent> action) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        String value = LegacyComponentSerializer.legacySection().serialize(Component.text("Value: ").color(NamedTextColor.GRAY).append(parseCustomText(plugin.getConfigManager().getLore().text())));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Lore Text", NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.lore.text").color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("The lore appended/set to the item").color(NamedTextColor.WHITE)), "", value, LegacyComponentSerializer.legacySection().serialize(Component.text("Click to change").color(NamedTextColor.DARK_GRAY))));
            itemStack.setItemMeta(itemMeta);
        }
        return ItemBuilder.from(itemStack).asGuiItem(action);
    }
}
