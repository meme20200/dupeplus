package net.meme20200.Bukkit.menus.submenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.meme20200.Bukkit.menus.BlocklistMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;
import static net.meme20200.Bukkit.Utilities.UtilAPIs.getCustomSkull;

public class UnduplicatorMenu {

    public Gui getMenu(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Unduplicator", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false))
                .rows(3)
                .create();

        gui.setItem(13, new GuiItem(Material.AIR));

        gui.setDefaultClickAction(event -> {
            if (event.isShiftClick() && event.getClickedInventory() == player.getInventory()) {
                // Find the first empty slot (should be slot 5 if it's empty)
                ItemStack cursor = event.getCurrentItem();
                if (cursor != null && cursor.getType() != Material.AIR) {
                    if (event.getInventory().getItem(13) == null ||
                            event.getInventory().getItem(13).getType() == Material.AIR) {


                        event.setCancelled(false);

                        org.bukkit.Bukkit.getScheduler().runTaskLater(
                                getPlugin(),
                                () -> {
                                    ItemStack item = event.getInventory().getItem(13);
                                    if (item != null && item.getType() != Material.AIR) {
                                        item = processItem(player, item);
                                    }
                                    gui.getInventory().setItem(13, item);
                                },
                                2L
                        );
                        return;
                    }
                }
            } if (event.getSlot() == 13) {
                event.setCancelled(false);
                org.bukkit.Bukkit.getScheduler().runTaskLater(
                        getPlugin(),
                        () -> {
                            ItemStack placedItem = gui.getInventory().getItem(13);

                            if (placedItem != null && placedItem.getType() != Material.AIR) {
                                placedItem = processItem(player, placedItem);
                            }

                            gui.getInventory().setItem(13, placedItem);
                        },
                        2L
                );
            }
        });

        ItemStack backHead = getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3M2NmNjZkMzFiODNjZDhiODY0NGMxNTk1OGMxYjczYzhkOTczMjNiODAxMTcwYzFkODg2NGJiNmE4NDZkIn19fQ==");
        ItemMeta backMeta = backHead.getItemMeta();
        backMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(3, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    new BlocklistMenu().getMenu(player).open(player);
                }
        ));

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> {
            event.setCancelled(true);
        }));

        return gui;
    }

    private ItemStack processItem(Player player, ItemStack item) {
        if (!NBTEditor.contains(item, NBTEditor.CUSTOM_DATA, "dupenotallowed")) {
            item = NBTEditor.set(item, (byte) 1, NBTEditor.CUSTOM_DATA, "dupenotallowed");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.RECORDS,1f, 1f);
            return item;
        }
        return item;
    }
}
