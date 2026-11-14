package net.meme20200.dupeplus.menus.config.multiplier;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.*;

public class MultiplierMenu {

    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Multiplier Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(0, MenuUtils.generateBooleanItem("Multiplier Option", "dupe.multiplier.enabled", "If the multiplier feature should be enabled or not.", plugin.getConfigManager().getMultiplier().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getMultiplier().enabled(!plugin.getConfigManager().getMultiplier().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Multiplier Option", "dupe.multiplier.enabled", "If the multiplier feature should be enabled or not.",  plugin.getConfigManager().getMultiplier().enabled()));
        }));

        gui.setItem(1, generateSectionItem("Max Values", "dupe.multiplier.max.*", Material.CHEST, event -> {
            event.setCancelled(true);
            new MultiplierMaxValuesMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.setItem(2, generateTextItem("Min Value", "dupe.multiplier.min", "Min Value", String.valueOf(plugin.getConfigManager().getMultiplier().min()), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                try {
                    int seconds = Integer.parseInt(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll(""));
                    plugin.getConfigManager().getMultiplier().min(seconds);
                } catch (NumberFormatException e) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.NAME_TAG, String.valueOf(plugin.getConfigManager().getMultiplier().min()), MessagesUtil.minValueLore)).title("Min Value").text(String.valueOf(plugin.getConfigManager().getMultiplier().min())).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        gui.setItem(3, generateBooleanItem("One Time Message", "dupe.multiplier.permission", "Should it send the dupe message once or every time in multiplier?", plugin.getConfigManager().getMultiplier().oneTimeMessage(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getMultiplier().oneTimeMessage(!plugin.getConfigManager().getMultiplier().oneTimeMessage());
            gui.updateItem(3, generateBooleanItem("One Time Message", "dupe.multiplier.permission", "Should it send the dupe message once or every time in multiplier?", plugin.getConfigManager().getMultiplier().oneTimeMessage()));
        }));

        gui.setItem(4, generateBooleanItem("Multiplier Permission", "dupe.multiplier.permission", "Permission: dupeplus.multiplier", plugin.getConfigManager().getMultiplier().permission(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getMultiplier().permission(!plugin.getConfigManager().getMultiplier().permission());
            gui.updateItem(4, generateBooleanItem("Multiplier Permission", "dupe.multiplier.permission", "Permission: dupeplus.multiplier", plugin.getConfigManager().getMultiplier().permission()));
        }));

        mainTemplate(plugin, gui);
        return gui;
    }
}
