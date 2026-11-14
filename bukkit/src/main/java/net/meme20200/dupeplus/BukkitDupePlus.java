package net.meme20200.dupeplus;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.translation.GlobalTranslator;
import net.meme20200.dupeplus.commands.*;
import net.meme20200.dupeplus.events.NotifyJoinPlayer;
import net.meme20200.dupeplus.utilities.CooldownManager;
import net.meme20200.dupeplus.utilities.UpdateChecker;
import net.meme20200.dupeplus.utilities.config.BukkitConfigManager;
import net.meme20200.dupeplus.utilities.config.BukkitConfigyml;
import net.meme20200.dupeplus.utilities.types.Translation;
import net.meme20200.dupeplus.utilities.types.UpdateAPIType;
import net.meme20200.modern.FastStats;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.exception.InvalidCommandSenderException;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.setting.ManagerSetting;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.util.Locale;

import static net.meme20200.dupeplus.utilities.UtilAPIs.*;

/**
 * The start of DupePlus.
 *
 * @since Jun 19, 2023
 */
public class BukkitDupePlus extends JavaPlugin {

    /**
     * The version will be replaced with the actual version. You don't have to change this version.
     */
    private String VERSION = "1.5";
    private BukkitAudiences adventure;
    private FastStats fastStats;

    private Economy econ;
    private Translation translation;

    private boolean isBundleSupported = false;

    //
    // MANAGERS
    //
    private LegacyPaperCommandManager<@NonNull CommandSender> manager;
    private CooldownManager cooldownManager;

    //
    // CONFIG
    //
    private BukkitConfigManager configManager;
    private BukkitConfigyml configyml;

    //
    // COMMANDS
    //
    private BukkitDupeFilterCommand dupeFilterCommand;
    private BukkitDupeUnblockCommand dupeUnblockCommand;
    private BukkitDupeBlockCommand dupeBlockCommand;
    private BukkitDupeCommand bukkitDupeCommand;
    private BukkitDupePlusCommand bukkitDupePlusCommand;


    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        if (isBelow(new int[]{1,13,2})) {
            getLogger().info("------------------------");
            getLogger().info("This Minecraft server version (" + Bukkit.getBukkitVersion() + ") isn't supported by DupePlus." );
            getLogger().info("Use the minimum supported version 1.13.2 or a stable version like 26.3" );
            getLogger().info("------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!classExists("it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap")) {
            getLogger().info("------------------------");
            getLogger().info("[Performance Notice] Fastutil libraries not found natively.");
            getLogger().info("This plugin will use fallback, but");
            getLogger().info("this runs best on modern platforms (e.g., Paper, Purpur)");
            getLogger().info("and/or later versions (e.g., 1.21.11, 26.3)");
            getLogger().info("as those include these performance collections.");
            getLogger().info("------------------------");
        }
        isBundleSupported = classExists("org.bukkit.inventory.meta.BundleMeta");

        configManager = new BukkitConfigManager(this);
        configManager.loadConfig();
        configyml = new BukkitConfigyml(this);

        this.adventure = BukkitAudiences.create(this);
        VERSION = this.getDescription().getVersion();

        cooldownManager = new CooldownManager(this);

        setupEconomy();
        initMetrics();

        manager = LegacyPaperCommandManager.createNative(this, ExecutionCoordinator.simpleCoordinator());

        if (manager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            manager.registerBrigadier();
        } else if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            manager.registerAsynchronousCompletions();
        }

