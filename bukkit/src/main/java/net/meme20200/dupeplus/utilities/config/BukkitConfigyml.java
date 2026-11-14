package net.meme20200.dupeplus.utilities.config;

import de.tr7zw.changeme.nbtapi.NBT;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.ItemUtils;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.meme20200.dupeplus.utilities.types.CustomItemPluginType;
import net.meme20200.dupeplus.utilities.types.LoreMode;
import net.meme20200.dupeplus.utilities.types.ModeType;
import net.meme20200.dupeplus.utilities.types.UpdateAPIType;
import net.meme20200.dupeplus.utilities.types.config.*;
import net.meme20200.shared.Obj2IntMap;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.*;

import static net.meme20200.dupeplus.utilities.ItemUtils.itemStackName;
import static net.meme20200.dupeplus.utilities.UtilAPIs.isBundleItem;


public final class BukkitConfigyml {
    private final BukkitDupePlus plugin;

    public BukkitConfigyml(@NonNull BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    /**
     * @return The file {@code config.yml} as a {@link FileConfiguration file configuration}
     * @since 1.5
     * @deprecated Use {@link BukkitConfigManager} instead
     */
    @Deprecated(forRemoval = true)
    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }


    /**
     * Was used to send to the console when the console executed a player-only command.
     *
     * @return The console-message that is sent to the console.
     * @since 1.0
     * @deprecated It's not used anywhere. And just use {@link BukkitConfigManager#getConsoleMessage()} instead of this.
     */
    @Deprecated(forRemoval = true)
    public String consoleMessage() {
        return plugin.getConfigManager().getConsoleMessage();
    }

    /**
     * Reloads the config
     *
     * @return True if the custom command has been modified. The result can be ignored.
     * @since 1.0
     */
    @Deprecated(forRemoval = true)
    public boolean reloadConfig() {
        return plugin.getConfigManager().reloadConfig();
    }


    /**
     * The message sent to the player when they attempt to duplicate while holding nothing.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @since 1.0
     */
    public void nothingMessage(Player player, Audience p) {
        Component a = UtilAPIs.translatable(plugin, "nothing-message", "", player, Placeholder.component("prefix", getPrefix(player)));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }


    /**
     * The permission option. When turned on, DupePlus will check if the player has the permission or not whenever they attempt to duplicate.
     *
     * @return If the {@code dupe.permission} feature in {@link BukkitConfigyml#getConfig() {@code config.yml}} is enabled.
     * @since 1.0
     * @deprecated Use {@link BukkitConfigManager#permission()} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean dupePermissionOption() {
        return plugin.getConfigManager().permission();
    }


    /**
     * Checks if the player has the dupe permission or not.
     * <p>
     * {@link BukkitConfigyml#dupePermissionOption()} is used before this function to ensure that it
     *
     * @param player The player you want to check.
     * @return If the player has the permission or not.
     * @since 1.0
     */
    public boolean hasDupePermission(Player player) {
        return !plugin.getConfigManager().permission() || player.hasPermission("dupeplus.dupe");
    }

    /**
     * Checks if the Sound Feedback ({@code dupe.sound-feedback}) feature is enabled or not.
     *
     * @return True if the Sound Feedback ({@code dupe.sound-feedback}) is enabled in {@link BukkitConfigyml#getConfig() {@code config.yml}}.
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#enabled() BukkitConfigManager.getSoundFeedback().enabled()} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean isSoundFeedbackEnabled() {
        return plugin.getConfigManager().getSoundFeedback().enabled();
    }

    /**
     * The sound feedback sound value {@code dupe.sound-feedback.sound} in {@link BukkitConfigyml#getConfig() {@code config.yml}}
     *
     * @return The sound played when the player duplicates an item.
     * <p>Defaults to minecraft:block.amethyst_block.chime if not found.
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#sound() BukkitConfigManager.getSoundFeedback().sound()} instead.
     */
    @Deprecated(forRemoval = true)
    public String soundFeedbackSoundValue() {
        return plugin.getConfigManager().getSoundFeedback().sound().toString();
    }

