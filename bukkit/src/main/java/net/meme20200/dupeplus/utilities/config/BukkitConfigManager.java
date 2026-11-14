package net.meme20200.dupeplus.utilities.config;

import net.kyori.adventure.key.Key;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.meme20200.dupeplus.utilities.types.CustomItemPluginType;
import net.meme20200.dupeplus.utilities.types.LoreMode;
import net.meme20200.dupeplus.utilities.types.ModeType;
import net.meme20200.dupeplus.utilities.types.UpdateAPIType;
import net.meme20200.dupeplus.utilities.types.config.*;
import net.meme20200.shared.Obj2IntMap;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static net.meme20200.dupeplus.utilities.UtilAPIs.stringToCategory;

/**
 * Better way of working with the configuration
 */
public final class BukkitConfigManager implements ConfigData {
    /**
     * The plugin
     */
    private final BukkitDupePlus plugin;

    /**
     * The addons section of the config
     */
    private Addons addons;

    /**
     * If permission is enabled or not.
     */
    private boolean permission;
    /**
     * The custom commands section of the config
     */
    private CustomCommand customCommand;
    /**
     * Alternative commands to /dupe {@code dupe.aliases}
     */
    private Set<String> aliases;
    /**
     * The worlds section of the config
     */
    private Worlds worlds;
    private CustomNBTItem customNBTItem;
    /**
     * The dupe on option. Either the main hand or the offhand.
     */
    private String dupeOn;
    /**
     * The message sent to the console
     */
    private String consoleMessage;

    /**
     * The filter section of the config
     * <p>
     * The object does not contain the filter lists. You must either use {@link vanillaFilter} if you want the vanilla filter list, or {@link customFilter} if you want the custom filter list.
     */
    private Filter filter;
    /**
     * The cooldown section of the config
     */
    private Cooldown cooldown;
    /**
     * The multiplier section of the config
     */
    private Multiplier multiplier;
    /**
     * The lore section of the config
     */
    private Lore lore;
    /**
     * The sound feedback section of the config
     */
    private SoundFeedback soundFeedback;
    /**
     * The economy section of the config
     */
    private Economy economy;
    /**
     * The update checker section of the config
     */
    private UpdateChecker updateChecker;

    /**
     * The admin shorthand commands option. If turned on, commands like these: {@code /dupeblock}, {@code /dupeunblock}, {@code /dupefilter} will appear in the server.
     */
    private boolean adminShorthandCommands;


    /**
     * Each custom plugin (ItemsAdder, Oraxen), if it's in the filter list, will have its own {@code Map<String, Set<String>>}.
     * <p>
     * {@code Map<CustomPluginType, Map<Namespace, Set<ID>>}
     * <p>
     * Without the custom plugin type: {@code Map<Namespace, Set<ID>>}
     *
     * @since 1.5
     */
    private Map<CustomItemPluginType, Map<String, Set<String>>> customFilter = new EnumMap<>(CustomItemPluginType.class);
    /**
     * Used for the minecraft namespace
     * <pre>{@code
     * dupe:
     *   ...
     *   filter:
     *      ...
     *      items:
     *       ...
     *       - minecraft:id
     *  }</pre>
     *
     * @since 1.5
     */
    private Set<Material> vanillaFilter = new HashSet<>();

