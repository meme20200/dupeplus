package net.meme20200.dupeplus.commands;

import net.meme20200.dupeplus.BukkitDupePlus;
import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

/**
 * The class for {@code /dupefilter}
 */
public class BukkitDupeFilterCommand {

    private final BukkitDupePlus plugin;

    /**
     * The shorthand command for {@link BukkitDupePlusCommand {@code /dupeplus filter}}.
     */
    public BukkitDupeFilterCommand(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    public void registerCommand() {
        plugin.getCommandManager().command(
                plugin.getCommandManager().commandBuilder("dupefilter", Description.of("Dupe Blocklist Command, opens a Blacklist Menu"))
                        .permission("dupeplus.admin.features")
                        .senderType(Player.class)
                        .handler(plugin.getBukkitDupePlusCommand()::executeFilterGUI)
        );
    }

    public void unregisterCommand() {
        plugin.getCommandManager().deleteRootCommand("dupefilter");
    }
}
