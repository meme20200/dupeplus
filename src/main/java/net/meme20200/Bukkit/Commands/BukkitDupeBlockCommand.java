package net.meme20200.Bukkit.Commands;

import org.bukkit.entity.Player;
import org.incendo.cloud.description.Description;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupeBlockCommand {

    // I'll be using /dupeblock,
    public BukkitDupeBlockCommand() {
        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeblock", Description.of("DupePlus Command"))
                        .permission("dupeplus.admin.block")
                        .senderType(Player.class)
                        .handler(getPlugin().getBukkitDupePlusCommand()::executeBlockCommand)
        );
    }
}
