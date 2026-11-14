package net.meme20200.dupeplus.utilities;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.types.Translation;
import net.meme20200.shared.Obj2IntMap;
import net.meme20200.shared.Obj2LongMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilAPIs {
    public static final int INVENTORY_LIMIT = 2304;
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    public static Pattern getWhitespacePattern() {
        return WHITESPACE;
    }

    /**
     * Checks if the material is a bundle or not.
     * @param material The material you want to check if it's a bundle or not.
     * @return True if the material is a bundle, otherwise false if not.
     * @since 1.4.3
     */
    public static boolean isBundleItem(Material material) {
        if (material == null) return false;
        return material.name().contains("BUNDLE");
    }

    /**
     * Checks if the material is a shulker box or not.
     * @param material The material you want to check if it's a shulker box or not.
     * @return True if the material is a shulker box, otherwise false if not.
     */
    public static boolean isShulkerItem(Material material) {
        if (material == null) return false;
        return material.name().contains("SHULKER");
    }


    /**
     * Checks if the server version is below the supported version
     * @param supportVersion The minimum version, or the supported version.
     * @return True If the server version is below the minimum version
     * @since 1.5
     */
    public static boolean isBelow(int[] supportVersion) {
        Matcher matcher = Pattern.compile("\\(MC: ([\\d.]+)[^)]*\\)").matcher(Bukkit.getVersion());
        if (matcher.find()) {
            String mcVersion = matcher.group(1);

            String[] splitVersion = mcVersion.split("\\.", 3);
            int[] mcServerVersion = new int[splitVersion.length];
            for (int i = 0; i < splitVersion.length; i++) {
                mcServerVersion[i] = Integer.parseInt(splitVersion[i]);
            }
            return Arrays.compare(mcServerVersion, supportVersion) < 0;
        }
        return false;
    }

    public static boolean overJava17() {
        try {
            String v = System.getProperty("java.specification.version");
            if (v == null) return false;
            int version = v.startsWith("1.") ? Integer.parseInt(v.substring(2)) : Integer.parseInt(v);
            return version >= 17;
        } catch (Exception e) {
            return false;
        }
    }

    public static Component translatable(BukkitDupePlus plugin, String key, String fallback, Player player, TagResolver tagResolver) {
        Translation translatedText = plugin.getTranslation();

        String template = translatedText.getMiniMessageString(key, Locale.forLanguageTag(player.getLocale().replace("_", "-")));
        if (template == null) template = fallback;

        return format(plugin, template, player, tagResolver);
    }

    public static Component translatable(BukkitDupePlus plugin, String key, String fallback, Player player) {
        Translation translatedText = plugin.getTranslation();

        String template = translatedText.getMiniMessageString(key, Locale.forLanguageTag(player.getLocale().replace("_", "-")));
        if (template == null) template = fallback;

        return format(plugin, template, player);
    }

    private static Component format(BukkitDupePlus plugin, String text, Player player, TagResolver tagResolver) {
        if (text == null || text.isEmpty()) return Component.empty();

        if (plugin.getConfigManager() != null && plugin.getConfigManager().getAddons().isPlaceholderAPIInstalled())
            text = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);

        return MiniMessage.miniMessage().deserialize(text, tagResolver);
    }

    public static Component format(BukkitDupePlus plugin, String text, Player player) {
        if (text == null || text.isEmpty()) return Component.empty();

        if (plugin.getConfigManager() != null && plugin.getConfigManager().getAddons().isPlaceholderAPIInstalled())
            text = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);

        return MiniMessage.miniMessage().deserialize(text);
    }

    public static Component format(String text) {
        if (text == null || text.isEmpty()) return Component.empty();
        return MiniMessage.miniMessage().deserialize(text);
    }

    public static Sound.@NonNull Source stringToCategory(String text) {
        return text != null ? Sound.Source.NAMES.valueOr(UtilAPIs.getWhitespacePattern().matcher(text).replaceAll("").toLowerCase(Locale.ROOT), Sound.Source.MASTER) : Sound.Source.MASTER;
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className, false, BukkitDupePlus.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> Obj2IntMap<V> createObj2IntMap() {
        if (classExists("it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap")) {
            try {
                return (Obj2IntMap<V>) Class.forName("net.meme20200.modern.FastUtilObj2Int")
                        .getDeclaredConstructor()
                        .newInstance();
            } catch (Throwable ignored) {
            }
        }
        return new Obj2IntMap.JavaImplementation<>();
    }

    @SuppressWarnings("unchecked")
    public static <V> Obj2LongMap<V> createObj2LongMap() {
        if (classExists("it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap")) {
            try {
                return (Obj2LongMap<V>) Class.forName("net.meme20200.modern.FastUtilObj2Long")
                        .getDeclaredConstructor()
                        .newInstance();
            } catch (Throwable ignored) {
            }
        }
        return new Obj2LongMap.JavaImplementation<>();
    }


    public static Component parseCustomText(String input) {
        if (input == null) return Component.empty();
        String processed = input.replace("§", "&");
        if (processed.contains("&")) {
            processed = MiniMessage.miniMessage().serialize(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(processed)
            );
        }
        return MiniMessage.miniMessage().deserialize(processed);
    }

    public static boolean isAir(Material material) {
        if (material == null) {
            return true;
        }
        switch (material) {
            case AIR:
            case CAVE_AIR:
            case VOID_AIR:
                return true;
            default:
                return false;
        }
    }

    public static Material redDye() {
        Material redDye = Material.matchMaterial("RED_DYE");
        if (redDye == null) {
            redDye = Material.matchMaterial("ROSE_RED");
            if (redDye == null) redDye = Material.STONE;
        }
        return redDye;
    }

    public static Material sign() {
        Material sign = Material.matchMaterial("OAK_SIGN");
        if (sign == null) {
            sign = Material.matchMaterial("SIGN");
            if (sign == null) sign = Material.STONE;
        }
        return sign;
    }
}
