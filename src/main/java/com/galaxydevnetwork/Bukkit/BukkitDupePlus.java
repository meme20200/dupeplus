package com.galaxydevnetwork.Bukkit;

import com.galaxydevnetwork.Bukkit.Commands.BukkitDupePlusCommand;
import com.galaxydevnetwork.Bukkit.TabCompletors.BukkitDupeCommandTabCompleter;
import com.galaxydevnetwork.Bukkit.TabCompletors.BukkitDupePlusCommandTabCompleter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    public static BukkitDupePlus getPlugin() {
        return plugin;
    }
}
