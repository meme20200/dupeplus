package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;

import static net.meme20200.dupeplus.utilities.MenuUtils.generateBooleanItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class IntegrationsMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Integrations Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();
        gui.setItem(0, generateBooleanItem("PlaceholderAPI", "integrations.placeholderapi", "Should PlaceholderAPI be used in this plugin?", plugin.getConfigManager().getAddons().isPlaceholderAPIEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getAddons().setPlaceholderAPIEnabled(!plugin.getConfigManager().getAddons().isPlaceholderAPIEnabled());
            gui.updateItem(0, generateBooleanItem("PlaceholderAPI", "integrations.placeholderapi", "Should PlaceholderAPI in DupePlus?", plugin.getConfigManager().getAddons().isPlaceholderAPIEnabled()));
        }));

        gui.setItem(1, generateBooleanItem("Vault", "integrations.vault", "Should Vault be used in this plugin?", plugin.getConfigManager().getAddons().isVaultEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getAddons().setVaultEnabled(!plugin.getConfigManager().getAddons().isVaultEnabled());
            gui.updateItem(1, generateBooleanItem("Vault", "integrations.vault", "Should Vault be used in this plugin?", plugin.getConfigManager().getAddons().isVaultEnabled()));
        }));

        mainTemplate(plugin, gui);
        return gui;
    }
}
