package net.meme20200.dupeplus.menus.config.filter.submenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.menus.config.filter.FilterMenu;
import net.meme20200.dupeplus.utilities.ItemUtils;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemListMenu {
    private final BukkitDupePlus plugin;

    public ItemListMenu(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    public PaginatedGui getMenu() {
        PaginatedGui gui = Gui.paginated().title(Component.text("All Items in Items List", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(6).create();
        List<GuiItem> guiList = new ArrayList<>();

        for (Material material : plugin.getConfigManager().getVanillaFilter()) {
            if (UtilAPIs.isAir(material)) continue;
            GuiItem item = ItemBuilder.from(generateItem(material)).asGuiItem(event -> {
                event.setCancelled(true);
                if (event.getClick() != ClickType.DROP && event.getClick() != ClickType.CONTROL_DROP) return;
                plugin.getConfigyml().removeItemFromFilter(material);
                event.getWhoClicked().closeInventory();
                getMenu().open(event.getWhoClicked());
            });

            guiList.add(item);
        }

        controls(gui);
        if (!guiList.isEmpty()) gui.addItem(guiList.toArray(new GuiItem[0]));
        return gui;
    }

    private void controls(PaginatedGui gui) {
        ItemStack addButton = new ItemStack(UtilAPIs.sign());
        ItemMeta addButtonMeta = addButton.getItemMeta();
        if (addButtonMeta != null)
            addButtonMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<green>+ <b>ADD</b></green>").decoration(TextDecoration.ITALIC, false)));
        addButton.setItemMeta(addButtonMeta);

        gui.setItem(6, 1, ItemBuilder.from(addButton).asGuiItem(event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            new AddItemMenu(plugin).getMenu().open(event.getWhoClicked());
        }));

        ItemStack previousHead = ItemUtils.generatePlayerHead(MenuUtils.PREVIOUS_HEAD);
        ItemMeta previousMeta = previousHead.getItemMeta();
        if (previousMeta != null)
            previousMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<yellow><white>←</white> <b>PREVIOUS</b></yellow>").decoration(TextDecoration.ITALIC, false)));
        previousHead.setItemMeta(previousMeta);

        gui.setItem(6, 4, ItemBuilder.from(previousHead).asGuiItem(event -> {
            event.setCancelled(true);
            gui.previous();
        }));

        ItemStack nextHead = ItemUtils.generatePlayerHead(MenuUtils.NEXT_HEAD);
        ItemMeta nextMeta = nextHead.getItemMeta();
        if (nextMeta != null)
            nextMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<b><green>NEXT</green></b> <white>→</white>").decoration(TextDecoration.ITALIC, false)));
        nextHead.setItemMeta(nextMeta);

        gui.setItem(6, 6, ItemBuilder.from(nextHead).asGuiItem(event -> {
            event.setCancelled(true);
            gui.next();
        }));

        ItemStack backHead = ItemUtils.generatePlayerHead(MenuUtils.BACK_HEAD);
        ItemMeta backMeta = backHead.getItemMeta();
        if (backMeta != null)
            backMeta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<white>←</white> <b><red>BACK</red></b>").decoration(TextDecoration.ITALIC, false)));
        backHead.setItemMeta(backMeta);

        gui.setItem(6, 9, ItemBuilder.from(backHead).asGuiItem(event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            new FilterMenu().getMenu(plugin).open(event.getWhoClicked());
        }));

        gui.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> event.setCancelled(true)));
    }

    private ItemStack generateItem(Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(MessagesUtil.itemListLore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

}