    /**
     * Must use {@link loadConfig()}
     *
     * @param plugin The Main class
     */
    public BukkitConfigManager(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads the configuration from config.yml and stores it all into objects
     */
    @SuppressWarnings({"PatternValidation"})
    public void loadConfig() {
        checkConfigVersion();

        plugin.reloadConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        List<String> filterItems = config.getStringList("dupe.filter.items");
        if (!filterItems.isEmpty()) {
            vanillaFilter = parseVanillaFilter(filterItems);
            customFilter = parseCustomFilter(filterItems);
        } else {
            vanillaFilter = new HashSet<>();
            customFilter = new HashMap<>();
        }
        adminShorthandCommands = config.getBoolean("dupeplus.shorthand-commands", false);

        boolean isPlaceholderAPIEnabled = config.getBoolean("integrations.placeholderapi", true);
        boolean isPlaceholderAPIInstalled = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        boolean isVaultEnabled = config.getBoolean("integrations.vault", true);
        boolean isVaultInstalled = plugin.getEconomy() != null;
        addons = new Addons(isPlaceholderAPIEnabled, isPlaceholderAPIInstalled, isVaultEnabled, isVaultInstalled);

        permission = config.getBoolean("dupe.permission", true);

        customCommand = new CustomCommand(config.getBoolean("dupe.custom-command.enabled", true), UtilAPIs.getWhitespacePattern().matcher(config.getString("dupe.custom-command.name", "dupe")).replaceAll(""));

        aliases = noWhiteSpaceSet(config.getStringList("dupe.aliases"));

        worlds = new Worlds(config.getBoolean("dupe.worlds.enabled", true), ModeType.match(config.getString("dupe.worlds.mode", "blacklist").toLowerCase(Locale.ROOT)), new HashSet<>(config.getStringList("dupe.worlds.worlds")));

        customNBTItem = new CustomNBTItem(config.getBoolean("dupe.custom-nbt-item.enabled", true), config.getString("dupe.custom-nbt-item.tag", "dupenotallowed"));

        dupeOn = config.getString("dupe.dupe-on");

        consoleMessage = config.getString("dupe.console-message");

        filter = new Filter(config.getBoolean("dupe.filter.enabled", false), ModeType.match(config.getString("dupe.filter.mode", "blacklist")), config.getBoolean("dupe.filter.shulkerbox", false), config.getBoolean("dupe.filter.bundle", false));

        cooldown = new Cooldown(config.getBoolean("dupe.cooldown.enabled", false), config.getLong("dupe.cooldown.seconds", 3), config.getBoolean("dupe.cooldown.permission", true));

        lore = new Lore(config.getBoolean("dupe.lore.enabled", false), config.getString("dupe.lore.text", "<dark_red>*</dark_red> <red>Duplicated Item</red>"), LoreMode.match(config.getString("dupe.lore.mode", "blacklist").toLowerCase(Locale.ROOT)), config.getBoolean("dupe.lore.permission", false));

        soundFeedback = new SoundFeedback(config.getBoolean("dupe.sound-feedback.enabled", false), stringToCategory(config.getString("dupe.sound-feedback.category", "master")), Key.key(config.getString("dupe.sound-feedback.sound", "minecraft:block.amethyst_block.chime")), (float) config.getDouble("dupe.sound-feedback.pitch", 1), (float) config.getDouble("dupe.sound-feedback.volume", 1), config.getBoolean("dupe.sound-feedback.permission", false));

        economy = new Economy(config.getBoolean("dupe.economy.enabled", false) && isVaultInstalled, config.getBoolean("dupe.economy.charge-per-dupe", true), config.getDouble("dupe.economy.price", 25), config.getBoolean("dupe.economy.permission", false));

        updateChecker = new UpdateChecker(config.getBoolean("updates.enabled", true), UpdateAPIType.match(config.getString("updates.api", "Modrinth")), config.getBoolean("updates.notify.player-notify", true), config.getBoolean("updates.notify.console-notify", true), config.getString("updates.notify.console-notify-message", "DupePlus | Update DupePlus at <link>"));

        Obj2IntMap<String> maxValues = UtilAPIs.createObj2IntMap();
        ConfigurationSection maxValuesSection = config.getConfigurationSection("dupe.multiplier.max");
        if (maxValuesSection != null) {
            Set<String> keys = maxValuesSection.getKeys(false);
            for (String key : keys) {
                maxValues.put(key, maxValuesSection.getInt(key, 1));
            }
        }
        multiplier = new Multiplier(config.getBoolean("dupe.multiplier.enabled", false), config.getInt("dupe.multiplier.min", 0), config.getBoolean("dupe.multiplier.one-time-message", false), config.getBoolean("dupe.multiplier.permission", false), maxValues);
    }

    @SuppressWarnings("deprecation")
    public void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        try {
            config.options().parseComments(true);
        } catch (NoSuchMethodError e) {
            config.options().copyHeader(true);
        }
        List<String> filterItems = parseFilterIntoList(getVanillaFilter(), getCustomFilter());
        config.set("dupe.filter.items", filterItems);
        config.set("dupe.permission", permission);
        // Custom Command
        config.set("dupe.custom-command.enabled", customCommand.enabled());
        config.set("dupe.custom-command.name", customCommand.name());
        // Alias
        config.set("dupe.aliases", new ArrayList<>(aliases));
        // Worlds
        config.set("dupe.worlds.enabled", worlds.enabled());
        config.set("dupe.worlds.mode", worlds.modeType().name());
        config.set("dupe.worlds.worlds", new ArrayList<>(worlds.worlds()));
        // Custom NBT Item
        config.set("dupe.custom-nbt-item.enabled", customNBTItem.enabled());
        config.set("dupe.custom-nbt-item.tag", customNBTItem.tag());
        // Dupe On
        config.set("dupe.dupe-on", dupeOn);
        // Console Message
        config.set("dupe.console-message", consoleMessage);
        // Filter
        config.set("dupe.filter.enabled", filter.enabled());
        config.set("dupe.filter.mode", filter.modeType().name());
        config.set("dupe.filter.shulkerbox", filter.shulkerBox());
        config.set("dupe.filter.bundle", filter.bundle());
        // Cooldown
        config.set("dupe.cooldown.enabled", cooldown.enabled());
        config.set("dupe.cooldown.seconds", cooldown.seconds());
        config.set("dupe.cooldown.permission", cooldown.isPermissionEnabled());
        // Lore
        config.set("dupe.lore.enabled", lore.enabled());
        config.set("dupe.lore.text", lore.text());
        config.set("dupe.lore.mode", lore.mode().name());
        config.set("dupe.lore.permission", lore.isPermissionEnabled());
        // Sound Feedback
        config.set("dupe.sound-feedback.enabled", soundFeedback.enabled());
        config.set("dupe.sound-feedback.category", soundFeedback.category().name());
        config.set("dupe.sound-feedback.sound", soundFeedback.sound().asString());
        config.set("dupe.sound-feedback.pitch", soundFeedback.pitch());
        config.set("dupe.sound-feedback.volume", soundFeedback.volume());
        config.set("dupe.sound-feedback.permission", soundFeedback.isPermissionEnabled());
        // Economy
        config.set("dupe.economy.enabled", economy.enabled());
        config.set("dupe.economy.charge-per-dupe", economy.chargePerDupe());
        config.set("dupe.economy.price", economy.price());
        config.set("dupe.economy.permission", economy.isPermissionEnabled());
        // Update Checker
        config.set("updates.enabled", updateChecker.enabled());
        config.set("updates.api", updateChecker.apiType().name());
        config.set("updates.notify.player-notify", updateChecker.playerNotify());
        config.set("updates.notify.console-notify", updateChecker.consoleNotify());
        // Addons
        config.set("integrations.placeholderapi", addons.isPlaceholderAPIEnabled());
        config.set("integrations.vault", addons.isVaultEnabled());
        // DupePlus command (/dupeplus)
        config.set("dupeplus.shorthand-commands", adminShorthandCommands);

        plugin.saveConfig();
    }

