package net.meme20200.dupeplus.commands;

import net.meme20200.dupeplus.BukkitDupePlus;
import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

/**
 * The class for {@link BukkitDupeFilterCommand#BukkitDupeFilterCommand() /dupeblock}
 */
public class BukkitDupeBlockCommand {

    private final BukkitDupePlus plugin;

    /**
     * The shorthand command for {@link BukkitDupePlusCommand {@code /dupeplus block}}.
     */
    public BukkitDupeBlockCommand(BukkitDupePlus plugin) {
        this.plugin = plugin;
    }

    public void registerCommand() {
        plugin.getCommandManager().command(
                plugin.getCommandManager().commandBuilder("dupeblock", Description.of("DupePlus Command"))
                        .permission("dupeplus.admin.block")
                        .senderType(Player.class)
                        .handler(plugin.getBukkitDupePlusCommand()::executeBlockCommand)
        );
    }

    public void unregisterCommand() {
        plugin.getCommandManager().deleteRootCommand("dupeblock");
    }
}
