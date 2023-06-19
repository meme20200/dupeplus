package com.galaxydevnetwork.Bukkit.TabCompletors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BukkitDupePlusCommandTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> a = new ArrayList<>();
        if (commandSender.hasPermission("dupeplus.admin.reloadconfig")) {
            a.add("reload");
        }
        return a;
    }
}