    /**
     * @return True if the custom command has changed. Otherwise, false.
     */
    public boolean reloadConfig() {
        boolean customCommandEnabled = getCustomCommand().enabled();
        String oldCustomCommandName = getCustomCommand().name();
        boolean registered = adminShorthandCommands;
        // Unregister commands
        loadConfig();
        if (adminShorthandCommands && !registered) {
            // Unregistered but admin shorthand commands is now turned on
            plugin.dupeBlockCommand().registerCommand();
            plugin.dupeFilterCommand().registerCommand();
            plugin.dupeUnblockCommand().registerCommand();
        } else if (!adminShorthandCommands && registered) {
            // Registered but admin shorthand commands is now turned off
            plugin.dupeBlockCommand().unregisterCommand();
            plugin.dupeFilterCommand().unregisterCommand();
            plugin.dupeUnblockCommand().unregisterCommand();
        }
        if (getCustomCommand().enabled() && (!oldCustomCommandName.equals(getCustomCommand().name()) || !customCommandEnabled)) {
            plugin.getBukkitDupeCommand().reloadCommand(oldCustomCommandName);
            return true;
        }
        return false;
    }

    /**
     * @param blacklistItemsSTR The list from {@code dupe.filter.items}.
     * @return The generated, organized filter, which is separated by plugin.
     * @since 1.5
     */
    public @NonNull Map<CustomItemPluginType, Map<String, Set<String>>> parseCustomFilter(List<String> blacklistItemsSTR) {
        Map<CustomItemPluginType, Map<String, Set<String>>> compiled = new EnumMap<>(CustomItemPluginType.class);
        if (blacklistItemsSTR == null) return compiled;
        for (String filter : blacklistItemsSTR) {
            if (filter == null) continue;
            if (filter.startsWith("itemsadder:") || filter.startsWith("minehutcosmetics:")) {
                CustomItemPluginType pluginType = filter.startsWith("itemsadder:") ? CustomItemPluginType.ItemsAdder : CustomItemPluginType.MinehutCosmetics;
                String[] parts = filter.split(":", 4);
                if (parts.length < 2) continue;
                String namespace = "";
                String id;
                if (parts.length == 2) {
                    id = UtilAPIs.getWhitespacePattern().matcher(parts[1]).replaceAll("");
                } else {
                    namespace = UtilAPIs.getWhitespacePattern().matcher(parts[1]).replaceAll("");
                    id = UtilAPIs.getWhitespacePattern().matcher(parts[2]).replaceAll("");
                }
                if (namespace.isEmpty() && id.equals("*")) {
                    namespace = "*";
                }
                compiled.computeIfAbsent(pluginType, k -> new HashMap<>()).computeIfAbsent(namespace, k -> new HashSet<>()).add(id);
            } else if (filter.startsWith("oraxen:") || filter.startsWith("nexo:")) {
                CustomItemPluginType pluginType = filter.startsWith("oraxen:") ? CustomItemPluginType.Oraxen : CustomItemPluginType.Nexo;
                String[] parts = filter.split(":", 2);
                if (parts.length < 2) continue;
                String id = UtilAPIs.getWhitespacePattern().matcher(parts[1]).replaceAll("");
                compiled.computeIfAbsent(pluginType, k -> new HashMap<>()).computeIfAbsent(pluginType.key(), k -> new HashSet<>()).add(id);
            }
        }
        return compiled;
    }

