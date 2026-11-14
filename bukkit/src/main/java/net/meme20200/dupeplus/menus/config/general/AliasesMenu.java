package net.meme20200.dupeplus.menus.config.general;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.ItemUtils;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;

public class AliasesMenu {
    public PaginatedGui getMenu(BukkitDupePlus plugin) {
        PaginatedGui gui = Gui.paginated().title(Component.text("General Menu > Aliases", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        List<GuiItem> items = new ArrayList<>();
        for (String alias : plugin.getConfigManager().getAliases()) {
            final String lowercaseAlias = alias.toLowerCase();
            items.add(generateAlias(lowercaseAlias, lowercaseAlias, event -> {
                event.setCancelled(true);
                // Delete
                if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
                    plugin.getConfigManager().getAliases().remove(alias);
                    gui.updateItem(event.getSlot(), new ItemStack(Material.AIR));
                    return;
                }
                if (event.getClick() != ClickType.RIGHT) return;
                // Right click
                if (!(event.getWhoClicked() instanceof Player)) return;
                @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
                new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                    String userInput = stateSnapshot.getText();
                    if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                        event.getWhoClicked().closeInventory();
                        new AliasesMenu().getMenu(plugin).open(event.getWhoClicked());
                    }));
                    userInput = UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT);
                    if (plugin.getConfigManager().getAliases().contains(userInput)) {
                        audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                        return Collections.emptyList();
                    }
                    plugin.getConfigManager().getAliases().remove(userInput);
                    return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                        event.getWhoClicked().closeInventory();
                        new AliasesMenu().getMenu(plugin).open(event.getWhoClicked());
                    }));
                }).itemLeft(generateItemStack(Material.PAPER, lowercaseAlias, MessagesUtil.aliasLore)).title("Alias").text(lowercaseAlias).plugin(plugin).open((Player) event.getWhoClicked());
            }));
        }

        controls(plugin, gui);

        gui.addItem(items.toArray(new GuiItem[0]));
        return gui;
    }

    private void controls(BukkitDupePlus plugin, PaginatedGui gui) {
        ItemStack addButton = new ItemStack(UtilAPIs.sign());
        ItemMeta addButtonMeta = addButton.getItemMeta();
        if (addButtonMeta != null)
            addButtonMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<green>+ <b>ADD</b></green>").decoration(TextDecoration.ITALIC, false)));
        addButton.setItemMeta(addButtonMeta);

        gui.setItem(3, 1, ItemBuilder.from(addButton).asGuiItem(event -> {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                    event.getWhoClicked().closeInventory();
                    new AliasesMenu().getMenu(plugin).open(event.getWhoClicked());
                }));
                userInput = UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT);
                if (plugin.getConfigManager().getAliases().contains(userInput)) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                plugin.getConfigManager().getAliases().add(userInput);
                return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                    event.getWhoClicked().closeInventory();
                    new AliasesMenu().getMenu(plugin).open(event.getWhoClicked());
                }));
            }).itemLeft(generateItemStack(Material.NAME_TAG, MessagesUtil.BLANK, MessagesUtil.addAliasLore)).title("Add new alias").text(" ").plugin(plugin).open((Player) event.getWhoClicked());
        }));

        ItemStack previousHead = ItemUtils.generatePlayerHead(MenuUtils.PREVIOUS_HEAD);
        ItemMeta previousMeta = previousHead.getItemMeta();
        if (previousMeta != null)
            previousMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<yellow><white>←</white> <b>PREVIOUS</b></yellow>").decoration(TextDecoration.ITALIC, false)));
        previousHead.setItemMeta(previousMeta);

        gui.setItem(3, 4, ItemBuilder.from(previousHead).asGuiItem(event -> {
            event.setCancelled(true);
            gui.previous();
        }));

        ItemStack nextHead = ItemUtils.generatePlayerHead(MenuUtils.NEXT_HEAD);
        ItemMeta nextMeta = nextHead.getItemMeta();
        if (nextMeta != null)
            nextMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<b><green>NEXT</green></b> <white>→</white>").decoration(TextDecoration.ITALIC, false)));
        nextHead.setItemMeta(nextMeta);

        gui.setItem(3, 6, ItemBuilder.from(nextHead).asGuiItem(event -> {
            event.setCancelled(true);
            gui.next();
        }));

        ItemStack backHead = ItemUtils.generatePlayerHead(MenuUtils.BACK_HEAD);
        ItemMeta backMeta = backHead.getItemMeta();
        if (backMeta != null)
            backMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(3, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            new GeneralMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
    }

    private @NonNull GuiItem generateAlias(String name, String value, GuiAction<InventoryClickEvent> action) {
        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(name, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.aliases").color(NamedTextColor.GRAY)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Command: /").color(NamedTextColor.GRAY).append(Component.text(value).color(NamedTextColor.WHITE))), LegacyComponentSerializer.legacySection().serialize(Component.text("Q (Drop) to remove").color(NamedTextColor.DARK_GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("Right Click to change").color(NamedTextColor.DARK_GRAY))));
            itemStack.setItemMeta(itemMeta);
        }
        return ItemBuilder.from(itemStack).asGuiItem(action);
    }
}
