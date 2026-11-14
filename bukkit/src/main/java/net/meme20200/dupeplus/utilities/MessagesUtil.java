package net.meme20200.dupeplus.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessagesUtil {
    public static final String enabled = LegacyComponentSerializer.legacySection().serialize(Component.text("Enabled").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));
    public static final String disabled = LegacyComponentSerializer.legacySection().serialize(Component.text("Disabled").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true));
    public static final String toggleOption = LegacyComponentSerializer.legacySection().serialize(Component.text("Click to toggle the feature").color(NamedTextColor.DARK_GRAY));

    public static final String blacklistLore = LegacyComponentSerializer.legacySection().serialize(Component.text("Blacklist").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true));
    public static final String whitelistLore = LegacyComponentSerializer.legacySection().serialize(Component.text("Whitelist").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));


    public static final List<String> cooldownSecondsLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.cooldown.seconds").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the cooldown seconds").color(NamedTextColor.WHITE))
    );
    public static final List<String> customCommandNameLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.custom-command.name").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the custom command name").color(NamedTextColor.WHITE))
    );

    public static final List<String> customNBTItemLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.custom-nbt-item.tag").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the custom NBT tag").color(NamedTextColor.WHITE))
    );

    public static final List<String> economyPriceLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.economy.tag").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the price").color(NamedTextColor.WHITE))
    );

    public static final List<String> minValueLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.multiplier.min").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the multiplier minimum").color(NamedTextColor.WHITE))
    );

    public static final List<String> aliasLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.aliases.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change this alias to something else").color(NamedTextColor.WHITE))
    );

    public static final List<String> multiplierMaxValue = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.multiplier.max.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change this multiplier max's value to something else").color(NamedTextColor.WHITE))
    );

    public static final List<String> itemListLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("Q (Drop) to remove").color(NamedTextColor.GRAY))
    );

    public static final List<String> loreTextLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.lore.text").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the text lore").color(NamedTextColor.WHITE))
    );

    public static final List<String> worldsLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.worlds.worlds.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the name in the filtered worlds").color(NamedTextColor.WHITE))
    );

    public static final List<String> addWorldLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.worlds.worlds.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Filter a new world").color(NamedTextColor.WHITE))
    );

    public static final List<String> addMultiplier = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.multiplier.max.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Add a new multiplier. (Defaults to 1)").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Edit the value of the new multiplier in the max section!").color(NamedTextColor.RED))
    );

    public static final List<String> addAliasLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.aliases.*").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Add a new alias for the dupe command").color(NamedTextColor.WHITE))
    );

    public static final List<String> soundFeedbackCategoryLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.sound-feedback.category").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the sound category").color(NamedTextColor.WHITE))
    );

    public static final List<String> soundFeedbackCategoryHelpLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("List of the sound categories in Minecraft:").color(NamedTextColor.GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("master").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("music").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("record").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("weather").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("block").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("hostile").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("neutral").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("player").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("ambient").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("voice").color(NamedTextColor.WHITE)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("ui").color(NamedTextColor.WHITE))
    );

    public static final List<String> soundFeedbackSoundLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.sound-feedback.sound").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the played sound").color(NamedTextColor.WHITE))
    );


    public static final List<String> soundFeedbackVolumeLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.sound-feedback.volume").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the volume of the played sound").color(NamedTextColor.WHITE))
    );

    public static final List<String> soundFeedbackPitchLore = List.of(
            LegacyComponentSerializer.legacySection().serialize(Component.text("#dupe.sound-feedback.lore").color(NamedTextColor.DARK_GRAY)),
            LegacyComponentSerializer.legacySection().serialize(Component.text("Change the pitch of the played sound").color(NamedTextColor.WHITE))
    );


    public static final String cooldownEnabled = LegacyComponentSerializer.legacySection().serialize(Component.text("Cooldown Feature").color(NamedTextColor.WHITE));

    public static final String configureLore = LegacyComponentSerializer.legacySection().serialize(Component.text("Click to configure").color(NamedTextColor.DARK_GRAY));
    public static final List<String> modrinthUpdateLore = List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#updates.api").color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("The website to check for updates").color(NamedTextColor.WHITE)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("Modrinth").color(NamedTextColor.GREEN)), MessagesUtil.toggleOption);
    public static final List<String> spigotUpdateLore = List.of(LegacyComponentSerializer.legacySection().serialize(Component.text("#updates.api").color(NamedTextColor.GRAY)), LegacyComponentSerializer.legacySection().serialize(Component.text("The website to check for updates").color(NamedTextColor.WHITE)), "", LegacyComponentSerializer.legacySection().serialize(Component.text("SpigotMC").color(NamedTextColor.YELLOW)), MessagesUtil.toggleOption);

    public static final String BLANK = LegacyComponentSerializer.legacySection().serialize(Component.text(" "));
    public static final @NotNull Component helpCommandMessage = MiniMessage.miniMessage().deserialize("<st><gray>                          </gray></st>\n" +
            "<green>/dupeplus</green>\n" +
            "<green>DupePlus Docs:</green> <click:open_url:'https://galaxyy.gitbook.io/dupeplus'>https://galaxyy.gitbook.io/dupeplus</click>\n" +
            "<gray>├</gray> <aqua>issues</aqua> (Links to the DupePlus bug tracker)\n" +
            "<gray>├</gray> <aqua>docs</aqua> (Links to the DupePlus Docs)\n" +
            "<gray>├</gray> <aqua>config</aqua> (Opens the in-game config menu)\n" +
            "<gray>├</gray> <aqua>filter</aqua> (Opens a GUI)\n" +
            "<gray>├</gray> <aqua>block</aqua> (Makes the item you are holding undupeable)\n" +
            "<gray>├</gray> <aqua>unblock</aqua> (Makes the item you are holding dupeable if you made it undupeable)\n" +
            "<gray>└</gray> <aqua>reload</aqua> (Reloads the config.yml)\n" + "<st><gray>                          </gray></st>");
    public static final @NotNull Component issuesCommandMessage = MiniMessage.miniMessage().deserialize("<st><gray>                          </gray></st>\n" +
            "<green>DupePlus <aqua>Bug Tracker</aqua></green>\n" +
            "<gray>├</gray> <click:open_url:'https://github.com/meme20200/dupeplus'>https://github.com/meme20200/dupeplus</click>\n" +
            "<gray>└</gray> <click:open_url:'https://discord.gg/hdMVQJ3XyN'>https://discord.gg/hdMVQJ3XyN</click>\n" +
            "\n" +
            "<white>You can help the plugin become better for you and other server owners by reporting bugs or requesting new features.</white> \n" +
            "<st><gray>                          </gray></st>");
    public static final @NotNull Component docsCommandMessage = MiniMessage.miniMessage().deserialize("<st><gray>                          </gray></st>\n" +
            "<green>DupePlus <aqua>Docs</aqua></green>\n" +
            "<gray>└</gray> <click:open_url:'https://galaxyy.gitbook.io/dupeplus'>https://galaxyy.gitbook.io/dupeplus</click>\n" +
            "\n" +
            "<white>You can use the docs to help you in customizing DupePlus to your preferences.</white> \n" +
            "<st><gray>                          </gray></st>");
}