    private List<String> parseFilterIntoList(Set<Material> vanillaFilter, Map<CustomItemPluginType, Map<String, Set<String>>> customFilter) {
        List<String> list = new ArrayList<>();
        if (vanillaFilter != null && !vanillaFilter.isEmpty()) {
            // minecraft:id
            for (Material material : vanillaFilter) {
                list.add(material.getKey().toString());
            }
        }

        if (customFilter == null || customFilter.isEmpty()) return list;
        // itemsadder:namespace:id
        // oraxen:id
        // nexo:id
        // minehutcosmetics:namespace:id
        for (Map.Entry<CustomItemPluginType, Map<String, Set<String>>> entry : customFilter.entrySet()) {
            // The custom plugin type (e.g., ItemsAdder, Nexo)
            // Not Null
            CustomItemPluginType pluginType = entry.getKey();
            String key = pluginType.name().toLowerCase(Locale.ROOT);
            // Namespace, IDs
            Map<String, Set<String>> namespacesMap = entry.getValue();
            if (namespacesMap == null) continue;

            if (pluginType.equals(CustomItemPluginType.Oraxen) || pluginType.equals(CustomItemPluginType.Nexo)) {
                Set<String> ids = namespacesMap.values().iterator().next();
                for (String id : ids) {
                    if (id == null) continue;
                    list.add(key + ":" + id);
                }
            } else {
                // There are namespaces in ItemsAdder & Minehut Cosmetics
                for (Map.Entry<String, Set<String>> namespaceID : namespacesMap.entrySet()) {
                    // Namespace
                    String namespace = namespaceID.getKey();
                    // IDs
                    Set<String> ids = namespaceID.getValue();
                    if (namespace == null || ids == null || ids.isEmpty()) continue;

                    for (String id : ids) {
                        if (id == null) continue;
                        if (namespace.isEmpty()) {
                            // Namespace is empty, which means it was initially "key:id"
                            list.add(key + ":" + id);
                            continue;
                        }
                        // key:namespace:id
                        list.add(key + ":" + namespace + ":" + id);
                    }
                }
            }
        }
        return list;
    }

