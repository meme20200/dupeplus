package net.meme20200.Bukkit.menus.submenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import net.meme20200.Bukkit.menus.BlocklistMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static net.meme20200.Bukkit.Utilities.UtilAPIs.getCustomSkull;

public class ItemListMenu {
    public PaginatedGui getMenu(Player player) {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text("All Items in Items List", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false))
                .rows(6)
                .create();

        List<GuiItem> guiList = new ArrayList<>();

        BukkitConfigyml.listedItems().forEach(material -> {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setLore(
                    List.of(
                            "",
                            BukkitComponentSerializer.legacy().serialize(Component.text("Click to remove item", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false))
                    )
            );


            GuiItem item = ItemBuilder.from(itemStack).asGuiItem(event -> {
                event.setCancelled(true);

                BukkitConfigyml.removeItemfromlistedItems(material);

                player.closeInventory();
                getMenu(player).open(player);
            });

            guiList.add(item);
        });

        gui.addItem(guiList.toArray(new GuiItem[0]));

        ItemStack previousHead = getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM5MjYyM2NhZmRmNzQ1YmZjYjMxZDA1ZGJjZDI0N2Q1NzFkODk3ZjIyYWNlMzhlZTU0ZWVkM2ZiOTYzNiJ9fX0=");
        ItemMeta previousMeta = previousHead.getItemMeta();
        previousMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(MiniMessage.miniMessage().deserialize("<yellow><white>←</white> <b>ᴘʀᴇᴠɪᴏᴜs</b></yellow>").decoration(TextDecoration.ITALIC, false)));
        previousHead.setItemMeta(previousMeta);

        gui.setItem(6, 4, ItemBuilder.from(previousHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    gui.previous();
                }
        ));

        ItemStack nextHead = getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJjYTkwZWJhZmRmNTdkZjRjMzgwY2YyNjU1YWE3YjRlYzZhNGJkYmQxNTUxNmViZmRlNDMyN2ExZTI3In19fQ==");
        ItemMeta nextMeta = nextHead.getItemMeta();
        nextMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(MiniMessage.miniMessage().deserialize("<b><green>NEXT</green></b> <white>→</white>").decoration(TextDecoration.ITALIC, false)));
        nextHead.setItemMeta(nextMeta);

        gui.setItem(6, 6, ItemBuilder.from(nextHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    gui.next();
                }
        ));

        ItemStack backHead = getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3M2NmNjZkMzFiODNjZDhiODY0NGMxNTk1OGMxYjczYzhkOTczMjNiODAxMTcwYzFkODg2NGJiNmE4NDZkIn19fQ==");
        ItemMeta backMeta = backHead.getItemMeta();
        backMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(6, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    new BlocklistMenu().getMenu(player).open(event.getWhoClicked());
                }
        ));


        gui.getFiller().fillBottom(ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).asGuiItem(event -> {
            event.setCancelled(true);
        }));

        return gui;
    }

}
