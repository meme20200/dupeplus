package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.dupeplus.utilities.types.CustomItemPluginType;
import org.bukkit.Material;

import java.util.Map;
import java.util.Set;

public interface ConfigData {
    Addons getAddons();

    boolean permission();
    void permission(boolean permission);

    CustomCommand getCustomCommand();

    Set<String> getAliases();

    Worlds getWorlds();

    CustomNBTItem getCustomNBTItem();

    String getDupeOn();

    String getConsoleMessage();

    Filter getFilter();

    Cooldown getCooldown();

    Multiplier getMultiplier();

    Lore getLore();

    SoundFeedback getSoundFeedback();

    Economy getEconomy();

    Map<CustomItemPluginType, Map<String, Set<String>>> getCustomFilter();

    Map<String, Set<String>> getCustomFilter(CustomItemPluginType customItemPluginType);

    Set<Material> getVanillaFilter();

    UpdateChecker getUpdateChecker();

    boolean adminShorthandCommands();
    void adminShorthandCommands(boolean enabled);
}