    /**
     * @param blacklistItemsSTR The list from {@code config.yml} {@code dupe.filter.items}
     * @return A list (set) of materials in the filter.
     * @since 1.5
     */
    private Set<Material> parseVanillaFilter(List<String> blacklistItemsSTR) {
        Set<Material> compiled = new HashSet<>();
        if (blacklistItemsSTR == null) return compiled;
        for (String filter : blacklistItemsSTR) {
            if (filter == null || !filter.startsWith("minecraft:")) continue;
            Material material = Material.matchMaterial(filter);
            if (material != null) compiled.add(material);
        }
        return compiled;
    }

    /**
     * Checks if the config.yml is old or not. If it is an old version, then it will rename the old config to old.config.yml.
     */
    private void checkConfigVersion() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        List<String> versions = List.of("1.0", "1.1", "1.2", "1.3", "1.4", "1.4.2", "1.4.3");

        // Check if config-version is 1.0, 1.1, 1.2, 1.3, 1.4, 1.4.2, 1.4.3
        if (versions.contains(config.getString("config-version", "1.5"))) {
            // Rename old config
            File oldConfigFile = new File(plugin.getDataFolder(), "old.config.yml");
            if (oldConfigFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                oldConfigFile.delete();
            }
            if (configFile.renameTo(oldConfigFile)) {
                plugin.getLogger().info("Old config renamed to old.config.yml");
            } else {
                plugin.getLogger().info("Failed to rename old config to old.config.yml");
            }

            // Generate or copy new config
            plugin.saveResource("config.yml", true); // This will save the default config.yml
        }
    }

    @Override
    public Addons getAddons() {
        return addons;
    }

    @Override
    public boolean permission() {
        return permission;
    }

    @Override
    public void permission(boolean permission) {
        this.permission = permission;
    }

    @Override
    public CustomCommand getCustomCommand() {
        return customCommand;
    }

    @Override
    public Set<String> getAliases() {
        return aliases;
    }

    @Override
    public Worlds getWorlds() {
        return worlds;
    }

    @Override
    public CustomNBTItem getCustomNBTItem() {
        return customNBTItem;
    }

    @Override
    public String getDupeOn() {
        return dupeOn;
    }

    @Override
    public String getConsoleMessage() {
        return consoleMessage;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public Cooldown getCooldown() {
        return cooldown;
    }

    @Override
    public Multiplier getMultiplier() {
        return multiplier;
    }

    @Override
    public Lore getLore() {
        return lore;
    }

    @Override
    public SoundFeedback getSoundFeedback() {
        return soundFeedback;
    }

    @Override
    public Economy getEconomy() {
        return economy;
    }

    @Override
    public Map<CustomItemPluginType, Map<String, Set<String>>> getCustomFilter() {
        return customFilter;
    }

    @Override
    public Map<String, Set<String>> getCustomFilter(CustomItemPluginType customItemPluginType) {
        return customFilter.get(customItemPluginType);
    }

    @Override
    public Set<Material> getVanillaFilter() {
        return vanillaFilter;
    }

    @Override
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    @Override
    public boolean adminShorthandCommands() {
        return adminShorthandCommands;
    }

    @Override
    public void adminShorthandCommands(boolean enabled) {
        this.adminShorthandCommands = enabled;
    }

    private Set<String> noWhiteSpaceSet(List<String> inputSet) {
        if (inputSet == null || inputSet.isEmpty()) return new HashSet<>();
        return inputSet.stream()
                .map(s -> UtilAPIs.getWhitespacePattern().matcher(s).replaceAll(""))
                .collect(Collectors.toSet());
    }
}
