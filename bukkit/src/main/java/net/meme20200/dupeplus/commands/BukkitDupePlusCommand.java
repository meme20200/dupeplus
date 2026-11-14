package net.meme20200.dupeplus.commands;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.menus.config.ConfigMenu;
import net.meme20200.dupeplus.menus.config.filter.FilterMenu;
import net.meme20200.dupeplus.utilities.ItemUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;
import org.jspecify.annotations.NonNull;


/**
 * The class for the {@code /dupeplus} command
 */
public class BukkitDupePlusCommand {
    private final BukkitDupePlus plugin;

    /**
     * Registering the {@code /dupeplus} command and its subcommands (e.g., {@code /dupeplus block}, {@code /dupeplus reload})
     */
    public BukkitDupePlusCommand(BukkitDupePlus plugin) {
        this.plugin = plugin;
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).permission("dupeplus.admin").senderType(Player.class).handler(this::executeHelpCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("reload").permission("dupeplus.admin.reload").handler(this::executeReloadCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("config").permission("dupeplus.admin.config").senderType(Player.class).handler(this::executeConfigCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("filter").permission("dupeplus.admin.filter").senderType(Player.class).handler(this::executeFilterGUI));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("block").permission("dupeplus.admin.block").senderType(Player.class).handler(this::executeBlockCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("unblock").permission("dupeplus.admin.unblock").senderType(Player.class).handler(this::executeUnblockCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("issues", "bugs").permission("dupeplus.admin").senderType(Player.class).handler(this::executeIssuesCommand));
        plugin.getCommandManager().command(plugin.getCommandManager().commandBuilder("dupeplus", Description.of("DupePlus Command")).literal("docs").permission("dupeplus.admin").senderType(Player.class).handler(this::executeDocsCommand));
    }

    private void executeDocsCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
        p.sendMessage(MessagesUtil.docsCommandMessage);
    }

    /**
     * For {@code /dupeplus config}
     *
     * @param context Used in the code
     * @since 1.5
     */
    private void executeConfigCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        new ConfigMenu().getMenu(plugin).open(player);
    }

    /**
     * For the {@code /dupeplus filter} and {@link BukkitDupeFilterCommand {@code /dupefilter}} command.
     *
     * @param context Used in the code
     */
    public void executeFilterGUI(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        if (!player.hasPermission("dupeplus.admin.filter")) return;
        new FilterMenu().getMenu(plugin).open(player);
    }

    /**
     * For the {@code /dupeplus reload} command.
     *
     * @param context Used in the code
     */
    private void executeReloadCommand(@NonNull CommandContext<CommandSender> context) {
        if (context.sender() instanceof Player) {
            Player player = (Player) context.sender();
            @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
            if (!player.hasPermission("dupeplus.admin.reload")) return;
            boolean customcommandReloaded = plugin.getConfigManager().reloadConfig();
            if (customcommandReloaded) {
                p.sendMessage(Component.text("DupePlus").color(NamedTextColor.GREEN).appendSpace().append(Component.text("|").color(NamedTextColor.DARK_GRAY)).appendSpace().append(Component.text("Successfully reloaded! (Players will need to rejoin to see the changed dupe command in tab complete)").color(NamedTextColor.WHITE)));
            } else {
                p.sendMessage(Component.text("DupePlus").color(NamedTextColor.GREEN).appendSpace().append(Component.text("|").color(NamedTextColor.DARK_GRAY)).appendSpace().append(Component.text("Successfully reloaded!").color(NamedTextColor.WHITE)));
            }
        } else {
            if (!context.sender().hasPermission("dupeplus.admin.reload")) return;
            boolean customcommandReloaded = plugin.getConfigManager().reloadConfig();
            if (customcommandReloaded) {
                context.sender().sendMessage("DupePlus | Successfully reloaded! (Players will need to rejoin to see the changed dupe command in tab complete)");
            } else {
                context.sender().sendMessage("DupePlus | Successfully reloaded!");
            }
        }
    }

    /**
     * For the {@code /dupeplus help} command or just the {@code /dupeplus} with no sub command.
     *
     * @param context Used in the code
     */
    private void executeHelpCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);

        p.sendMessage(MessagesUtil.helpCommandMessage);
    }

    /**
     * For the {@code /dupeplus issues} command, which sends the player the GitHub issues link
     *
     * @param context Used in the code
     */
    private void executeIssuesCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
        // Issues message
        p.sendMessage(MessagesUtil.issuesCommandMessage);

    }

    /**
     * For the {@code /dupeplus block} command, which makes the item undupeable.
     *
     * @param context Used in the code
     */
    public void executeBlockCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
        if (!player.hasPermission("dupeplus.admin.block")) {
            p.sendMessage(UtilAPIs.translatable(plugin, "permission-message", "<prefix> <red>You are not allowed to use this command</red>", player, Placeholder.component("prefix", plugin.getConfigyml().getPrefix(player))));
            return;
        }
        boolean isMainHand = plugin.getConfig().getString("dupe.dupe-on", "mainhand").equalsIgnoreCase("mainhand");
        if (isMainHand ? UtilAPIs.isAir(player.getInventory().getItemInMainHand().getType()) : UtilAPIs.isAir(player.getInventory().getItemInOffHand().getType())) return;
        ItemStack itemStack = isMainHand ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();

        ItemUtils.undupeableItem(itemStack, plugin.getConfigManager().getCustomNBTItem().tag());
        p.sendMessage(UtilAPIs.translatable(plugin, "block.successful", "<prefix> <white>Successfully made your item undupeable!</white>", player, Placeholder.component("prefix", plugin.getConfigyml().getPrefix(player))));
    }

    /**
     * For the {@code /dupeplus block} command, which makes the item dupeable if the item was undupeable.
     *
     * @param context Used in the code
     */
    public void executeUnblockCommand(@NonNull CommandContext<Player> context) {
        Player player = context.sender();
        @SuppressWarnings("resource") Audience p = plugin.adventure().player(player);
        if (!player.hasPermission("dupeplus.admin.unblock")) {
            p.sendMessage(UtilAPIs.translatable(plugin, "permission-message", "<prefix> <red>You are not allowed to use this command</red>", player, Placeholder.component("prefix", plugin.getConfigyml().getPrefix(player))));
            return;
        }
        boolean isMainHand = plugin.getConfig().getString("dupe.dupe-on", "mainhand").equalsIgnoreCase("mainhand");
        if (isMainHand ? UtilAPIs.isAir(player.getInventory().getItemInMainHand().getType()) : UtilAPIs.isAir(player.getInventory().getItemInOffHand().getType())) return;
        ItemStack itemStack = isMainHand ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();

        ItemUtils.dupeableItem(itemStack, plugin.getConfigManager().getCustomNBTItem().tag());
        p.sendMessage(UtilAPIs.translatable(plugin, "unblock.successful", "<prefix> <white>Successfully made your item dupeable!</white>", player, Placeholder.component("prefix", plugin.getConfigyml().getPrefix(player))));
    }
}
