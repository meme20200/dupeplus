package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.ItemUtils;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.types.UpdateAPIType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import static net.meme20200.dupeplus.utilities.MenuUtils.generateBooleanItem;
import static net.meme20200.dupeplus.utilities.MenuUtils.mainTemplate;

public class UpdateCheckerMenu {
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Update Checker Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(0, MenuUtils.generateBooleanItem("Update Checker Option", "updates.enabled", "If the plugin should check for updates", plugin.getConfigManager().getUpdateChecker().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getUpdateChecker().enabled(!plugin.getConfigManager().getUpdateChecker().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Update Checker Option", "updates.enabled", "If the plugin should check for updates", plugin.getConfigManager().getUpdateChecker().enabled()));
        }));

        gui.setItem(1, ItemBuilder.from(generateItemAPIType(plugin.getConfigManager().getUpdateChecker().apiType())).asGuiItem(event -> {
            event.setCancelled(true);
            final UpdateAPIType newAPIType = plugin.getConfigManager().getUpdateChecker().apiType().equals(UpdateAPIType.SpigotMC) ? UpdateAPIType.Modrinth : UpdateAPIType.SpigotMC;
            plugin.getConfigManager().getUpdateChecker().apiType(newAPIType);
            gui.updateItem(1, generateItemAPIType(newAPIType));
        }));

        gui.setItem(2, generateBooleanItem("Player Notify", "updates.notify.player-notify", "If admins should be notified when there's a new update.", plugin.getConfigManager().getUpdateChecker().playerNotify(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getUpdateChecker().playerNotify(!plugin.getConfigManager().getUpdateChecker().playerNotify());
            gui.updateItem(2, generateBooleanItem("Player Notify", "updates.notify.player-notify", "If admins should be notified when there's a new update.", plugin.getConfigManager().getUpdateChecker().playerNotify()));
        }));

        gui.setItem(3, generateBooleanItem("Console Notify", "updates.notify.console-notify", "If the console should be notified when there's a new update.", plugin.getConfigManager().getUpdateChecker().consoleNotify(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getUpdateChecker().consoleNotify(!plugin.getConfigManager().getUpdateChecker().consoleNotify());
            gui.updateItem(3, generateBooleanItem("Console Notify", "updates.notify.console-notify", "If the console should be notified when there's a new update.", plugin.getConfigManager().getUpdateChecker().consoleNotify()));
        }));

        mainTemplate(plugin, gui);
        return gui;
    }

    public @NonNull ItemStack generateItemAPIType(UpdateAPIType type) {
        ItemStack item = ItemUtils.generatePlayerHead(type == UpdateAPIType.Modrinth ? MenuUtils.MODRINTH_HEAD : MenuUtils.SPIGOTMC_HEAD);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(Component.text(type.toString()).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
            meta.setLore(type == UpdateAPIType.Modrinth ? MessagesUtil.modrinthUpdateLore : MessagesUtil.spigotUpdateLore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
