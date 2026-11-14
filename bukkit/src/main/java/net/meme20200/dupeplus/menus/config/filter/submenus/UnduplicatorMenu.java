package net.meme20200.dupeplus.menus.config.filter.submenus;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.meme20200.dupeplus.utilities.MenuUtils.filterTemplate;

public class UnduplicatorMenu {

    private final BukkitDupePlus plugin;

    public UnduplicatorMenu(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    public Gui getMenu() {
        Gui gui = Gui.gui().title(Component.text("Unduplicator", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(13, new GuiItem(Material.AIR));

        gui.setDefaultClickAction(event -> {
            if (!(event.getWhoClicked() instanceof Player)) return;
            Player player = (Player) event.getWhoClicked();

            Inventory clickedInv = event.getClickedInventory();
            if (clickedInv == null) return;

            Inventory topInventory = gui.getInventory();
            boolean isShiftToTop = event.isShiftClick() && clickedInv.equals(player.getInventory());
            boolean isClickSlot13 = event.getSlot() == 13 && clickedInv.equals(topInventory);

            if (isClickSlot13 && event.getClick() == ClickType.NUMBER_KEY) {
                event.setCancelled(false);
                scheduleProcessItem(topInventory, player);
                return;
            }

            if (isShiftToTop || isClickSlot13) {
                if (isShiftToTop) {
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem == null || UtilAPIs.isAir(currentItem.getType())) return;

                    ItemStack selectedItem = topInventory.getItem(13);
                    if (selectedItem != null && !UtilAPIs.isAir(selectedItem.getType())) return;
                }
                event.setCancelled(false);
                scheduleProcessItem(topInventory, player);
            }
        });

        filterTemplate(plugin, gui);
        return gui;
    }

    private void scheduleProcessItem(Inventory inventory, Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ItemStack placedItem = inventory.getItem(13);
            if (placedItem != null && !UtilAPIs.isAir(placedItem.getType())) {
                processItem(player, placedItem);
            }
            inventory.setItem(13, placedItem);
        }, 2L);
    }

    private void processItem(Player player, ItemStack item) {
        if (!plugin.getConfigyml().customNBTItem(item)) {
            NBT.modify(item, nbt -> {
                nbt.setBoolean(plugin.getConfigManager().getCustomNBTItem().tag(), true);
            });
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.RECORDS, 1f, 1f);
        }
    }
}
