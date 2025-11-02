package net.meme20200.Bukkit.Commands;

import net.meme20200.Bukkit.Utilities.BukkitConfigyml;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandArguments;
import dev.jorel.commandapi.executors.CommandExecutor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupePlusCommand implements CommandExecutor {
    @Override
    public void run(CommandSender commandSender, CommandArguments commandArguments) throws WrapperCommandSyntaxException {
        String argument = (String) commandArguments.getOptional("option").orElse("");
        String item = (String) commandArguments.getOptional("item").orElse("");

        if (!(commandSender instanceof Player)) {
            if (argument.equals("reload")) {
                if (commandSender.hasPermission("dupeplus.admin.reloadconfig")) {
                    BukkitConfigyml.reloadConfig();
                    commandSender.sendMessage("%prefix% | Reloaded the config!".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
                } else {
                    commandSender.sendMessage("%prefix% | You have no permission!".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
                }
            } else {
                commandSender.sendMessage("%prefix% | Reload configurations: /dupeplus reload".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
            }
            return;
        }
        Player player = (Player) commandSender;
        Audience audience = getPlugin().adventure().player(player);
        if (!(argument.isEmpty())) {
            if (argument.equals("reload")) {
                if (player.hasPermission("dupeplus.admin.reloadconfig")) {
                    BukkitConfigyml.reloadConfig();
                    audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <green>Reloaded the config!</green>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                } else {
                    audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <red>You have no permission!</red>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                }
            } else if (argument.equals("blacklist")) {
                if (player.hasPermission("dupeplus.admin.blacklist")) {
                    if (!(item.isEmpty())) {
                        BukkitConfigyml.config.getStringList("dupe.blacklist.items").add(item);
                        BukkitConfigyml.reloadConfig();
                        audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <red>Blacklisted %blacklist-item%</red>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix())).replaceAll("%blacklist-item%", item)));
                    } else {
                        audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <red>Please add a item to blacklist</red>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                    }
                } else {
                    audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <red>You have no permission!</red>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                }
            } else {
                audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix%\n <white>- reload</white>\n <white>- blacklist [item]".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
            }
        } else {
            audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix%\n <white>- reload</white>\n <white>- blacklist [item]".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
        }
    }


}
