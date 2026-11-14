package net.meme20200.dupeplus.menus.config.multiplier;

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

public class MultiplierMaxValuesMenu {

    public PaginatedGui getMenu(BukkitDupePlus plugin) {
        PaginatedGui gui = Gui.paginated().title(Component.text("Multiplier Menu > Max Values", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        List<GuiItem> items = new ArrayList<>();
        plugin.getConfigManager().getMultiplier().maxValues().forEachFast((key, value) -> items.add(generateMultiplierMaxItem(key, String.valueOf(value), event -> {
            event.setCancelled(true);
            if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
                plugin.getConfigManager().getMultiplier().maxValues().remove(key);
                gui.updateItem(event.getSlot(), new ItemStack(Material.AIR));
                return;
            }
            if (event.getClick() != ClickType.RIGHT) return;
            if (!(event.getWhoClicked() instanceof Player))  return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                    event.getWhoClicked().closeInventory();
                    new MultiplierMenu().getMenu(plugin).open(event.getWhoClicked());
                }));
                try {
                    int seconds = Integer.parseInt(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll(""));
                    plugin.getConfigManager().getMultiplier().maxValues().put(key, seconds);
                } catch (NumberFormatException e) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                    event.getWhoClicked().closeInventory();
                    new MultiplierMenu().getMenu(plugin).open(event.getWhoClicked());
                }));
            }).itemLeft(generateItemStack(Material.NAME_TAG, String.valueOf(value), MessagesUtil.multiplierMaxValue)).title("Multiplier Max Value").text(String.valueOf(value)).plugin(plugin).open((Player) event.getWhoClicked());
        })));
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
                String userInput = stateSnapshot.getText().toLowerCase(Locale.ROOT);
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                if (plugin.getConfigManager().getMultiplier().maxValues().contains(userInput)) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                plugin.getConfigManager().getMultiplier().maxValues().put(userInput, 1);
                return List.of(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> {
                    event.getWhoClicked().closeInventory();
                    new MultiplierMaxValuesMenu().getMenu(plugin).open(event.getWhoClicked());
                }));
            }).itemLeft(generateItemStack(Material.NAME_TAG, MessagesUtil.BLANK, MessagesUtil.addMultiplier)).title("Add new multiplier").text(" ").plugin(plugin).open((Player) event.getWhoClicked());
        }));

        ItemStack previousHead = ItemUtils.generatePlayerHead(MenuUtils.PREVIOUS_HEAD);
        ItemMeta previousMeta = previousHead.getItemMeta();
        if (previousMeta != null) previousMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<yellow><white>←</white> <b>PREVIOUS</b></yellow>").decoration(TextDecoration.ITALIC, false)));
        previousHead.setItemMeta(previousMeta);

        gui.setItem(3, 4, ItemBuilder.from(previousHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    gui.previous();
                }
        ));

        ItemStack nextHead = ItemUtils.generatePlayerHead(MenuUtils.NEXT_HEAD);
        ItemMeta nextMeta = nextHead.getItemMeta();
        if (nextMeta != null) nextMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<b><green>NEXT</green></b> <white>→</white>").decoration(TextDecoration.ITALIC, false)));
        nextHead.setItemMeta(nextMeta);

        gui.setItem(3, 6, ItemBuilder.from(nextHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    gui.next();
                }
        ));

        ItemStack backHead = ItemUtils.generatePlayerHead(MenuUtils.BACK_HEAD);
        ItemMeta backMeta = backHead.getItemMeta();
        if (backMeta != null) backMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(3, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    new MultiplierMenu().getMenu(plugin).open(event.getWhoClicked());
                }
        ));

        gui.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
    }

    private @NonNull GuiItem generateMultiplierMaxItem(String key, String value, GuiAction<InventoryClickEvent> action) {
        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(key, NamedTextColor.WHITE)));
            itemMeta.setLore(List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.multiplier.max." + key).color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("").color(NamedTextColor.WHITE)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Max Multiplier: ").color(NamedTextColor.GRAY).append(Component.text(value).color(NamedTextColor.WHITE))), LegacyComponentSerializer.legacySection().serialize(Component.text("Q (Drop) to remove").color(NamedTextColor.DARK_GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("Right click to change").color(NamedTextColor.DARK_GRAY))));
            itemStack.setItemMeta(itemMeta);
        }
        return ItemBuilder.from(itemStack).asGuiItem(action);
    }
}
