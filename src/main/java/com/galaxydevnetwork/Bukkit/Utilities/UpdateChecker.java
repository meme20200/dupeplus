package com.galaxydevnetwork.Bukkit.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;


public class UpdateChecker {

    private final JavaPlugin plugin;
    private final boolean isSpigot;

    public UpdateChecker(JavaPlugin plugin, boolean isSpigot) {
        this.plugin = plugin;
        this.isSpigot = isSpigot;
    }

    public void SpigotVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=110621/~").openStream();
                 Scanner scann = new Scanner(is)) {
                if (scann.hasNext()) {
                    consumer.accept(scann.next());
                } else {
                    plugin.getLogger().info("No version information available from Spigot.");
                    consumer.accept(null); // Notify consumer that no version was found
                }
            } catch (IOException e) {
                plugin.getLogger().info("Unable to check for updates (Spigot): " + e.getMessage());
                consumer.accept(null); // Handle error case by passing null
            }
        });
    }

    public void ModrinthVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream is = new URL("https://api.modrinth.com/v2/project/pyRTWAFM/version").openStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parse the JSON response
                JSONArray jsonResponse = new JSONArray(response.toString());

                if (!jsonResponse.isEmpty()) {
                    // Get the first version object (latest one)
                    JSONObject latestVersion = jsonResponse.getJSONObject(0);
                    String versionNumber = latestVersion.getString("version_number");

                    // Pass the version number to the consumer
                    consumer.accept(versionNumber);
                } else {
                    plugin.getLogger().info("No version information available from Modrinth.");
                    consumer.accept(null); // Notify consumer if no version data found
                }

            } catch (IOException e) {
                plugin.getLogger().info("Unable to check for updates (Modrinth): " + e.getMessage());
                consumer.accept(null); // Handle error case by passing null
            }
        });
    }

    public void getVersion(final Consumer<String> consumer) {
        if (isSpigot) {
            SpigotVersion(consumer);
        } else {
            ModrinthVersion(consumer);
        }
    }
}