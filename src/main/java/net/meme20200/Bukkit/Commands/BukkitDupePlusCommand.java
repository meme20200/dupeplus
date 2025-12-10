package net.meme20200.Bukkit.Commands;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.meme20200.Bukkit.Utilities.BukkitConfigyml;

import net.kyori.adventure.audience.Audience;
import net.meme20200.Bukkit.menus.BlocklistMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;

import static net.meme20200.Bukkit.BukkitDupePlus.getPlugin;


public class BukkitDupePlusCommand {

    public BukkitDupePlusCommand() {
        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command"))
                        .senderType(Player.class)
                        .handler(this::executeHelpCommand)
        );

        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command"))
                        .literal("reload")
                        .permission("dupeplus.reload")
                        .senderType(Player.class)
                        .handler(this::executeReloadCommand)
        );

        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command"))
                        .permission("dupeplus.blocklist")
                        .literal("blocklist")
                        .senderType(Player.class)
                        .handler(this::executeGUICommand)
        );

        getPlugin().getCommandManager().command(
                getPlugin().getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command"))
                        .literal("block")
                        .senderType(Player.class)
                        .handler(this::executeBlockCommand)
        );
    }

    private void executeGUICommand(CommandContext<Player> context) {
        Player player = context.sender();
        if (player.hasPermission("dupeplus.blocklist.gui")) {
            new BlocklistMenu().getMenu(player).open(player);
        }
    }

    private void executeReloadCommand(CommandContext<Player> context) {
        Player player = context.sender();
        Audience p = getPlugin().adventure().player(player);
        if (player.hasPermission("dupeplus.reload")) {
            BukkitConfigyml.reloadConfig();
            p.sendMessage(MiniMessage.miniMessage().deserialize("<green>DupePlus</green> <dark_gray>|</dark_gray> <white>Successfully reloaded! (if command name is changed, then players will need to rejoin to see the dupe command in tab complete)</white>"));
        }
    }

    private void executeHelpCommand(CommandContext<Player> context) {
        Player player = context.sender();
        Audience p = getPlugin().adventure().player(player);
        // Help message
        p.sendMessage(MiniMessage.miniMessage().deserialize("<st><gray>                          </gray></st>\n" +
                "<green>/dupeplus</green>\n" +
                "<gray>├</gray> <aqua>blacklist</aqua> (Opens a GUI)\n" +
                "<gray>├</gray> <aqua>block</aqua> (Makes the item you are holding undupeable)\n" +
                "<gray>└</gray> <aqua>reload</aqua> (Reloads the config.yml)\n" +
                "<st><gray>                          </gray></st>"));


    }

    private void executeBlockCommand(CommandContext<Player> context) {
        Player player = context.sender();
        Audience p = getPlugin().adventure().player(player);

        if (getPlugin().getConfig().getString("dupe.dupe-on", "MainHand").equals("MainHand")) {
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            }
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (!NBTEditor.contains(itemStack, NBTEditor.CUSTOM_DATA, "dupenotallowed")) {
                itemStack = NBTEditor.set(itemStack, true, NBTEditor.CUSTOM_DATA, "dupenotallowed");
            }
            player.getInventory().setItemInMainHand(itemStack);


        } else if (getPlugin().getConfig().getString("dupe.dupe-on", "MainHand").equals("OffHand")) {
            if (player.getInventory().getItemInOffHand().getType() == Material.AIR) {
                return;
            }
            ItemStack itemStack = player.getInventory().getItemInOffHand();
            if (!NBTEditor.contains(itemStack, NBTEditor.CUSTOM_DATA, "dupenotallowed")) {
                itemStack = NBTEditor.set(itemStack, true, NBTEditor.CUSTOM_DATA, "dupenotallowed");
            }
            player.getInventory().setItemInOffHand(itemStack);
        } else {
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            }
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (!NBTEditor.contains(itemStack, NBTEditor.CUSTOM_DATA, "dupenotallowed")) {
                itemStack = NBTEditor.set(itemStack, true, NBTEditor.CUSTOM_DATA, "dupenotallowed");
            }
            player.getInventory().setItemInMainHand(itemStack);
        }

        p.sendMessage(MiniMessage.miniMessage().deserialize("<green>DupePlus</green> <dark_gray>|</dark_gray> <white>Successfully made your item undupeable!</white>"));
    }
}
