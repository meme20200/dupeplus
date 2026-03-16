package net.meme20200.Bukkit.Commands;

import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupeBlockListCommand {

    // /dupeblocklist for this, but this time I'll be adding /blocklist cuz why not...
    public BukkitDupeBlockListCommand() {
        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeblocklist", Description.of("Dupe Blocklist Command, opens a Blacklist Menu"))
                        .permission("dupeplus.admin.blocklist")
                        .senderType(Player.class)
                        .handler(getPlugin().getBukkitDupePlusCommand()::executeGUICommand)
        );

        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("blocklist", Description.of("Dupe Blocklist Command, opens a Blacklist Menu"))
                        .permission("dupeplus.admin.blocklist")
                        .senderType(Player.class)
                        .handler(getPlugin().getBukkitDupePlusCommand()::executeGUICommand)
        );
    }
}
