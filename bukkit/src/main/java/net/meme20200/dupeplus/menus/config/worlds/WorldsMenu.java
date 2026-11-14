package net.meme20200.dupeplus.menus.config.worlds;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.types.ModeType;
import org.bukkit.Material;

import static net.meme20200.dupeplus.utilities.MenuUtils.*;

public class WorldsMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Worlds Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(0, MenuUtils.generateBooleanItem("World Option", "dupe.words.enabled", "If the worlds feature should be enabled or not.", plugin.getConfigManager().getWorlds().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getWorlds().enabled(!plugin.getConfigManager().getWorlds().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("World Option", "dupe.words.enabled", "If the worlds feature should be enabled or not.", plugin.getConfigManager().getWorlds().enabled()));
        }));

        gui.setItem(1, generateToggleFilterMode(plugin.getConfigManager().getWorlds().modeType() == ModeType.BLACKLIST ? "Blacklist" : "Whitelist", "dupe.worlds.mode", plugin.getConfigManager().getWorlds().modeType() == ModeType.BLACKLIST, event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getWorlds().enabled(!plugin.getConfigManager().getWorlds().enabled());
            gui.updateItem(1, generateToggleFilterMode(plugin.getConfigManager().getWorlds().modeType() == ModeType.BLACKLIST ? "Blacklist" : "Whitelist", "dupe.worlds.mode", plugin.getConfigManager().getWorlds().modeType() == ModeType.BLACKLIST));
        }));

        gui.setItem(2, generateSectionItem("Worlds List", "dupe.worlds.worlds", Material.CHEST, event -> {
            event.setCancelled(true);
            new WorldsListMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        mainTemplate(plugin, gui);
        return gui;
    }
}