    /**
     * The sound feedback sound category {@code dupe.sound-feedback.category} in {@link BukkitConfigyml#getConfig() {@code config.yml}} in which the sound will play to the player.
     *
     * @return The sound category where the sound will play.
     * <p>Defaults to master if not found.
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#category()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public Sound.@NonNull Source soundFeedbackSoundCategory() {
        return plugin.getConfigManager().getSoundFeedback().category();
    }

    /**
     * The sound feedback sound volume {@code dupe.sound-feedback.volume} in {@link BukkitConfigyml#getConfig() {@code config.yml}} when the sound plays.
     *
     * @return The volume of the sound
     * <p>Defaults to 1 if not found
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#volume()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public @NonNull Double soundFeedbackVolume() {
        return (double) plugin.getConfigManager().getSoundFeedback().volume();
    }

    /**
     * The sound feedback sound pitch ({@code dupe.sound-feedback.pitch} in {@link BukkitConfigyml#getConfig() {@code config.yml}}) of the played sound.
     *
     * @return The pitch of the sound
     * <p>Defaults to 1 if not found
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#pitch()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public @NonNull Double soundFeedbackPitch() {
        return (double) plugin.getConfigManager().getSoundFeedback().pitch();
    }

    /**
     * Plays the sound to the player.
     *
     * @param p The player as an audience. You can pass {@code getPlugin().adventure().player(player)} to this argument.
     * @since 1.4.3
     */
    public void playDupeSuccessfulSound(Audience p) {
        final Key soundtoPlay = plugin.getConfigManager().getSoundFeedback().sound();
        final Sound.Source category = plugin.getConfigManager().getSoundFeedback().category();
        float pitch = plugin.getConfigManager().getSoundFeedback().pitch();
        float volume = plugin.getConfigManager().getSoundFeedback().volume();

        p.playSound(Sound.sound(soundtoPlay, category, volume, pitch));
    }

    /**
     * Checks if the sound-feedback ({@code dupe.sound-feedback.category} in {@link BukkitConfigyml#getConfig() {@code config.yml}}) permission option is enabled or not.
     * <p>
     * If enabled, the plugin will check if the player has the sound {@code dupeplus.sound} permission.
     *
     * @return True if the sound feedback permission option is enabled.
     * @since 1.4.3
     * @deprecated Use {@link SoundFeedback#isPermissionEnabled()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean soundFeedbackPermissionOption() {
        return plugin.getConfigManager().getSoundFeedback().isPermissionEnabled();
    }

    /**
     * Checks if the player has the sound {@code dupeplus.sound} permission.
     * <p>
     * This will return
     *
     * @return True if the player has the sound feedback permission.
     * @since 1.4.3
     */
    public boolean hasSoundFeedbackPermission(Player player) {
        return !plugin.getConfigManager().getSoundFeedback().isPermissionEnabled() || player.hasPermission("dupeplus.sound");
    }

    /**
     * @param player The player to get the item from.
     * @return The item held by the player
     * @since 1.0
     */
    @NonNull
    public ItemStack getDupedItem(Player player) {
        if (plugin.getConfigManager().getDupeOn().equalsIgnoreCase("offhand"))
            return player.getInventory().getItemInOffHand();
        return player.getInventory().getItemInMainHand();
    }

    /**
     * Checks if the filter ({@code dupe.filter}) is enabled in {@link BukkitConfigyml#getConfig() {@code config.yml}}
     *
     * @return True if the filter ({@code dupe.filter}) is enabled in {@link BukkitConfigyml#getConfig() {@code config.yml}}
     * @since 1.1 (originally)
     * @deprecated Use {@link Filter#enabled() Filter.enabled()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean isFilterEnabled() {
        return plugin.getConfigManager().getFilter().enabled();
    }


    /**
     * Checks if the filter mode ({@code dupe.filter.mode}) is blacklist in {@link BukkitConfigyml#getConfig() {@code config.yml}}
     *
     * @return True if the filter mode ({@code dupe.filter.mode}) is blacklist in {@link BukkitConfigyml#getConfig() {@code config.yml}}.<p>
     * Otherwise, it will return false, which means that the mode is whitelist.
     * @since 1.1 (originally)
     * @deprecated Use {@link Filter#modeType() Filter.modeType()} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean isFilterModeBlacklist() {
        return plugin.getConfigManager().getFilter().modeType().equals(ModeType.BLACKLIST);
    }

    /**
     * Sets the filter mode ({@code dupe.filter.mode}) in {@link BukkitConfigyml#getConfig() {@code config.yml}} by if it should be "blacklist".
     *
     * @param blacklist Pass true to the argument to set the mode to blacklist.<p>
     *                  Otherwise pass false to the argument to set the mode to whitelist.
     * @deprecated Use {@link Filter#modeType(ModeType) Filter.modeType(ModeType)} from {@link BukkitConfigManager} instead.
     */
    @Deprecated(forRemoval = true)
    public void setFilterModeByBlacklist(boolean blacklist) {
        plugin.getConfigManager().getFilter().modeType(blacklist ? ModeType.BLACKLIST : ModeType.WHITELIST);
    }

    /**
     * @return The Minecraft filter {@link BukkitConfigManager#getVanillaFilter()}
     * @deprecated Use {@link BukkitConfigManager#getVanillaFilter()} instead.
     */
    @Deprecated(forRemoval = true)
    public Set<Material> filterItems() {
        return plugin.getConfigManager().getVanillaFilter();
    }

