package net.meme20200.dupeplus.menus.config.submenus;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.dupeplus.utilities.MenuUtils;
import net.meme20200.dupeplus.utilities.MessagesUtil;
import net.meme20200.dupeplus.utilities.UtilAPIs;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static net.meme20200.dupeplus.utilities.ItemUtils.generateItemStack;
import static net.meme20200.dupeplus.utilities.MenuUtils.*;

public class SoundFeedbackMenu {
    @SuppressWarnings("PatternValidation")
    public Gui getMenu(BukkitDupePlus plugin) {
        Gui gui = Gui.gui().title(Component.text("Sound Feedback Menu", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false)).rows(3).create();

        gui.setItem(0, MenuUtils.generateBooleanItem("Sound Feedback Option", "dupe.sound-feedback.enabled", "If the sound feedback feature should be enabled or not", plugin.getConfigManager().getSoundFeedback().enabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getSoundFeedback().enabled(!plugin.getConfigManager().getSoundFeedback().enabled());
            gui.updateItem(0, MenuUtils.generateBooleanItem("Sound Feedback Option", "dupe.sound-feedback.enabled", "If the sound feedback feature should be enabled or not", plugin.getConfigManager().getSoundFeedback().enabled()));
        }));

        gui.setItem(1, generateTextItem("Sound Category", "dupe.sound-feedback.category", "The sound category used to play the sound", plugin.getConfigManager().getSoundFeedback().category().name(), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput == null || userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                userInput = UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("").toLowerCase(Locale.ROOT);
                Sound.Source value = Sound.Source.NAMES.value(userInput);
                if (value == null) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                plugin.getConfigManager().getSoundFeedback().category(value);
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.CHEST, plugin.getConfigManager().getSoundFeedback().category().name(), MessagesUtil.soundFeedbackCategoryLore)).itemRight(generateItemStack(Material.PAPER, "Categories", MessagesUtil.soundFeedbackCategoryHelpLore)).title("Sound Category").text(plugin.getConfigManager().getSoundFeedback().category().name()).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        gui.setItem(2, generateTextItem("Played Sound", "dupe.sound-feedback.sound", "The sound played when a player does /dupe", plugin.getConfigManager().getSoundFeedback().sound().asString(), Material.JUKEBOX, event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput == null || userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                userInput = UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll("");
                try {
                    Key sound = Key.key(userInput);
                    plugin.getConfigManager().getSoundFeedback().sound(sound);
                } catch (InvalidKeyException e) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.JUKEBOX, plugin.getConfigManager().getSoundFeedback().sound().asString(), MessagesUtil.soundFeedbackSoundLore)).title("Played Sound").text(plugin.getConfigManager().getSoundFeedback().sound().asString()).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        gui.setItem(3, generateTextItem("Volume", "dupe.sound-feedback.volume", "The volume of the sound played", String.valueOf(plugin.getConfigManager().getSoundFeedback().volume()), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                try {
                    int seconds = Integer.parseInt(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll(""));
                    plugin.getConfigManager().getSoundFeedback().volume(seconds);
                } catch (NumberFormatException e) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.NAME_TAG, String.valueOf(plugin.getConfigManager().getSoundFeedback().volume()), MessagesUtil.soundFeedbackVolumeLore)).title("Sound Volume").text(String.valueOf(plugin.getConfigManager().getSoundFeedback().volume())).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        gui.setItem(4, generateTextItem("Pitch", "dupe.sound-feedback.pitch", "The pitch of the sound played", String.valueOf(plugin.getConfigManager().getSoundFeedback().pitch()), Material.NAME_TAG, event -> {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player)) return;
            @SuppressWarnings("resource") Audience audience = plugin.adventure().player((Player) event.getWhoClicked());
            new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {
                if (!(slot == AnvilGUI.Slot.OUTPUT)) return Collections.emptyList();
                String userInput = stateSnapshot.getText();
                if (userInput.isBlank()) return List.of(AnvilGUI.ResponseAction.close());
                try {
                    int seconds = Integer.parseInt(UtilAPIs.getWhitespacePattern().matcher(userInput).replaceAll(""));
                    plugin.getConfigManager().getSoundFeedback().pitch(seconds);
                } catch (NumberFormatException e) {
                    audience.playSound(Sound.sound(Key.key("minecraft:entity.villager.no"), Sound.Source.UI, 1.0f, 1.0f));
                    return Collections.emptyList();
                }
                return List.of(AnvilGUI.ResponseAction.close());
            }).itemLeft(generateItemStack(Material.NAME_TAG, String.valueOf(plugin.getConfigManager().getSoundFeedback().pitch()), MessagesUtil.soundFeedbackPitchLore)).title("Sound Volume").text(String.valueOf(plugin.getConfigManager().getSoundFeedback().pitch())).plugin(plugin).open((Player) event.getWhoClicked());
        }));

        gui.setItem(5, generateBooleanItem("Sound Feedback Permission", "dupe.sound-feedback.permission", "If the sound feedback feature should be behind a permission.", plugin.getConfigManager().getSoundFeedback().isPermissionEnabled(), event -> {
            event.setCancelled(true);
            plugin.getConfigManager().getSoundFeedback().setPermissionEnabled(!plugin.getConfigManager().getSoundFeedback().isPermissionEnabled());
            gui.updateItem(5, generateBooleanItem("Sound Feedback Permission", "dupe.sound-feedback.permission", "If the sound feedback feature should be behind a permission.", plugin.getConfigManager().getSoundFeedback().isPermissionEnabled()));
        }));
        mainTemplate(plugin, gui);
        return gui;
    }
}
