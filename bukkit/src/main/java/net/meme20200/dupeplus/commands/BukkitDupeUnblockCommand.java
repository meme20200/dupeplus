package net.meme20200.dupeplus.commands;

import net.meme20200.dupeplus.BukkitDupePlus;
import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

/**
 * The class for {@link BukkitDupeFilterCommand#BukkitDupeFilterCommand() /dupeunblock}
 */
public class BukkitDupeUnblockCommand {

    private final BukkitDupePlus plugin;

    /**
     * The shorthand command for {@link BukkitDupePlusCommand {@code /dupeplus unblock}}.
     */
    public BukkitDupeUnblockCommand(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    public void registerCommand() {
        plugin.getCommandManager().command(
                plugin.getCommandManager().commandBuilder("dupeunblock", Description.of("DupePlus Command"))
                        .permission("dupeplus.admin.unblock")
                        .senderType(Player.class)
                        .handler(plugin.getBukkitDupePlusCommand()::executeUnblockCommand)
        );
    }

    public void unregisterCommand() {
        plugin.getCommandManager().deleteRootCommand("dupeunblock");
    }
}
