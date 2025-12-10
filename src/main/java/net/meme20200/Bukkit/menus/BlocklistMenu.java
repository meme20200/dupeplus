package net.meme20200.Bukkit.menus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import net.meme20200.Bukkit.menus.submenus.AddItemMenu;
import net.meme20200.Bukkit.menus.submenus.ItemListMenu;
import net.meme20200.Bukkit.menus.submenus.UnduplicatorMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class BlocklistMenu {
    public Gui getMenu(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Blocklist Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false))
                .rows(3)
                .create();

        if (BukkitConfigyml.isblacklistEnabled()) {
            ItemStack blacklistItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta blacklistItemMeta = blacklistItem.getItemMeta();

            blacklistItemMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                    Component.text("Blacklist", NamedTextColor.WHITE)
            ));

            blacklistItemMeta.setLore(
                    List.of(
                            "",
                            BukkitComponentSerializer.legacy().serialize(Component.text("Blacklist will allow all items but only block duplicating items in the items list.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                            "",
                            BukkitComponentSerializer.legacy().serialize(Component.text("Click to toggle between Whitelist and Blacklist", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                    )
            );

            blacklistItem.setItemMeta(blacklistItemMeta);

            GuiItem blacklistToggle = ItemBuilder.from(blacklistItem).asGuiItem(event -> {
                event.setCancelled(true);
                player.closeInventory();
                BukkitConfigyml.setblacklistEnabled(false);
                getPlugin().saveConfig();
                if (player.hasPermission("dupeplus.blocklist")) {
                    getMenu(player).open(player);
                }
            });

            gui.setItem(2, 2, blacklistToggle);

        } else {
            ItemStack whitelistItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta whitelistItemMeta = whitelistItem.getItemMeta();

            whitelistItemMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                    Component.text("Whitelist", NamedTextColor.WHITE)
            ));

            whitelistItemMeta.setLore(
                    List.of(
                            "",
                            BukkitComponentSerializer.legacy().serialize(Component.text("Whitelist will block all items but only allow duplicating items in the items list.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                            "",
                            BukkitComponentSerializer.legacy().serialize(Component.text("Click to toggle between Whitelist and Blacklist", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                    )
            );

            whitelistItem.setItemMeta(whitelistItemMeta);

            GuiItem whitelistToggle = ItemBuilder.from(whitelistItem).asGuiItem(event -> {
                event.setCancelled(true);
                player.closeInventory();
                BukkitConfigyml.setblacklistEnabled(true);
                getPlugin().saveConfig();
                if (player.hasPermission("dupeplus.blocklist")) {
                    getMenu(player).open(player);
                }
            });

            gui.setItem(2, 2, whitelistToggle);
        }

        ItemStack ItemListItem = new ItemStack(Material.PAPER);
        ItemMeta ItemListMeta = ItemListItem.getItemMeta();

        ItemListMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                Component.text("Items List", NamedTextColor.WHITE)
        ));

        ItemListMeta.setLore(
                List.of(
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("All Items that are blocked/allowed from duplicating", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("Click to check all items blocked/allowed in /dupe", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                )
        );

        ItemListItem.setItemMeta(ItemListMeta);

        GuiItem ItemList = ItemBuilder.from(ItemListItem).asGuiItem(event -> {
            event.setCancelled(true);
            new ItemListMenu().getMenu(player).open(player);
        });

        gui.setItem(2, 4, ItemList);

        ItemStack AddItemItem = new ItemStack(Material.ANVIL);
        ItemMeta AddItemMeta = AddItemItem.getItemMeta();

        AddItemMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                Component.text("Add Item", NamedTextColor.WHITE)
        ));

        AddItemMeta.setLore(
                List.of(
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("Add an item to the Items list", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("Click to add an item to the items list", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                )
        );

        AddItemItem.setItemMeta(AddItemMeta);

        GuiItem AddItem = ItemBuilder.from(AddItemItem).asGuiItem(event -> {
            event.setCancelled(true);
            new AddItemMenu().getMenu(player).open(player);
        });

        gui.setItem(2, 6, AddItem);

        ItemStack UnduplicatorItem = new ItemStack(Material.RED_DYE);
        ItemMeta UnduplicatorMeta = UnduplicatorItem.getItemMeta();

        UnduplicatorMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                Component.text("Unduplicator", NamedTextColor.WHITE)
        ));

        UnduplicatorMeta.setLore(
                List.of(
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("Block exactly this item from being duplicated", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
                        "",
                        BukkitComponentSerializer.legacy().serialize(Component.text("Click to make an item unduplicateable", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                )
        );

        UnduplicatorItem.setItemMeta(UnduplicatorMeta);

        GuiItem Unduplicator = ItemBuilder.from(UnduplicatorItem).asGuiItem(event -> {
            event.setCancelled(true);
            new UnduplicatorMenu().getMenu(player).open(player);
        });

        gui.setItem(2, 8, Unduplicator);

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> {
            event.setCancelled(true);
        }));

        return gui;
    }
}
