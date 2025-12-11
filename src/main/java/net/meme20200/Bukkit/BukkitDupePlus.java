package net.meme20200.Bukkit;

import net.meme20200.Bukkit.Commands.BukkitDupeCommand;
import net.meme20200.Bukkit.Commands.BukkitDupePlusCommand;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import net.meme20200.Bukkit.Utilities.UpdateChecker;
import net.meme20200.Bukkit.events.NotifyJoinPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.bukkit.internal.CraftBukkitReflection;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.parser.standard.IntegerParser;
import org.incendo.cloud.setting.ManagerSetting;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static net.meme20200.Bukkit.Utilities.BukkitConfigyml.*;
import static org.incendo.cloud.parser.standard.StringParser.greedyStringParser;

public class BukkitDupePlus extends JavaPlugin {
    private static BukkitDupePlus plugin;
    private LegacyPaperCommandManager<CommandSender> manager;
    private BukkitAudiences adventure;
    public static String version = "1.4.1";
    public static boolean isItemsadderInstalled = false;

    public static boolean isPlaceholderAPIInstalled = false;

    private static BukkitDupeCommand bukkitDupeCommand;



    public @NotNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {


        this.adventure = BukkitAudiences.create(plugin);
        version = plugin.getDescription().getVersion();
        checkConfigVersion();
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        isItemsadderInstalled = (getServer().getPluginManager().getPlugin("ItemsAdder") != null && config.getBoolean("integrations.itemsadder", true));
        isPlaceholderAPIInstalled = (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && config.getBoolean("integrations.placeholderapi", true));

        manager = new LegacyPaperCommandManager<>(
                this,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        ) {{
            if (CraftBukkitReflection.classExists("com.mojang.brigadier.tree.CommandNode")) {
                registerCapability(CloudBukkitCapabilities.BRIGADIER);
            }
        }};

        manager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true);


        if (manager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {}
        else if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            // Use Paper async completions API (see Javadoc for why we don't use this with Brigadier)
            manager.registerAsynchronousCompletions();
        }


        Metrics metrics = new Metrics(this, 18772);
        metrics.addCustomChart(new SimplePie("configversion", () -> version));
        if (isCheckUpdateAllowed()) {
            new UpdateChecker(this, isSpigotMC()).getVersion(newversion -> {
                if (!(BukkitDupePlus.version.equals(newversion))) {
                    if (isPlayerNotifyAllowed()) {
                        Bukkit.getServer().getPluginManager().registerEvents(new NotifyJoinPlayer(newversion), this);
                        return;
                    }
                    if (isPlayerNotifyAllowed() || isConsoleNotifyAllowed()) {
                        if (isConsoleNotifyAllowed()) {
                            updateConsoleMessage(this, newversion);
                        }
                    }
                }
            });
        }

        bukkitDupeCommand = new BukkitDupeCommand();
        bukkitDupeCommand.registerCommand();


        new BukkitDupePlusCommand();
    }

    public void unregisterCommand(String commandName) {
        getPlugin().getCommandManager().deleteRootCommand(commandName);
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    private void checkConfigVersion() {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        List<String> versions = new ArrayList<>(List.of("1.0", "1.1", "1.2", "1.3"));

        // Check if config-version is 1.0, 1.1, 1.2, 1.3
        if (versions.contains(config.getString("config-version", "1.4"))) {
            // Rename old config
            File oldConfigFile = new File(getDataFolder(), "old.config.yml");
            if (oldConfigFile.exists()) {
                oldConfigFile.delete();
            }
            if (configFile.renameTo(oldConfigFile)) {
                getLogger().info("Old config renamed to old.config.yml");
            } else {
                getLogger().info("Failed to rename old config to old.config.yml");
            }


            // Generate or copy new config
            saveResource("config.yml", true); // This will save the default config.yml

        }
    }

    public static BukkitDupePlus getPlugin() {
        return plugin;
    }

    public LegacyPaperCommandManager<CommandSender> getCommandManager() {
        return manager;
    }

    public BukkitDupeCommand getBukkitDupeCommand() {
        return bukkitDupeCommand;
    }
}