        manager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true);
        manager.exceptionController().registerHandler(InvalidCommandSenderException.class, (exceptionContext) -> exceptionContext.context().sender().sendMessage(configManager.getConsoleMessage()));

        // Commands
        bukkitDupeCommand = new BukkitDupeCommand(this);
        bukkitDupeCommand.registerCommand();

        bukkitDupePlusCommand = new BukkitDupePlusCommand(this);
        dupeBlockCommand = new BukkitDupeBlockCommand(this);
        dupeUnblockCommand = new BukkitDupeUnblockCommand(this);
        dupeFilterCommand = new BukkitDupeFilterCommand(this);
        if (configManager.adminShorthandCommands()) {
            dupeBlockCommand.registerCommand();
            dupeUnblockCommand.registerCommand();
            dupeFilterCommand.registerCommand();
        }

        // Sets up the languages
        initLanguage();

        if (configManager.getUpdateChecker().enabled()) {
            new UpdateChecker(this, configManager.getUpdateChecker().apiType() == UpdateAPIType.SpigotMC).getVersion(newversion -> {
                if (newversion == null || this.VERSION.equals(newversion)) return;
                if (configManager.getUpdateChecker().playerNotify()) {
                    Bukkit.getServer().getPluginManager().registerEvents(new NotifyJoinPlayer(this, newversion), this);
                }
                if (configManager.getUpdateChecker().consoleNotify()) {
                    configyml.updateConsoleMessage(newversion, configManager.getUpdateChecker().consoleNotifyMessage());
                }
            });
        }
    }




    /**
     * Sets up the messages, and the {@link GlobalTranslator} for {@link net.kyori.adventure.text.Component#translatable() Component.translatable()}.
     */
    private void initLanguage() {
        translation = new Translation();

        File en_us_File = new File(getDataFolder(), "lang/en_us.yml");
        if (!en_us_File.exists()) saveResource("lang/en_us.yml", false);

        File langFolder = new File(getDataFolder(), "lang");
        File[] files = langFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (files != null) {
            for (File file : files) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                String[] fileLanguageName = file.getName().replace(".yml", "").split("_");
                Locale locale = (fileLanguageName.length == 2) ? new Locale.Builder().setLanguage(fileLanguageName[0]).setRegion(fileLanguageName[1]).build() : Locale.ENGLISH;
                for (String key : config.getKeys(true)) {
                    if (key.equals("version")) continue;
                    if (!config.isString(key)) continue;
                    String value = config.getString(key);
                    if (value != null) translation.register(key, locale, value);
                }
            }
        }
        GlobalTranslator.translator().addSource(translation);
    }

    @Override
    public void onDisable() {
        if (this.configManager != null) {
            this.configManager.saveConfig();
        }

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        if (this.fastStats != null) {
            this.fastStats.close();
        }
    }

    /**
     * Initializes the metrics, such as bStats, and FastStats
     */
    private void initMetrics() {
        Metrics metrics = new Metrics(this, 18772);
        metrics.addCustomChart(new SimplePie("configversion", () -> VERSION));

        // FastStats is built using Java bytecode 61 (Java 17)
        if (overJava17()) fastStats = new FastStats(this, VERSION);
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }


    /**
     * Used mainly for the economy feature {@code dupe.custom-command}
     *
     * @return Economy (VaultAPI)
     */
    @Nullable
    public Economy getEconomy() {
        return econ;
    }

    /**
     * Used primarily to register and unregister because of the {@code config.yml dupe.custom-command} feature.
     *
     * @return The Dupe Command class.
     */
    public BukkitDupeCommand getBukkitDupeCommand() {
        return bukkitDupeCommand;
    }

    /**
     * Used primarily for the shorthand commands like {@link BukkitDupeBlockCommand}, {@link BukkitDupeFilterCommand}, and {@link BukkitDupeUnblockCommand}.
     *
     * @return The DupePlus Command class.
     */
    public BukkitDupePlusCommand getBukkitDupePlusCommand() {
        return bukkitDupePlusCommand;
    }


    /**
     * @return The command manager (Incendo Cloud)
     */
    public LegacyPaperCommandManager<CommandSender> getCommandManager() {
        return manager;
    }


    /**
     * @return Returns the current plugin version installed.
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * @return The translation used by DupePlus, which is for the messages.
     */
    public Translation getTranslation() {
        return translation;
    }

    /**
     * @return The cooldown manager
     */
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    /**
     * @return Everything to do with the configuration; the config is cached here, and all config sections must be accessed here.
     */
    public BukkitConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * @return The old config. All deprecated functions in configyml delegate to {@link getConfigManager()}
     */
    public BukkitConfigyml getConfigyml() {
        return configyml;
    }

    public BukkitDupeFilterCommand dupeFilterCommand() {
        return dupeFilterCommand;
    }

    public BukkitDupeUnblockCommand dupeUnblockCommand() {
        return dupeUnblockCommand;
    }

    public BukkitDupeBlockCommand dupeBlockCommand() {
        return dupeBlockCommand;
    }

    public boolean isBundleSupported() {
        return isBundleSupported;
    }
}
