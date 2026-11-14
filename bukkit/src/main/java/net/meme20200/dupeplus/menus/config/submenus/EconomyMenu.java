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

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.*;

public class EconomyMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("General Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, MenuUtils.generateBooleanItem("Economy Option", "dupe.economy.enabled", "If the economy feature should be enabled or not.", plugin.getConfigManager().getEconomy().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getEconomy().enabled(!plugin.getConfigManager().getEconomy().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Economy Option", "dupe.economy.enabled", "If the economy feature should be enabled or not.", plugin.getConfigManager().getEconomy().enabled()));
        }));

        gui.setItem(1, generateBooleanItem("Charge Per Dupe", "dupe.economy.charge-per-dupe", "If it should charge per due or only once.", plugin.getConfigManager().getEconomy().chargePerDupe(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getEconomy().chargePerDupe(!plugin.getConfigManager().getEconomy().enabled());
            gui.updateItem(1, generateBooleanItem("Charge Per Dupe", "dupe.economy.charge-per-dupe", "If it should charge per dupe or only once.", plugin.getConfigManager().getEconomy().chargePerDupe()));
        }));

        gui.setItem(2, generateTextItem("Economy Price", "dupe.economy.price", "The price that the player must pay for /dupe", String.valueOf(plugin.getConfigManager().getEconomy().price()), Material.PAPER, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (event.getWhoClicked() instanceof Player) {
                @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
                new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                    if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                    String userInput = stateSnapshot.getText();
                    if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                    try {
                        double seconds = Double.parseDouble(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll(""));
                        plugin.getConfigManager().getEconomy().price(seconds);
                    } catch (NumberFormatException e) {
                        audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                        return Collections.emptyList();
                    }
                    return List.of(AnvilGUI.ResponseAction.close());
                }).itemLeft(generateItemStack(Material.PAPER, String.valueOf(plugin.getConfigManager().getEconomy().price()), MessagesUtil.economyPriceLore)).title("Economy Price").text(String.valueOf(plugin.getConfigManager().getEconomy().price())).plugin(plugin).open((Player) event.getWhoClicked());
            }
        }));

        gui.setItem(3, generateBooleanItem("Economy Permission", "dupe.economy.permission", "Permission: dupeplus.bypasseconomy", plugin.getConfigManager().getEconomy().isPermissionEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getEconomy().setPermissionEnabled(!plugin.getConfigManager().getEconomy().isPermissionEnabled());
            gui.updateItem(3, generateBooleanItem("Economy Permission", "dupe.economy.permission", "Permission: dupeplus.bypasseconomy", plugin.getConfigManager().getEconomy().isPermissionEnabled()));
        }));
        mainTemplate(plugin, gui);
        return gui;
    }
}
