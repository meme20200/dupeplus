package net.meme20200.dupeplus.events;

import net.kyori.adventure.audience.Audience;
import net.meme20200.dupeplus.BukkitDupePlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyJoinPlayer implements Listener {
    private final BukkitDupePlus plugin;
    private final String newversion;

    public NotifyJoinPlayer(BukkitDupePlus plugin, String newversion) {
        this.plugin = plugin;
        this.newversion = newversion;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("dupeplus.updates.notify")) return;
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
        plugin.getConfigyml().updatePlayerMessage(player, newversion, p);
    }
}