    /**
     * Removes an item from the filter items ({@code dupe.filter.items}) in {@link BukkitConfigyml#getConfig() {@code config.yml}}.
     *
     * @param toRemove The material to remove from the filter items.
     */
    public void removeItemFromFilter(Material toRemove) {
        plugin.getConfigManager().getVanillaFilter().remove(toRemove);
    }

    /**
     * Adds an item to the filter items ({@code dupe.filter.items}) in {@link BukkitConfigManager {@code config.yml}}.
     *
     * @param toAdd The material to add in the filter items.
     * @return True when successful, otherwise false if it's already in the list
     */
    public boolean addItemToFilter(Material toAdd) {
        return plugin.getConfigManager().getVanillaFilter().add(toAdd);
    }

    /**
     * Checks if the item (e.g., ItemsAdder item, Oraxen item) is in the filter items.
     *
     * @param itemStack The item to verify
     * @return Returns true if the item is in the filter.
     * @since 1.5
     */
    public boolean customBlockItem(ItemStack itemStack) {
        boolean itemsAdderItem = ItemUtils.isFilteredItemsAdderItem(itemStack, plugin.getConfigManager().getCustomFilter(CustomItemPluginType.ItemsAdder));
        boolean oraxenItem = ItemUtils.isFilteredOraxenItem(itemStack, plugin.getConfigManager().getCustomFilter(CustomItemPluginType.Oraxen));
        boolean minehutCosmeticItem = ItemUtils.isFilteredMinehutCosmeticItem(itemStack, plugin.getConfigManager().getCustomFilter(CustomItemPluginType.MinehutCosmetics));
        boolean nexoItem = ItemUtils.isFilteredNexoItem(itemStack, plugin.getConfigManager().getCustomFilter(CustomItemPluginType.Nexo));
        return itemsAdderItem || oraxenItem || minehutCosmeticItem || nexoItem;
    }

    /**
     * Checks if the multiplier is enabled in {@link BukkitConfigManager {@code config.yml}}, more specifically the {@code dupe.multiplier.enabled}.
     *
     * @return True if multiplier is enabled.
     * @deprecated Use {@link Multiplier#enabled() BukkitConfigManager.getEconomy().isPermissionEnabled()} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean multiplierEnabled() {
        return plugin.getConfigManager().getMultiplier().enabled();
    }

    /**
     * Retrieves the multiplier max for the player.
     *
     * @param player The player to check
     * @return The maximum multiplier the player can use
     */
    public int multiplierMax(Player player) {
        Obj2IntMap<String> maxValues = plugin.getConfigManager().getMultiplier().maxValues();
        if (maxValues == null) return 5;
        int maxAllowed = maxValues.getOrDefault("default", 2);
        for (Obj2IntMap.Entry<String> entry : maxValues.fastIterable()) {
            int value = entry.getIntValue();
            if (value == 0) return 0;
            String oldPermission = "dupeplus.times.max." + entry;
            String permission = "dupeplus.multiplier.max." + entry;
            if (player.hasPermission(oldPermission) || player.hasPermission(permission)) {
                maxAllowed = Math.max(maxAllowed, value);
            }
        }
        return maxAllowed;
    }

    /**
     * Retrieves the multiplier minimum from {@link BukkitConfigManager {@code config.yml}} in {@code dupe.multiplier.min}
     *
     * @return The Multiplier minimum
     * @deprecated Use {@link Multiplier#min() BukkitConfigManager.getMultiplier().min()}} instead.
     */
    @Deprecated(forRemoval = true)
    public int multiplierMin() {
        return plugin.getConfigManager().getMultiplier().min();
    }

