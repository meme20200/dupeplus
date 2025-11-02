package net.meme20200.Bukkit;

import net.meme20200.Bukkit.Commands.BukkitDupeCommand;
import net.meme20200.Bukkit.Commands.BukkitDupePlusCommand;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import net.meme20200.Bukkit.Utilities.UpdateChecker;
import net.meme20200.Bukkit.events.NotifyJoinPlayer;
import dev.jorel.commandapi.*;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.meme20200.Bukkit.Utilities.BukkitConfigyml.*;

public class BukkitDupePlus extends JavaPlugin {
    private static BukkitDupePlus plugin;
    private BukkitAudiences adventure;
    public static String version = "1.4.0";
    public static boolean isItemsadderInstalled = false;

    public static boolean isPlaceholderAPIInstalled = false;



    public @NotNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public void reloadCommand(String customName) {
        if (!BukkitConfigyml.dupeAliases().isEmpty()) {
            new CommandAPICommand(customName)
                    .withOptionalArguments(new IntegerArgument("times")) // The arguments
                    .withAliases(BukkitConfigyml.dupeAliases().toArray(new String[0]))// Command aliases
                    .withPermission(CommandPermission.NONE)               // Required permissions
                    .executes(new BukkitDupeCommand())
                    .register();

        } else {
            new CommandAPICommand(customName)
                    .withOptionalArguments(new IntegerArgument("times")) // The arguments
                    .withPermission(CommandPermission.NONE)               // Required permissions
                    .executes(new BukkitDupeCommand())
                    .register();
        }
    }
    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPISpigotConfig(this));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();

        this.adventure = BukkitAudiences.create(this);
        plugin = this;
        version = plugin.getDescription().getVersion();
        checkConfigVersion();
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        isItemsadderInstalled = (getServer().getPluginManager().getPlugin("ItemsAdder") != null && config.getBoolean("integrations.itemsadder", true));
        isPlaceholderAPIInstalled = (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && config.getBoolean("integrations.placeholderapi", true));



        Metrics metrics = new Metrics(this, 18772);
        metrics.addCustomChart(new SimplePie("configversion", () -> version));
        if (isCheckUpdateAllowed()) {
            new UpdateChecker(this, BukkitConfigyml.isSpigotMC()).getVersion(newversion -> {
                if (!(BukkitDupePlus.version.equals(newversion))) {
                    if (BukkitConfigyml.isPlayerNotifyAllowed()) {
                        Bukkit.getServer().getPluginManager().registerEvents(new NotifyJoinPlayer(newversion), this);
                        return;
                    }
                    if (BukkitConfigyml.isPlayerNotifyAllowed() || BukkitConfigyml.isConsoleNotifyAllowed()) {
                        if (BukkitConfigyml.isConsoleNotifyAllowed()) {
                            BukkitConfigyml.updateConsoleMessage(this, newversion);
                        }
                    }
                }
            });
        }

        new CommandAPICommand("dupeplus")
                .withOptionalArguments(new StringArgument("option").includeSuggestions(ArgumentSuggestions.strings("blacklist", "reload")),
                        new StringArgument("item"))// The arguments

                .withPermission(CommandPermission.OP)               // Required permissions
                .executes(new BukkitDupePlusCommand())
                .register();
        if (BukkitConfigyml.isCustomCommandEnabled() & !BukkitConfigyml.customCommandName().isEmpty()) {
            reloadCommand(BukkitConfigyml.customCommandName());
        } else {
            reloadCommand("dupe");
        }
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
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

    public void unregisterCommands(String name) {
        CommandAPI.unregister(name);
    }
}
