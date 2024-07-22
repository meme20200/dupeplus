package com.galaxydevnetwork.Bukkit.TabCompletors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitDupePlusCommandTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> a = new ArrayList<>();

        if (commandSender.hasPermission("dupeplus.admin.reloadconfig")) {
            a.add("reload");
        } else if (commandSender.hasPermission("dupeplus.admin.blacklist")) {
            if (strings.length == 1) {
                a.add("blacklist");
            } else if (strings.length == 2 && strings[0].equalsIgnoreCase("blacklist")) {
                a = Arrays.stream(Material.values())
                        .map(Material::name)
                        .collect(Collectors.toList());
            }

        }
        // Autocomplete the whole minecraft items
        return a;
    }
}
