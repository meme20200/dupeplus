package net.meme20200.dupeplus.menus.config;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.menus.config.filter.FilterMenu;
import net.meme20200.dupeplus.menus.config.general.GeneralMenu;
import net.meme20200.dupeplus.menus.config.multiplier.MultiplierMenu;
import net.meme20200.dupeplus.menus.config.submenus.*;
import net.meme20200.dupeplus.menus.config.worlds.WorldsMenu;
import org.bukkit.Material;

import static net.meme20200.dupeplus.utilities.MenuUtils.generateBooleanItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.generateSectionItem;

public class ConfigMenu {

    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Config Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(6).create();
        gui.setItem(0, generateSectionItem("General", "dupe.*", Material.TRIPWIRE_HOOK, event -> {
            event.setCancelled(true);
            new GeneralMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(1, generateSectionItem("Custom Commands", "dupe.custom-command.*", Material.COMMAND_BLOCK, event -> {
            event.setCancelled(true);
            new CustomCommandsMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(3, generateSectionItem("Custom NBT Item", "dupe.custom-nbt-item.*", Material.ANVIL, event -> {
            event.setCancelled(true);
            new CustomNBTItemMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(4, generateSectionItem("Filter", "dupe.filter.*", Material.BOOK, event -> {
            event.setCancelled(true);
            new FilterMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(2, generateSectionItem("Worlds", "dupe.worlds.*", Material.GRASS_BLOCK, event -> {
            event.setCancelled(true);
            new WorldsMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(5, generateSectionItem("Cooldown", "dupe.cooldown.*", Material.CLOCK, event -> {
            event.setCancelled(true);
            new CooldownMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(6, generateSectionItem("Multiplier", "dupe.multiplier.*", Material.DIAMOND, event -> {
            event.setCancelled(true);
            new MultiplierMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(7, generateSectionItem("Lore", "dupe.lore.*", Material.NAME_TAG, event -> {
            event.setCancelled(true);
            new LoreMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(8, generateSectionItem("Sound Feedback", "dupe.sound-feedback.*", Material.NOTE_BLOCK, event -> {
            event.setCancelled(true);
            new SoundFeedbackMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(9, generateSectionItem("Economy", "dupe.economy.*", Material.EMERALD, event -> {
            event.setCancelled(true);
            new EconomyMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        for (int i = 27; i < 36; i++) {
            gui.setItem(i, ItemBuilder.from(Material.RED_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
        }

        gui.setItem(36, generateBooleanItem("Shorthand Commands", "dupeplus.shorthand-commands", "If /dupeblock, /dupeunblock and /dupefilter should be enabled. (Restart required)", plugin.getConfigManager().adminShorthandCommands(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().adminShorthandCommands(!plugin.getConfigManager().adminShorthandCommands());
            gui.updateItem(36, generateBooleanItem("Shorthand Commands", "dupeplus.shorthand-commands", "If /dupeblock, /dupeunblock and /dupefilter should be enabled. (Restart required)", plugin.getConfigManager().adminShorthandCommands()));
        }));
        gui.setItem(37, generateSectionItem("Update Checker", "updates.*", Material.DAYLIGHT_DETECTOR, event -> {
            event.setCancelled(true);
            new UpdateCheckerMenu().getMenu(plugin).open(event.getWhoClicked());
        }));
        gui.setItem(38, generateSectionItem("Integrations", "integrations.*", Material.HOPPER, event -> {
            event.setCancelled(true);
            new IntegrationsMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
        return gui;
    }
}
