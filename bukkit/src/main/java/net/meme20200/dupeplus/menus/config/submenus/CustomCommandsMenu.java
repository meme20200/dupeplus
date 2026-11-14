package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.audience.Audience;
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
import static net.meme20200.dupeplus.utilities.MenuUtils.generateTextItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class CustomCommandsMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Custom Command Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, MenuUtils.generateBooleanItem("Custom Command Option", "dupe.custom-command.enabled", "If the custom command feature should be enabled or not.", plugin.getConfigManager().getCustomCommand().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getCustomCommand().enabled(!plugin.getConfigManager().getCustomCommand().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Custom Command Option", "dupe.custom-command.enabled", "If the custom command feature should be enabled or not.", plugin.getConfigManager().getCustomCommand().enabled()));
        }));

        gui.setItem(1, generateTextItem("Custom Command Name", "dupe.custom-command.name", "The name used to create the dupe command /<name>.", "/" + plugin.getConfigManager().getCustomCommand().name(), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience p = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                String oldCommandName = plugin.getConfigManager().getCustomCommand().name();
                if (userInput == null || userInput.isBlank() || UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT).equals(oldCommandName)) return List.of(AnvilGUI.ResponseAction.close());
                userInput = UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT);
                plugin.getConfigManager().getCustomCommand().name(userInput);
                plugin.getBukkitDupeCommand().reloadCommand(oldCommandName);
                p.sendMessage(Component.text("DupePlus").color(NamedTextColor.GREEN).appendSpace().append(Component.text("|").color(NamedTextColor.DARK_GRAY)).appendSpace().append(Component.text("Players will need to rejoin to see the changed dupe command in tab complete").color(NamedTextColor.WHITE)));
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.PAPER, plugin.getConfigManager().getCustomCommand().name(), MessagesUtil.customCommandNameLore)).title("Custom Command Name").text(plugin.getConfigManager().getCustomCommand().name()).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        mainTemplate(plugin, gui);
        return gui;
    }
}
