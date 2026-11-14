package net.meme20200.dupeplus.utilities;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.types.CustomItemPluginType;
import net.meme20200.dupeplus.utilities.types.config.CustomNBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class ItemUtils {

    private static boolean supportsItemName = true;

    public static boolean supportsItemName() {
        return supportsItemName;
    }

    /**
     * Checks if the item is a blocked ItemsAdder item or not.
     * <p>
     * If it returns false, it does not mean that the item is not an itemsadder, it means the item didn't match anything in {@code dupe.filter.items} with the ItemsAdder namespace ({@code itemsadder:}).
     * <h1>Syntax</h1>
     * Shown in the code block below is the syntax of how the plugin interprets the itemsadder filter.
     * {@code plugins/DupePlus/config.yml}
     * <pre>{@code
     * dupe:
     *   ...
     *   filter:
     *      ...
     *      items:
     *       ...
     *       - itemsadder:namespace:id
     *       - itemsadder:namespace:*
     *       - itemsadder:id
     *       - itemsadder:*
     * }</pre>
     *
     * @param itemStack The itemStack to check if it's a blocked ItemsAdder item.
     * @param compiledFilters It's best to pass {@link net.meme20200.dupeplus.utilities.config.BukkitConfigManager#getCustomFilter(CustomItemPluginType) BukkitConfigManager.getCustomFilter(CustomItemPluginType)} to this function.
     * @return True if the item is an itemsadder item and is in the filter, otherwise false.
     * @since 1.5
     */
    public static boolean isFilteredItemsAdderItem(ItemStack itemStack, Map<String, Set<String>> compiledFilters) {
        if (itemStack == null || compiledFilters == null || compiledFilters.isEmpty()) return false;
        return NBT.get(itemStack, nbt -> {
            ReadableNBT itemsadderCompound = nbt.getCompound("itemsadder");
            if (itemsadderCompound == null) return false;
            String namespace = itemsadderCompound.getString("namespace");
            String id = itemsadderCompound.getString("id");
            if ((namespace == null || namespace.isBlank()) && (namespace == null || id.isBlank())) return false;

            Set<String> globalWildcard = compiledFilters.get("*");
            if (globalWildcard != null && globalWildcard.contains("*")) return true;

            Set<String> namespaceSpecific = compiledFilters.get(namespace);
            if (namespaceSpecific != null && namespaceSpecific.contains("*")) return true;

            Set<String> emptyNamespace = compiledFilters.get("");
            if (emptyNamespace != null && (emptyNamespace.contains("*") || emptyNamespace.contains(id))) return true;
            return namespaceSpecific != null && namespaceSpecific.contains(id);
        });
    }

    public static boolean isFilteredOraxenItem(ItemStack itemStack, Map<String, Set<String>> compiledFilters) {
        if (itemStack == null || compiledFilters == null || compiledFilters.isEmpty()) return false;
        return NBT.get(itemStack, nbt -> {
            ReadableNBT oraxenCompound = nbt.getCompound("PublicBukkitValues");
            if (oraxenCompound == null) return false;

            String id = oraxenCompound.getString("oraxen:id");
            if (id == null || id.isBlank()) return false;

            Set<String> oraxenItems = compiledFilters.get("oraxen");
            return oraxenItems != null && (oraxenItems.contains("*") || oraxenItems.contains(id));
        });
    }

    public static boolean isFilteredNexoItem(ItemStack itemStack, Map<String, Set<String>> compiledFilters) {
        if (itemStack == null || compiledFilters == null || compiledFilters.isEmpty()) return false;
        return NBT.get(itemStack, nbt -> {
            ReadableNBT nexoCompound = nbt.getCompound("PublicBukkitValues");
            if (nexoCompound == null) return false;

            String id = nexoCompound.getString("nexo:id");
            if (id == null || id.isBlank()) return false;

            Set<String> nexoItems = compiledFilters.get("nexo");
            return nexoItems != null && (nexoItems.contains("*") || nexoItems.contains(id));
        });
    }

    public static boolean isFilteredMinehutCosmeticItem(ItemStack itemStack, Map<String, Set<String>> compiledFilters) {
        if (itemStack == null || compiledFilters == null || compiledFilters.isEmpty()) return false;
        return NBT.get(itemStack, nbt -> {
            ReadableNBT publicBukkitValues = nbt.getCompound("PublicBukkitValues");
            if (publicBukkitValues == null) return false;

            String namespace = publicBukkitValues.getString("minehutcosmetics:cosmetic_category");
            String id = publicBukkitValues.getString("minehutcosmetics:cosmetic_id");
            if ((namespace == null || namespace.isBlank()) && (namespace == null || id.isBlank())) return false;

            Set<String> globalWildcard = compiledFilters.get("*");
            if (globalWildcard != null && globalWildcard.contains("*")) return true;

            Set<String> namespaceSpecific = compiledFilters.get(namespace);
            if (namespaceSpecific != null && namespaceSpecific.contains("*")) return true;

            Set<String> emptyNamespace = compiledFilters.get("");
            if (emptyNamespace != null && (emptyNamespace.contains("*") || emptyNamespace.contains(id))) return true;

            return namespaceSpecific != null && namespaceSpecific.contains(id);
        });
    }

    public static @NonNull Component itemStackName(@NonNull ItemStack itemStack) {
        if (itemStack.getItemMeta() == null)
            return Component.translatable((itemStack.getType().isBlock() ? "block.minecraft." : "item.minecraft.") + itemStack.getType().getKey().getKey());
        if (supportsItemName) {
            try {
                if (itemStack.getItemMeta().hasItemName())
                    return LegacyComponentSerializer.legacySection().deserialize(itemStack.getItemMeta().getItemName());
            } catch (NoSuchMethodError ignored) {
                supportsItemName = false;
                if (itemStack.getItemMeta().hasDisplayName())
                    return LegacyComponentSerializer.legacySection().deserialize(itemStack.getItemMeta().getDisplayName());
            }
        } else {
            if (itemStack.getItemMeta().hasDisplayName())
                return LegacyComponentSerializer.legacySection().deserialize(itemStack.getItemMeta().getDisplayName());
        }
        return Component.translatable((itemStack.getType().isBlock() ? "block.minecraft." : "item.minecraft.") + itemStack.getType().getKey().getKey());
    }

    public static @NonNull ItemStack generateItemStack(Material material, String text, String... lore) {
        return generateItemStack(material, text, Arrays.asList(lore));
    }

    public static @NonNull ItemStack generateItemStack(Material material, String text, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {

            if (supportsItemName) {
                try {
                    itemStack.getItemMeta().setItemName(text);
                } catch (NoSuchMethodError ignored) {
                    supportsItemName = false;
                    itemStack.getItemMeta().setDisplayName(text);
                }
            } else {
                itemStack.getItemMeta().setDisplayName(text);
            }
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public static @NonNull ItemStack generatePlayerHead(String base64) {
        final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (UtilAPIs.isBelow(new int[]{1,20,4})) {
            NBT.modify(item, nbt -> {
                ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");
                skullOwnerCompound.setUUID("Id", UUID.randomUUID());

                skullOwnerCompound.getOrCreateCompound("Properties")
                        .getCompoundList("textures")
                        .addCompound()
                        .setString("Value", base64);
            });
        } else {
            NBT.modifyComponents(item, nbt -> {
                ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
                profileNbt.setUUID("id", UUID.randomUUID());
                ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
                propertiesNbt.setString("name", "textures");
                propertiesNbt.setString("value", base64);
            });
        }
        return item;
    }

    /**
     * Makes the item undupeable
     * @param itemStack The item to make undupeable
     * @param tag From {@link BukkitDupePlus {@code plugin}} {@link CustomNBTItem#tag() {@code .getConfigManager().getCustomNBTItem().tag()}}
     */
    public static void undupeableItem(@NonNull ItemStack itemStack, String tag) {
        NBT.modify(itemStack, nbt -> {
            nbt.setBoolean(tag, true);
        });
    }

    /**
     * Makes the item dupeable
     * @param itemStack The item to make dupeable
     * @param tag From {@link CustomNBTItem#tag() {@code plugin.getConfigManager().getCustomNBTItem().tag()}}
     */
    public static void dupeableItem(@NonNull ItemStack itemStack, String tag) {
        NBT.modify(itemStack, nbt -> {
            nbt.removeKey(tag);
        });
    }

    public static void supportsItemName(boolean supportsItemName) {
        ItemUtils.supportsItemName = supportsItemName;
    }
}
