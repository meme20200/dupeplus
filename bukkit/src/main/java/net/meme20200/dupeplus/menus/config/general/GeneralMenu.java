package net.meme20200.dupeplus.menus.config.general;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import org.bukkit.Material;

import static net.meme20200.dupeplus.utilities.MenuUtils.generateSectionItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class GeneralMenu {

    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("General Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(0, MenuUtils.generateBooleanItem("Permission", "dupe.permission", "If the dupe command should be behind a permission.", plugin.getConfigManager().permission(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().permission(!plugin.getConfigManager().permission());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Permission", "dupe.permission", "If the dupe command should be behind a permission.", plugin.getConfigManager().permission()));
        }));

        gui.setItem(1, generateSectionItem("Aliases", "dupe.aliases", Material.CHEST, event -> {
            event.setCancelled(true);
            new AliasesMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        mainTemplate(plugin, gui);
        return gui;
    }
}