    /**
     * Sends a message to the {@link Audience Player} when the multiplier the player used was above their maximum limit.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param multiplier      The multiplier used by the player
     */
    public void multiplierMaxMessage(Player player, Audience p, int multiplier) {
        Component a = UtilAPIs.translatable(plugin, "multiplier.max-message", "<prefix> <red>This is above maximum! Do something lower than <max></red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("max", String.valueOf(multiplierMax(player))), Placeholder.unparsed("min", String.valueOf(plugin.getConfigManager().getMultiplier().min())), Placeholder.unparsed("multiplier", String.valueOf(multiplier))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Sends a message to the {@link Audience Player} when the final value after the multiplier is invalid.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param multiplier      The multiplier used by the player
     */
    public void multiplierErrorMessage(Player player, Audience p, int multiplier) {
        Component a = UtilAPIs.translatable(plugin, "multiplier.error-message", "<prefix> <red>You cannot do that. Please use another value.</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("max", String.valueOf(multiplierMax(player))), Placeholder.unparsed("min", String.valueOf(plugin.getConfigManager().getMultiplier().min())), Placeholder.unparsed("multiplier", String.valueOf(multiplier))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Sends a message to the {@link Audience Player} when the multiplier the player used was below the minimum limit.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param multiplier      The multiplier used by the player
     */
    public void multiplierMinMessage(Player player, Audience p, int multiplier) {
        Component a = UtilAPIs.translatable(plugin, "multiplier.min-message", "<prefix> <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than <min></red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("max", String.valueOf(multiplierMax(player))), Placeholder.unparsed("min", String.valueOf(plugin.getConfigManager().getMultiplier().min())), Placeholder.unparsed("multiplier", String.valueOf(multiplier))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Checks if the player has the multiplier permission.
     *
     * @param player The player to check
     * @return True if the player has either permission {@code dupeplus.multiplier} or {@code dupeplus.times}
     */
    public boolean hasMultiplierPermission(Player player) {
        return player.hasPermission("dupeplus.multiplier") || player.hasPermission("dupeplus.times");
    }

    /**
     * Sends a specific message to the {@link Audience Player} when they have no permission to use the multiplier feature.
     * <p>
     * For general no permission, use {@link BukkitConfigyml#dupeNoPermission(Player, Audience)}
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     */
    public void multiplierNoPermission(@NonNull Player player, Audience p) {
        Component a = UtilAPIs.translatable(plugin, "multiplier.permission-message", "<prefix> <red>You are not allowed to use this command</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) {
            return;
        }
        p.sendMessage(a);
    }

    /**
     * Checks if the multiplier feature ({@code dupe.multiplier}) in {@link BukkitConfigManager {@code config.yml}} has the permission option turned on.
     *
     * @return True if the multiplier feature has the permission option turned on.
     * @deprecated Use {@link Multiplier#permission() BukkitConfigManager.getMultiplier().isPermissionEnabled()}} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean multiplierPermissionOption() {
        return plugin.getConfigManager().getMultiplier().permission();
    }

    /**
     * Stops the plugin from sending repeated dupe messages to the player when the player uses {@code /dupe [<multiplier>: int]}
     *
     * @return True if the message should only be sent once.
     * @deprecated Use {@link Multiplier#oneTimeMessage() BukkitConfigManager.getMultiplier().oneTimeMessage()}} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean oneTimeMessage() {
        return plugin.getConfigManager().getMultiplier().oneTimeMessage();
    }

    /**
     * Sends a message to the {@link Audience Player} when they have duplicated something.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     */
    public void dupeMessage(Player player, Audience p) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "message", "<prefix> <gray>Duped <white><item_name> x<new_item_count></gray>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("old_item_count", String.valueOf(item.getAmount())), Placeholder.unparsed("new_item_count", String.valueOf(Math.min(item.getAmount() * 2, 64)))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Sends multiple messages at once to the {@link Audience Player} when they have duplicated something.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param times      The amount of repeated messages to send to the player at once
     */
    public void dupeMessage(Player player, Audience p, int times) {
        if (times <= 0) return;
        ItemStack item = getDupedItem(player);
        List<Component> components = new ArrayList<>(times);
        Component message = UtilAPIs.translatable(plugin, "message", "<prefix> <gray>Duped <white><item_name> x<new_item_count></gray>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("old_item_count", String.valueOf(item.getAmount())), Placeholder.unparsed("new_item_count", String.valueOf(Math.min(item.getAmount() * 2, 64)))));
        if (PlainTextComponentSerializer.plainText().serialize(message).isEmpty()) return;
        for (int i = 0; i < times; i++) {
            components.add(message);
        }
        p.sendMessage(Component.join(JoinConfiguration.newlines(), components));
    }


    /**
     * Retrieves the prefix as a {@link Component}.
     *
     * @return {@link Component} of the prefix from the translator.<p>
     * This prefix may differ from one another based on their language in Minecraft if you have a separate language under {@code lang/}.
     * @since 1.0
     */
    public @NonNull Component getPrefix(Player player) {
        return UtilAPIs.translatable(plugin, "prefix", "<green>DupePlus</green> <dark_gray>|</dark_gray>", player);
    }

    /**
     * Sends a message to the {@link Audience Player} when they have no permission to do a certain action.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml).
     * @param p      Audience for the messages.
     * @since 1.0
     */
    public void dupeNoPermission(@NonNull Player player, Audience p) {
        Component a = UtilAPIs.translatable(plugin, "permission-message", "<prefix> <red>You are not allowed to use this command</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Sends a message to the {@link Audience Player} when the player attempts to duplicate but the item held is blocked from duplication.
     *
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml).
     * @param p      Audience for the messages.
     * @since 1.1
     */
    public void blockedMessage(@NonNull Player player, Audience p) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "filter.blocked-message", "<prefix> <red>The item is blocked from being duped!</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("item_count", String.valueOf(item.getAmount()))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * @return True if DupePlus should check for updates.
     * @since 1.2
     * @deprecated Use {@link UpdateChecker#enabled() BukkitConfigManager.getUpdateChecker().enabled()}
     */
    @Deprecated(forRemoval = true)
    public boolean isCheckUpdateAllowed() {
        return plugin.getConfigManager().getUpdateChecker().enabled();
    }

    /**
     * @return True if the plugin should check the item for the dupenotallowed tag or other dupe plugin's block tag.
     * @deprecated Use {@link BukkitConfigManager#getCustomNBTItem()}
     */
    @Deprecated(forRemoval = true)
    public boolean isCustomNBTAllowed() {
        return plugin.getConfigManager().getCustomNBTItem().enabled();
    }


    /**
     * Checks what the API is set in the updates section.
     *
     * @return True if the API is Spigot. By default, the API is set to Modrinth.
     * @since 1.2
     * @deprecated Use {@link UpdateChecker#apiType() BukkitConfigManager.getUpdateChecker().apiType().equals(UpdateAPIType.SpigotMC)} instead
     */
    @Deprecated(forRemoval = true)
    public boolean isSpigotMC() {
        return plugin.getConfigManager().getUpdateChecker().apiType() == UpdateAPIType.SpigotMC;
    }

    /**
     * @return True if it should notify the admin about the latest version.
     * @since 1.2
     * @deprecated Use {@link UpdateChecker#playerNotify() BukkitConfigManager.getUpdateChecker().playerNotify()} instead
     */
    @Deprecated(forRemoval = true)
    public boolean isPlayerNotifyAllowed() {
        return plugin.getConfigManager().getUpdateChecker().playerNotify();
    }

    /**
     * @return True if it should send a message to the console about the latest version.
     * @since 1.2
     * @deprecated Use {@link UpdateChecker#consoleNotify() BukkitConfigManager.getUpdateChecker().consoleNotify()} instead
     */
    @Deprecated(forRemoval = true)
    public boolean isConsoleNotifyAllowed() {
        return plugin.getConfigManager().getUpdateChecker().consoleNotify();
    }

    /**
     * The message sent to the player informing them of a new update.
     *
     * @param player     The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml).
     * @param newversion The new version of DupePlus.
     * @param p        Audience for the update message.
     */
    public void updatePlayerMessage(@NonNull Player player, @NonNull String newversion, @NonNull Audience p) {
        String link = plugin.getConfigManager().getUpdateChecker().apiType().equals(UpdateAPIType.SpigotMC) ? "https://www.spigotmc.org/resources/dupeplus.110621/" : "https://modrinth.com/plugin/dupeplus";
        Component message = UtilAPIs.translatable(plugin, "notify.notify-message", "<prefix> <white><green>DupePlus</green> is outdated, please update at: <blue><link></blue>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("link", Component.text(link).clickEvent(ClickEvent.openUrl(link))), Placeholder.parsed("currentversion", plugin.getVersion()), Placeholder.parsed("newversion", newversion)));
        if (PlainTextComponentSerializer.plainText().serialize(message).isBlank()) return;
        p.sendMessage(message);
    }


    /**
     * The message sent to the console informing them of a new update.
     *
     * @param newversion The new version of DupePlus.
     * @param message    The message for the update checker
     */
    public void updateConsoleMessage(String newversion, String message) {
        if (message == null || newversion == null) return;
        String link = plugin.getConfigManager().getUpdateChecker().apiType().equals(UpdateAPIType.SpigotMC) ? "https://www.spigotmc.org/resources/dupeplus.110621/" : "https://modrinth.com/plugin/dupeplus";
        String newMessage = message.replace("<link>", link).replace("<currentversion>", plugin.getVersion()).replace("<newversion>", newversion);
        if (newMessage.isEmpty()) return;
        plugin.getLogger().info(newMessage);
    }

    /**
     * Checks if the cooldown feature is enabled or not.
     *
     * @return True if cooldown is enabled
     * @deprecated Use {@link Cooldown#enabled() BukkitConfigManager.getCooldown().enabled()}
     */
    @Deprecated(forRemoval = true)
    public boolean isCooldownEnabled() {
        return plugin.getConfigManager().getCooldown().enabled();
    }

    /**
     * Retrieves the cooldown seconds ({@code dupe.cooldown.seconds} from {@link BukkitConfigManager {@code config.yml}})
     *
     * @return The cooldown seconds.
     * @deprecated Use {@link Cooldown#seconds() BukkitConfigManager.getCooldown().seconds()}
     */
    @Deprecated(forRemoval = true)
    public long cooldownSeconds() {
        return plugin.getConfigManager().getCooldown().seconds();
    }

    /**
     * Checks if the cooldown ({@code dupe.cooldown.permission} in {@link BukkitConfigManager {@code config.yml}}) permission option is enabled or not.
     *
     * @return True if the permission
     * @deprecated Use {@link Cooldown#seconds() BukkitConfigManager.getCooldown().seconds()}
     */
    @Deprecated(forRemoval = true)
    public boolean cooldownPermissionOption() {
        return plugin.getConfigManager().getCooldown().isPermissionEnabled();
    }

    /**
     * Checks if the player has the cooldown permission.
     *
     * @param player The player to check
     * @return True if the player has {@code dupeplus.cooldown}
     */
    public boolean hasCooldownPermission(Player player) {
        return !plugin.getConfigManager().getCooldown().isPermissionEnabled() || player.hasPermission("dupeplus.cooldown");
    }

    /**
     * The message sent to the player when the player is on cooldown.
     *
     * @param player   The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml).
     * @param timeleft The cooldown time left
     * @param p        Audience for the messages.
     */
    public void cooldownMessage(Audience p, Player player, String timeleft) {
        Component a = UtilAPIs.translatable(plugin, "cooldown.wait-message", "<prefix> <dark_gray>|</dark_gray> <red>Please wait <duration>s.</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("duration", timeleft)));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }


    /**
     * Checks if the item contains the dupenotallowed tag.
     * <p>
     * Custom NBT Item section {@code dupe.custom-nbt-item} in {@code config.yml} must be enabled ({@code dupe.custom-nbt-item.enabled})
     *
     * @param item The item checked if it contains the dupenotallowed tag.
     * @return True if it does contain the dupenotallowed NBT tag.
     */
    public boolean customNBTItem(ItemStack item) {
        if (plugin.getConfigManager().getCustomNBTItem().tag().isBlank()) return false;
        return NBT.get(item, nbt -> {
            return nbt.getBoolean(plugin.getConfigManager().getCustomNBTItem().tag());
        });
    }


    /**
     * Sends a message that the item is blocked as it contains the Custom NBT
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     */
    public void customNBTItemMessage(Player player, Audience p) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "custom-nbt-item.blocked-message", "<prefix> <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("item_count", String.valueOf(item.getAmount()))));
        if (PlainTextComponentSerializer.plainText().serialize(a).isEmpty()) return;
        p.sendMessage(a);
    }

    /**
     * Checks if the lore feature is enabled or not.
     * @return True if the lore feature {@code dupe.lore} is enabled.
     * @deprecated Use {@link Lore#enabled() BukkitConfigManager.getLore().enabled()}
     */
    @Deprecated(forRemoval = true)
    public boolean isLoreEnabled() {
        return plugin.getConfigManager().getLore().enabled();
    }

    /**
     * Checks if the lore feature's permission option is enabled or not.
     * @return True if the lore feature {@code dupe.lore} permission option is enabled.
     * @deprecated Use {@link Lore#enabled() BukkitConfigManager.getLore().isPermissionEnabled()}
     */
    @Deprecated(forRemoval = true)
    public boolean lorePermissionOption() {
        return plugin.getConfigManager().getLore().isPermissionEnabled();
    }

    /**
     * Checks if the player has the lore permission
     * @param player The player you want to check.
     * @return True if the player has the permission and {@link Lore#enabled() BukkitConfigManager.getLore().isPermissionEnabled()} is enabled.
     */
    public boolean lorePermission(Player player) {
        return !plugin.getConfigManager().getLore().isPermissionEnabled() || player.hasPermission("dupeplus.lore");
    }

    /**
     * Checks if the shulker box option in ({@code dupe.filter} in {@link BukkitConfigManager {@code config.yml}}) is enabled or not.
     * @return True if the shulker box option {@code dupe.filter.shulkerbox} is enabled.
     * @deprecated Use {@link Filter#shulkerBox() BukkitConfigManager.getFilter().shulkerBox()}
     */
    @Deprecated(forRemoval = true)
    public boolean isShulkerBoxEnabled() {
        return plugin.getConfigManager().getFilter().shulkerBox();
    }

    /**
     * Checks if the bundles option in ({@code dupe.filter} in {@link BukkitConfigManager {@code config.yml}}) is enabled or not.
     * @return True if the bundles option {@code dupe.filter.bundle} is enabled.
     * @deprecated Use {@link Filter#bundle() BukkitConfigManager.getFilter().bundle()}
     */
    @Deprecated(forRemoval = true)
    public boolean isBundlesEnabled() {
        return plugin.getConfigManager().getFilter().bundle();
    }


    /**
     * Checks if the shulker box item contains any filtered item.
     * @param item The shulker box item to check if it contains any filtered items.
     * @return True if it contains a filtered item or more.
     */
    public boolean shulkerBoxFilterCheck(@NonNull ItemStack item) {
        if (item.getItemMeta() instanceof BlockStateMeta) {
            BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
            if (meta.getBlockState() instanceof ShulkerBox) {
                ShulkerBox shulker = (ShulkerBox) meta.getBlockState();
                Inventory shulkerInventory = shulker.getInventory();
                for (ItemStack i : shulkerInventory.getStorageContents()) {
                    if (i == null || UtilAPIs.isAir(i.getType())) continue;
                    if (isBundleItem(i.getType())) {
                        if (bundleBlacklist(i)) {
                            return true;
                        }
                    }
                    if (plugin.getConfigManager().getVanillaFilter().contains(i.getType())) {
                        return true;
                    }
                    if (customBlockItem(i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the bundle item contains any filtered item.
     * @param item The bundle item to check if it contains any filtered items.
     * @return True if it contains a filtered item or more.
     */
    @SuppressWarnings("UnstableApiUsage")
    public boolean bundleBlacklist(ItemStack item) {
        if (item == null || !isBundleItem(item.getType())) return false;
        if (!plugin.isBundleSupported()) return false;
        try {
            if (item.getItemMeta() instanceof BundleMeta) {
                BundleMeta meta = (org.bukkit.inventory.meta.BundleMeta) item.getItemMeta();
                List<ItemStack> bundleItems = meta.getItems();
                for (ItemStack i : bundleItems) {
                    if (i == null || UtilAPIs.isAir(i.getType())) continue;
                    if (isBundleItem(i.getType()) && bundleBlacklist(i)) return true;
                    if (plugin.getConfigManager().getVanillaFilter().contains(i.getType())) return true;
                    if (customBlockItem(i)) return true;
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }

    /**
     * @param itemStack The item to add the lore to.
     * @return A new {@link ItemStack} with the lore.
     */
    public ItemStack addLore(ItemStack itemStack, Player player) {

        ItemStack newItem = itemStack.clone();
        ItemMeta meta = newItem.getItemMeta();

        if (meta == null) return newItem;

        LoreMode loreMode = plugin.getConfigManager().getLore().mode(); // Set/Append
        String textLore = plugin.getConfigManager().getLore().text(); // The lore in the config.yml

        if (textLore == null || textLore.isBlank()) return newItem;
        textLore = LegacyComponentSerializer.legacySection().serialize(UtilAPIs.format(plugin, textLore, player));
        List<String> newLore = Arrays.asList(textLore.split("\n", -1));

        if (loreMode.equals(LoreMode.SET)) {
            if (!meta.hasLore() || !Objects.equals(meta.getLore(), newLore)) meta.setLore(newLore);
        } else {
            List<String> existingLore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            if (existingLore == null) {
                newItem.setItemMeta(meta);
                return newItem;
            } else {
                for (String line : newLore) {
                    if (existingLore.contains(line)) continue;
                    existingLore.add(LegacyComponentSerializer.legacySection().serialize(UtilAPIs.format(plugin, line, player)));
                }
                meta.setLore(existingLore);
            }
        }

        newItem.setItemMeta(meta);
        return newItem;
    }


    /**
     * Checks if the custom command feature is enabled or not.
     * @return True if the custom command feature is enabled.
     * @deprecated Use {@link CustomCommand#enabled() BukkitConfigManager.getCustomCommand().enabled()}
     */
    @Deprecated(forRemoval = true)
    public boolean isCustomCommandEnabled() {
        return plugin.getConfigManager().getCustomCommand().enabled();
    }

    /**
     * Retrieves the custom command's name.
     * <p>
     * <h1>Example</h1>
     * If the custom-command.name is set to {@code /d}, then the main command will be renamed from {@code /dupe} to {@code /d}.
     * @return The custom command's name.
     * @deprecated Use {@link CustomCommand#name() BukkitConfigManager.getCustomCommand().name()}
     */
    @Deprecated(forRemoval = true)
    public String customCommandName() {
        return plugin.getConfigManager().getCustomCommand().name();
    }

    /**
     * Alternate ways of performing the main dupe command.
     * @return The aliases for {@code /dupe}
     */
    @Deprecated(forRemoval = true)
    public Set<String> dupeAliases() {
        return plugin.getConfigManager().getAliases();
    }

    /**
     * Checks if the worlds feature is enabled or not.
     * @return True if the worlds feature is enabled.
     * @deprecated Use {@link Worlds#enabled() BukkitConfigManager.getWorlds().enabled()} instead
     */
    @Deprecated(forRemoval = true)
    public boolean isWorldsEnabled() {
        return plugin.getConfigManager().getWorlds().enabled();
    }

    /**
     * Retrieves the worlds filter mode (blacklist/whitelist).
     * @return The worlds mode as blacklist or whitelist.
     * @deprecated Use {@link Worlds#modeType() BukkitConfigManager.getWorlds().modeType()} instead
     */
    @Deprecated(forRemoval = true)
    public String getWorldsMode() {
        return plugin.getConfigManager().getWorlds().modeType().name();
    }

    /**
     * Retrieves the worlds filter list
     * @return All the filtered worlds.
     * @deprecated Use {@link Worlds#worlds() BukkitConfigManager.getWorlds().worlds()} instead
     */
    @Deprecated(forRemoval = true)
    public Set<String> getWorlds() {
        return plugin.getConfigManager().getWorlds().worlds();
    }

    /**
     * Sends a message to the player if the world they are currently in is filtered.
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     */
    public void worldBlockedMessage(Player player, Audience p) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "worlds.blocked-message", "<prefix> <red>The world is banned from using /dupe!</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("item_count", String.valueOf(item.getAmount())), Placeholder.unparsed("world", player.getWorld().getName())));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
    }

    /**
     * Deducts money from the player.
     * @param player The player you want to deduct money from.
     */
    public void deductCurrency(Player player) {
        if (plugin.getEconomy() == null) return;
        EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, plugin.getConfigManager().getEconomy().price());
        response.transactionSuccess();
    }

    /**
     * Deducts money from the player.
     * @param player The player you want to deduct money from.
     */
    public void deductCurrencyMultiplier(Player player, int times) {
        if (plugin.getEconomy() == null) return;
        EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, plugin.getConfigManager().getEconomy().price() * times);
        response.transactionSuccess();
    }

    /**
     * Sends a message to the player when they don't have enough currency to use the main dupe command.
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param price The price of what they wanted, but couldn't afford.
     */
    public void notEnoughCurrencyMessage(Player player, Audience p, double price) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "economy.not-enough-message", "<prefix> <red>You don't have enough currency to use /dupe!</red>", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("price", String.valueOf(price)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("item_count", String.valueOf(item.getAmount()))));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
    }

    /**
     * Checks if the economy feature is enabled or disabled in {@link BukkitConfigManager {@code config.yml}})
     * @return True if the economy feature is enabled
     */
    public boolean isEconomyEnabled() {
        return plugin.getConfigManager().getEconomy().enabled();
    }

    /**
     * Retrieves the economy feature's fixed price on the main dupe command in {@link BukkitConfigManager {@code config.yml}})
     * @return The economy price {@code dupe.economy.price}.
     * @deprecated Use {@link Economy#price() BukkitConfigManager.getEconomy().price()} instead
     */
    @Deprecated(forRemoval = true)
    public double economyPrice() {
        return plugin.getConfigManager().getEconomy().price();
    }

    /**
     * @param player The player to check
     * @param price The price to check if the player can afford or not.
     * @return True if the player can afford the price.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasEnoughCurrency(Player player, double price) {
        return plugin.getEconomy() != null && plugin.getEconomy().has(player, price);
    }

    /**
     * Checks if the economy feature's permission option is enabled or not.
     * @return True if the economy feature permission option is enabled.
     * @deprecated Use {@link Economy#isPermissionEnabled() BukkitConfigManager.getEconomy().isPermissionEnabled()} instead
     */
    @Deprecated(forRemoval = true)
    public boolean economyPermissionOption() {
        return plugin.getConfigManager().getEconomy().isPermissionEnabled();
    }

    /**
     * Checks if the charge-per-dupe option in the economy feature is turned on or not.
     * @return True if the charge-per-dupe option is turned on in the economy feature
     * @deprecated Use {@link Economy#chargePerDupe() BukkitConfigManager.getEconomy().chargedPerDupe()} instead
     */
    @Deprecated(forRemoval = true)
    public boolean chargePerDupeEnabled() {
        return plugin.getConfigManager().getEconomy().chargePerDupe();
    }

    /**
     * Checks if the player has the bypass permission or not.
     * @param player The player to check
     * @return True if the player has the bypass economy permission.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasBypassEconomyPermission(Player player) {
        return plugin.getConfigManager().getEconomy().isPermissionEnabled() && player.hasPermission("dupeplus.bypasseconomy");
    }

    /**
     * @param player The player, which is to determine the locale and PlaceholderAPI (if installed and used in the lang/en_us.yml)
     * @param p      Audience for the messages
     * @param price  The price the player paid
     */
    public void successfulMessage(Player player, Audience p, double price) {
        ItemStack item = getDupedItem(player);
        Component a = UtilAPIs.translatable(plugin, "economy.successful-message", "", player, TagResolver.resolver(Placeholder.component("prefix", getPrefix(player)), Placeholder.unparsed("price", String.valueOf(price)), Placeholder.component("item_name", itemStackName(item)), Placeholder.unparsed("item_type", item.getType().name()), Placeholder.unparsed("item_count", String.valueOf(item.getAmount()))));
        if (!(PlainTextComponentSerializer.plainText().serialize(a).isEmpty())) p.sendMessage(a);
    }
}

