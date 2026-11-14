package net.meme20200.dupeplus.menus.config.submenus;

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
import java.util.Locale;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.*;

public class CooldownMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Cooldown Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, MenuUtils.generateBooleanItem("Cooldown Option", "dupe.cooldown.enabled", "If the cooldown feature should be enabled or not.", plugin.getConfigManager().getCooldown().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getCooldown().enabled(!plugin.getConfigManager().getCooldown().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Cooldown Option", "dupe.cooldown.enabled", "If the cooldown feature should be enabled or not.", plugin.getConfigManager().getCooldown().enabled()));
        }));

        gui.setItem(1, generateTextItem("Cooldown Seconds", "dupe.cooldown.seconds", "The cooldown for a player in seconds", plugin.getConfigManager().getCooldown().seconds() + "s", Material.CLOCK, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (event.getWhoClicked() instanceof Player) {
                @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
                new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                    String userInput = stateSnapshot.getText();
                    if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                    try {
                        long seconds = Long.parseLong(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT));
                        plugin.getConfigManager().getCooldown().seconds(seconds);
                    } catch (NumberFormatException e) {
                        audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                        return Collections.emptyList();
                    }
                    return List.of(AnvilGUI.ResponseAction.close());
                }).itemLeft(generateItemStack(Material.PAPER, String.valueOf(plugin.getConfigManager().getCooldown().seconds()), MessagesUtil.cooldownSecondsLore)).title("Cooldown Seconds").text(String.valueOf(plugin.getConfigManager().getCooldown().seconds())).plugin(plugin).open((Player) event.getWhoClicked());
            }
        }));

        gui.setItem(2, generateBooleanItem("Cooldown Permission", "dupe.cooldown.permission", "Permission: dupeplus.cooldown", plugin.getConfigManager().getCooldown().isPermissionEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getCooldown().setPermissionEnabled(!plugin.getConfigManager().getCooldown().isPermissionEnabled());
            gui.updateItem(2, generateBooleanItem("Cooldown Permission", "dupe.cooldown.permission", "Permission: dupeplus.cooldown", plugin.getConfigManager().getCooldown().isPermissionEnabled()));
        }));
        mainTemplate(plugin, gui);
        return gui;
    }
}
