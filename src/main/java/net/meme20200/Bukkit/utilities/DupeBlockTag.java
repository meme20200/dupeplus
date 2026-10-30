package net.meme20200.Bukkit.utilities;

import de.tr7zw.changeme.nbtapi.NBT;
import net.meme20200.Bukkit.BukkitDupePlus;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class DupeBlockTag {
    // Stored in the PersistentDataContainer, which works on every server version without NBT-API
    private static final NamespacedKey KEY = new NamespacedKey(JavaPlugin.getPlugin(BukkitDupePlus.class), "dupenotallowed");
    // Items blocked by older versions carry this plain tag, written through NBT-API
    private static final String LEGACY_KEY = "dupenotallowed";

    public static boolean isBlocked(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getPersistentDataContainer().has(KEY, PersistentDataType.BYTE)) return true;
        return isLegacyBlocked(item);
    }

    public static void block(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
    }

    public static void unblock(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().remove(KEY);
            item.setItemMeta(meta);
        }
        try {
            NBT.modify(item, nbt -> {
                nbt.removeKey(LEGACY_KEY);
            });
        } catch (Throwable ignored) {
            // NBT-API does not support this server version yet, nothing to clean up
        }
    }

    private static boolean isLegacyBlocked(ItemStack item) {
        try {
            return NBT.get(item, nbt -> {
                return nbt.getBoolean(LEGACY_KEY);
            });
        } catch (Throwable ignored) {
            // NBT-API does not support this server version yet, so only the PDC tag counts
            return false;
        }
    }
}
