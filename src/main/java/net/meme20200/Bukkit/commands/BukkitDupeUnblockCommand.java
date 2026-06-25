package net.meme20200.Bukkit.commands;

import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupeUnblockCommand {

    // /dupeunblock
    public BukkitDupeUnblockCommand() {
        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeunblock", Description.of("DupePlus Command"))
                        .permission("dupeplus.admin.unblock")
                        .senderType(Player.class)
                        .handler(getPlugin().getBukkitDupePlusCommand()::executeUnblockCommand)
        );
    }


}
