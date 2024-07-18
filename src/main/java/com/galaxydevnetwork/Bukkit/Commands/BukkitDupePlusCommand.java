package com.galaxydevnetwork.Bukkit.Commands;

import com.galaxydevnetwork.Bukkit.Utilities.BukkitConfigyml;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.galaxydevnetwork.Bukkit.BukkitDupePlus.getPlugin;

public class BukkitDupePlusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            if (strings[0].equals("reload")) {
                if (commandSender.hasPermission("dupeplus.admin.reloadconfig")) {
                    BukkitConfigyml.reloadConfig();
                    commandSender.sendMessage("%prefix% | Reloaded the config!".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
                } else {
                    commandSender.sendMessage("%prefix% | You have no permission!".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
                }
            } else {
                commandSender.sendMessage("%prefix% | - Reload".replaceAll("%prefix%", PlainTextComponentSerializer.plainText().serialize(BukkitConfigyml.getPrefix())));
            }
            return true;
        }
        Player player = (Player) commandSender;
        Audience audience = getPlugin().adventure().player(player);
        if (!(strings.length == 0)) {
            if (strings[0].equals("reload")) {
                if (player.hasPermission("dupeplus.admin.reloadconfig")) {
                    BukkitConfigyml.reloadConfig();
                    audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <green>Reloaded the config!</green>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                } else {
                    audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <red>You have no permission!</red>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
                }
                return true;
            } else {
                audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <white>- Reload</white>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
            }
        } else {
            audience.sendMessage(MiniMessage.miniMessage().deserialize("%prefix% <dark_gray>|</dark_gray> <white>- Reload</white>".replaceAll("%prefix%", MiniMessage.miniMessage().serialize(BukkitConfigyml.getPrefix()))));
        }
        return true;
    }
}
