package net.meme20200.dupeplus.menus.config.filter;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.menus.config.filter.submenus.AddItemMenu;
import net.meme20200.dupeplus.menus.config.filter.submenus.ItemListMenu;
import net.meme20200.dupeplus.menus.config.filter.submenus.UnduplicatorMenu;
import net.meme20200.dupeplus.utilities.types.ModeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class FilterMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        final Gui gui = Gui.gui().title(Component.text("Filter Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        ItemStack blacklistItem = new ItemStack(Material.BLACK_WOOL);
        ItemMeta blacklistItemMeta = blacklistItem.getItemMeta();
        if (blacklistItemMeta != null) {
            blacklistItemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Blacklist", NamedTextColor.WHITE)));

            blacklistItemMeta.setLore(List.of("", LegacyComponentSerializer.legacySection().serialize(Component.text("Blacklist will allow all items, but only block items in the filter.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to toggle between Whitelist and Blacklist", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        }
        blacklistItem.setItemMeta(blacklistItemMeta);

        ItemStack whitelistItem = new ItemStack(Material.WHITE_WOOL);
        ItemMeta whitelistItemMeta = whitelistItem.getItemMeta();
        if (whitelistItemMeta != null) {
            whitelistItemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Whitelist", NamedTextColor.WHITE)));

            whitelistItemMeta.setLore(List.of("", LegacyComponentSerializer.legacySection().serialize(Component.text("Whitelist will block all items, but only allow items in the filter.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to toggle between Whitelist and Blacklist", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        }
        whitelistItem.setItemMeta(whitelistItemMeta);

        GuiItem filterMode = ItemBuilder.from(plugin.getConfigManager().getFilter().modeType().equals(ModeType.BLACKLIST) ? blacklistItem : whitelistItem).asGuiItem(event -> {
            event.setCancelled(true);
            if (!event.getWhoClicked().hasPermission("dupeplus.admin.filter")) {
                event.getWhoClicked().closeInventory();
                return;
            }
            final ModeType newModeType = plugin.getConfigManager().getFilter().modeType().equals(ModeType.BLACKLIST) ? ModeType.WHITELIST : ModeType.BLACKLIST;
            plugin.getConfigManager().getFilter().modeType(newModeType);
            gui.updateItem(10, newModeType.equals(ModeType.BLACKLIST) ? blacklistItem : whitelistItem);
        });
        gui.setItem(10, filterMode);

        ItemStack itemListItem = new ItemStack(Material.PAPER);
        ItemMeta itemListMeta = itemListItem.getItemMeta();

        if (itemListMeta != null) {
            itemListMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Items List", NamedTextColor.WHITE)));

            itemListMeta.setLore(List.of("", LegacyComponentSerializer.legacySection().serialize(Component.text("All Items that are blocked/allowed from duplicating", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to check all items blocked/allowed in /dupe", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        }

        itemListItem.setItemMeta(itemListMeta);

        GuiItem ItemList = ItemBuilder.from(itemListItem).asGuiItem(event -> {
            event.setCancelled(true);
            new ItemListMenu(plugin).getMenu().open(event.getWhoClicked());
        });

        gui.setItem(2, 4, ItemList);

        ItemStack additemItem = new ItemStack(Material.ANVIL);
        ItemMeta additemItemMeta = additemItem.getItemMeta();

        if (additemItemMeta != null) {
            additemItemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Add Item", NamedTextColor.WHITE)));

            additemItemMeta.setLore(List.of("", LegacyComponentSerializer.legacySection().serialize(Component.text("Add an item to the filter", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to add an item to the filter", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        }

        additemItem.setItemMeta(additemItemMeta);

        GuiItem addItem = ItemBuilder.from(additemItem).asGuiItem(event -> {
            event.setCancelled(true);
            new AddItemMenu(plugin).getMenu().open(event.getWhoClicked());
        });

        gui.setItem(2, 6, addItem);
        Material redDye = Material.matchMaterial("RED_DYE");
        if (redDye == null) {
            redDye = Material.matchMaterial("ROSE_RED");
            if (redDye == null) redDye = Material.STONE;
        }
        ItemStack unduplicatorItem = new ItemStack(redDye);
        ItemMeta unduplicatorItemMeta = unduplicatorItem.getItemMeta();

        if (unduplicatorItemMeta != null) {
            unduplicatorItemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text("Unduplicator", NamedTextColor.WHITE)));

            unduplicatorItemMeta.setLore(List.of("", LegacyComponentSerializer.legacySection().serialize(Component.text("Block this specifically exact item from being duplicated", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Click to make an item Unduplicatable", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        }

        unduplicatorItem.setItemMeta(unduplicatorItemMeta);

        GuiItem unduplicator = ItemBuilder.from(unduplicatorItem).asGuiItem(event -> {
            event.setCancelled(true);
            new UnduplicatorMenu(plugin).getMenu().open(event.getWhoClicked());
        });

        gui.setItem(2, 8, unduplicator);

        mainTemplate(plugin, gui);

        return gui;
    }
}
