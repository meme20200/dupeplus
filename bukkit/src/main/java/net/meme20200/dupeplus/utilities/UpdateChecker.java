package net.meme20200.dupeplus.utilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.function.Consumer;


/**
 * The Update Checker used by DupePlus to check for any new updates.
 */
public class UpdateChecker {

    private final JavaPlugin plugin;
    private final boolean isSpigot;

    public UpdateChecker(JavaPlugin plugin, boolean isSpigot) {
        this.plugin = plugin;
        this.isSpigot = isSpigot;
    }

    /**
     * Update checker using SpigotMC
     * @param newversion The function that will do something if there's a new version.
     */
    public void spigotVersion(final Consumer<String> newversion) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            //noinspection resource
            try (InputStream is = HttpClient.newHttpClient().send(HttpRequest.newBuilder().uri(URI.create("https://api.spigotmc.org/legacy/update.php?resource=110621/~")).GET().build(), HttpResponse.BodyHandlers.ofInputStream()).body(); Scanner scann = new Scanner(is)) {
                if (scann.hasNext()) {
                    // Pass the version number to the consumer
                    newversion.accept(scann.next());
                } else {
                    plugin.getLogger().info("No version information available from Spigot.");
                    newversion.accept(null); // Handle error case by passing null
                }
            } catch (IOException | InterruptedException e) {
                plugin.getLogger().info("Unable to check for updates (Spigot): " + e.getMessage());
                newversion.accept(null); // Handle error case by passing null
            }
        });
    }

    /**
     * Update checker using Modrinth
     * @param newversion The function that will do something if there's a new version.
     */
    public void modrinthVersion(final Consumer<String> newversion) {
        // Runs asynchronously to not lag the server.
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            //noinspection resource
            try (InputStream is = HttpClient.newHttpClient().send(HttpRequest.newBuilder().uri(URI.create("https://api.modrinth.com/v2/project/pyRTWAFM/version")).GET().build(), HttpResponse.BodyHandlers.ofInputStream()).body(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                JSONArray jsonResponse = new JSONArray(response.toString());
                if (!jsonResponse.isEmpty()) {
                    // Get the first version object (latest one)
                    JSONObject latestVersion = jsonResponse.getJSONObject(0);
                    String versionNumber = latestVersion.getString("version_number");

                    // Pass the version number to the consumer
                    newversion.accept(versionNumber);
                } else {
                    plugin.getLogger().info("No version information available from Modrinth.");
                    newversion.accept(null); // Notify server owner if no version data found
                }

            } catch (IOException | InterruptedException | JSONException e) {
                plugin.getLogger().info("Unable to check for updates (Modrinth): " + e.getMessage());
                newversion.accept(null); // Handle error case by passing null
            }
        });
    }

    public void getVersion(final Consumer<String> newversion) {
        if (isSpigot) spigotVersion(newversion);
        else modrinthVersion(newversion);
    }
}