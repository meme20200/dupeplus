package net.meme20200.Bukkit;

import dev.faststats.bukkit.BukkitContext;
import net.meme20200.Bukkit.commands.*;
import net.meme20200.Bukkit.utilities.UpdateChecker;
import net.meme20200.Bukkit.events.NotifyJoinPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.bukkit.internal.CraftBukkitReflection;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.setting.ManagerSetting;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.meme20200.Bukkit.utilities.BukkitConfigyml.*;

public class BukkitDupePlus extends JavaPlugin {
    private static BukkitDupePlus plugin;
    public static String version = "1.4.3";

    private LegacyPaperCommandManager<CommandSender> manager;
    private BukkitAudiences adventure;

    public static boolean isItemsadderInstalled = false;
    public static boolean isPlaceholderAPIInstalled = false;
    public static boolean isVaultInstalled = false;


    private static BukkitDupeCommand bukkitDupeCommand;
    private static BukkitDupePlusCommand bukkitDupePlusCommand;
    private BukkitContext fastStatsMetrics = null;

    private static Economy econ = null;

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

        boolean setupEconomyResult = setupEconomy();

        isItemsadderInstalled = (getServer().getPluginManager().getPlugin("ItemsAdder") != null && config.getBoolean("integrations.itemsadder", true));
        isPlaceholderAPIInstalled = (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && config.getBoolean("integrations.placeholderapi", true));
        isVaultInstalled = (setupEconomyResult && config.getBoolean("integrations.vault", true));

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

        initMetrics();

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


        bukkitDupePlusCommand = new BukkitDupePlusCommand();
        new BukkitDupeBlockCommand();
        new BukkitDupeUnblockCommand();
        new BukkitDupeBlockListCommand();
    }

    public void unregisterCommand(String commandName) {
        getPlugin().getCommandManager().deleteRootCommand(commandName);
    }

    private void initMetrics() {

        Metrics metrics = new Metrics(this, 18772);
        metrics.addCustomChart(new SimplePie("configversion", () -> version));

        fastStatsMetrics = new BukkitContext.Factory(this, "9f44bf01b4e697d6ee8b3144f77d55cd")
                .metrics(factory -> factory.addMetric(dev.faststats.data.Metric.string("config_version", () -> version)).create())
                .create();

        fastStatsMetrics.ready();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        if (fastStatsMetrics != null) {
            fastStatsMetrics.shutdown();
            fastStatsMetrics = null;
        }
    }

    private void checkConfigVersion() {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        List<String> versions = new ArrayList<>(List.of("1.0", "1.1", "1.2", "1.3", "1.4", "1.4.2"));

        // Check if config-version is 1.0, 1.1, 1.2, 1.3, 1.4, 1.4.2, 1.4.3
        if (versions.contains(config.getString("config-version", "1.4.3"))) {
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

    public static Economy getEconomy() {
        return econ;
    }

    public LegacyPaperCommandManager<CommandSender> getCommandManager() {
        return manager;
    }

    public BukkitDupeCommand getBukkitDupeCommand() {
        return bukkitDupeCommand;
    }

    public BukkitDupePlusCommand getBukkitDupePlusCommand() {
        return bukkitDupePlusCommand;
    }
}
