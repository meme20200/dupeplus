package com.galaxydevnetwork.Bukkit;

import com.galaxydevnetwork.Bukkit.Commands.BukkitDupePlusCommand;
import com.galaxydevnetwork.Bukkit.TabCompletors.BukkitDupeCommandTabCompleter;
import com.galaxydevnetwork.Bukkit.TabCompletors.BukkitDupePlusCommandTabCompleter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class BukkitDupePlus extends JavaPlugin {
    private static BukkitDupePlus plugin;
    private BukkitAudiences adventure;

    public @NotNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        plugin = this;
        checkConfigVersion();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("dupe")).setExecutor(new com.galaxydevnetwork.Bukkit.Commands.BukkitDupeCommand());
        Objects.requireNonNull(getCommand("dupe")).setTabCompleter(new BukkitDupeCommandTabCompleter());
        Objects.requireNonNull(getCommand("dupeplus")).setExecutor(new BukkitDupePlusCommand());
        Objects.requireNonNull(getCommand("dupeplus")).setTabCompleter(new BukkitDupePlusCommandTabCompleter());
        Metrics metrics = new Metrics(this, 18772);
        metrics.addCustomChart(new SimplePie("configversion", () -> "1.0"));
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

        // Check if config-version is 1.0
        if (config.getString("config-version", "1.1").equals("1.0")) {
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
}
