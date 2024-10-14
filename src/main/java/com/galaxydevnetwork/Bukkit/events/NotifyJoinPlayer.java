package com.galaxydevnetwork.Bukkit.events;

import com.galaxydevnetwork.Bukkit.Utilities.BukkitConfigyml;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyJoinPlayer implements Listener {
    private final String newversion;

    public NotifyJoinPlayer(String newversion) {
        this.newversion = newversion;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dupeplus.updates.notify")) {
            BukkitConfigyml.updatePlayerMessage(event.getPlayer(), newversion);
        }
    }
}
