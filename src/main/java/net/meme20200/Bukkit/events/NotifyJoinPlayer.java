package net.meme20200.Bukkit.events;

import net.kyori.adventure.audience.Audience;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class NotifyJoinPlayer implements Listener {
    private final String newversion;

    public NotifyJoinPlayer(String newversion) {
        this.newversion = newversion;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dupeplus.updates.notify")) {
            Audience p = getPlugin().adventure().player(event.getPlayer());
            BukkitConfigyml.updatePlayerMessage(event.getPlayer(), newversion, p);
        }
    }
}
