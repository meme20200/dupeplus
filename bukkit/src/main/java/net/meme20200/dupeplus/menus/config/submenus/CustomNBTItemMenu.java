package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.generateTextItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class CustomNBTItemMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Custom NBT Item Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, MenuUtils.generateBooleanItem("Custom NBT Item Option", "dupe.custom-nbt-item.enabled", "If the Custom NBT Item feature should be enabled or not.", plugin.getConfigManager().getCustomNBTItem().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getCustomNBTItem().enabled(!plugin.getConfigManager().getCustomNBTItem().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Custom NBT Item Option", "dupe.custom-nbt-item.enabled", "If the Custom NBT Item feature should be enabled or not.", plugin.getConfigManager().getCustomNBTItem().enabled()));
        }));

        gui.setItem(1, generateTextItem("NBT Tag", "dupe.custom-nbt-item.tag", "The tag to check if the item contains it", plugin.getConfigManager().getCustomNBTItem().tag(), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (event.getWhoClicked() instanceof Player) {
                new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                    String userInput = stateSnapshot.getText();
                    if (userInput == null || userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                    plugin.getConfigManager().getCustomNBTItem().tag(userInput);
                    return List.of(AnvilGUI.ResponseAction.close());
                }).itemLeft(generateItemStack(Material.NAME_TAG, plugin.getConfigManager().getCustomNBTItem().tag(), MessagesUtil.customNBTItemLore)).title("Custom NBT Tag").text(plugin.getConfigManager().getCustomNBTItem().tag()).plugin(plugin).open((Player) event.getWhoClicked());
            }
        }));


        mainTemplate(plugin, gui);
        return gui;
    }
}
