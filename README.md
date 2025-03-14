![DupePlus Icon](https://cdn.modrinth.com/data/cached_images/c37c728a99ba59a425ff67a8992354ca5368f1d2.png)
# DupePlus

The best Dupe Plugin for your Dupe Server, it provides you with a user-friendly configuration with comments that help you understand what things mean in the YML, it will also make your survival life easier just by duplicating. Just send in chat "/dupe"!

## Setup Guide

### Installation
Download the plugin from the First Version in the Featured Versions or from the Versions Tab, next click Download and upload it to the `/plugins` folder in your Minecraft Server Files.

### Configuration
The config file will be at the `/plugins/DupePlus` folder in your Minecraft Server Files.

Previous config versions will be renamed to old.config.yml or deleted.

If you ever manage to break the `config.yml` or accidentally emptied the config.yml, then here is a copy of it.
<details>
<summary>config.yml</summary>

```yml
# DupePlus's Settings
# Message Variables
# %item_type% = The Duplicated item
# %item_name% = The Item's Display Name
# %old_item_count% = The Item Count before dupe
# %new_item_count% = The Item Count after dupe
# %item_count% = Item Count
# %prefix% = The Default prefix or the prefix you set
# %max% = The maximum
# %min% = The minimum

# Use this website to help you customize your messages
# https://webui.advntr.dev
dupe:
  # The prefix for you to use
  prefix: "<green>DupePlus</green>"

  # Make it false to let everyone allowed to use this command (Default: false)
  # Permission: dupeplus.dupe
  permission: false


  # The message sent to the player when try to execute dupe or reloadconfig no permission (Only if the above is true) (Default: "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>")
  # Leave blank for no message
  permission-message: "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>"

  # The message sent to the player when you duplicate an item. (Default: "%prefix% <dark_gray>|</dark_gray> <gray>Duped %item_name%</gray>")
  # Allowed Message Variables: %prefix%, %item_type%, %item_name%, %old_item_count%, %new_item_count%
  message: "%prefix% <dark_gray>|</dark_gray> <gray>Duped %item_name%</gray>"

  # The message sent whenever the player tries to dupe nothing (Air) (Default: "")
  # Leave blank for no message
  duping-nothing-message: ""

  # Checks if the item has a NBT Tag of "dupenotallowed", if it has, it will not dupe the item.
  custom-nbt-item:
    # Make it false to disable the Custom NBT feature, make it true to enable it (Default: true)
    enabled: true

    # Allowed message variable: %prefix%, %item_type%, %item_name%, %item_count% (Default: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>")
    # Leave blank for no message
    blocked-message: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>"


  # When using /dupe, in which hand will it dupe? (Default: MainHand)
  # OffHand
  # MainHand
  dupe-on: MainHand

  # Message to the sender if they try to dupe as a non-player (Default: "DupePlus | You can't do that!")
  console-message: "DupePlus | You can't do that!"

  # Settings for blacklist
  blacklist:
    # Make it false to disable the blacklist, make it true to enable it (Default: false)
    enabled: false
    # If the player tries to dupe an item that is in (Default: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>")
    # Allowed message variable: %prefix%, %item_type%, %item_name%, %item_count%
    # Leave blank for no message
    blocked-message: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>"
    # Add or remove items to block them from being duped, also do not include spaces. (NO: "diamond block" YES: "diamond_block")

    # Begin the item's id with minecraft: to check the item's type only, it will ignore if the item is part of a custom plugin. (Default: - minecraft:barrier)
    # Begin the item's id with itemsadder: if you want to check if the item's itemsadder id, it will not check for the namespace.
    items:
      - minecraft:barrier

  # Settings for the cooldown of /dupe
  cooldown:
    # Make it false to disable the cooldown, make it true to enable it. (Default: false)
    enabled: false

    # How many seconds for the cooldowns to be. (Default: 3)
    seconds: 3

    # If the player tries to dupe while the cooldown is set. (Default: "%prefix% <dark_gray>|</dark_gray> <red>Please wait %duration%.</red>")
    # Allowed message variable: %prefix%, %duration%
    # Leave blank for no message
    wait-message: "%prefix% <dark_gray>|</dark_gray> <red>Please wait %duration%.</red>"

    # Make it true if you want players with the permission to ignore the cooldown, make it false if you want everyone to have cooldown. (Default: false)
    # Permission: dupeplus.cooldown
    permission: true


  # Settings for this (ignore if you don't want this) players can still run /dupe
  # /dupe <times>
  times:
    # Make it false to disable this feature, make it true to enable this feature (Default: false)
    enabled: false

    # Set it to 0 to disable the times  (Default: 5) (Recommended: 5)
    # Permission to let a player/group have no maximum regardless of this option: dupeplus.times.max.unlimited
    # dupeplus.times.max.<name>
    max-values:
      pro: 10
      vip: 8
      Default: 5 # Default permission or players without the permission who are using the dupe times.

    # If the player puts the value above the maximum then what's the message? (Default: "%prefix% <dark_gray>|</dark_gray> <red>This is higher than maximum! Do something lower than %max%</red>")
    # Allowed message variable: %prefix%, %max%, %min%
    # Leave blank for no message
    max-message: "%prefix% <dark_gray>|</dark_gray> <red>This is higher than maximum! Do something lower than %max%</red>"

    # 0 for no minimum amount of times the player can run (Default: 0) (Recommended: 0)
    # Permission to let a player/group have no minimum regardless of this option: dupeplus.times.min.unlimited
    # It is recommended to just keep this at 0
    min: 0

    # If the player puts the value below the minimum (Default: "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>")
    # Allowed message variable: %prefix%, %max%, %min%
    # Leave blank for no message
    min-message: "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>"

    # Instead of sending the dupe message for every time it duplicates, it sends it one time to stop spam. (Default: true)
    one-time-message: true

    # Make it false to let everyone allowed to use this command (Default: false)
    # Permission: dupeplus.times
    permission: false

  # Adds lore to the duplicated item.
  lore:
    # Enables or disables this feature. (Default: false)
    enabled: false

    # The lore added to the duplicated item. (Default: "<dark_red>*</dark_red> <red>Duplicated Item</red>")
    text: "<dark_red>*</dark_red> <red>Duplicated Item</red>"

    # The modes on how the plugin will add the lore. (Default: set)
    # append: Checks if the dupe lore exists or not, if it doesn't exist then it adds after the last lore or just adds the lore if no lore is present.
    # set: Checks if the dupe lore exists or not, if it doesn't exist then it will replace the existing lore with the dupe lore, otherwise it just ignores. (Recommended)
    mode: set

# The Integrations that this plugin connects with.
integrations:
  # Enables ItemsAdder Integration (Default: true)
  itemsadder: true

# Checks for updates using the set API (Modrinth or SpigotMC),
updates:
  # Checks on startup for updates, disable if you do not wish to update DupePlus.
  checkupdate: true

  # Which Service should the Check Update use? (Default: Modrinth)
  # Modrinth
  # SpigotMC
  api: Modrinth

  notify:
    # Notify players with the permission to update the plugin to the latest version.
    player-notify: true
    # The message sent to everyone who has the permission (Default: "%prefix% <dark_gray>|</dark_gray> <white><green>DupePlus</green> is outdated, please update at: <blue>%link%</blue>")
    # (Permission: dupeplus.updates.notify)
    # Allowed message variable: %prefix%, %link%, %currentversion%, %newversion%
    notify-message: "%prefix% <dark_gray>|</dark_gray> <white><green>DupePlus</green> is outdated, please update at: <blue>%link%</blue>"

    # Send message to the console for the server owner to update the plugin.
    console-notify: true

    # The message sent to the console (Default: "%prefix% | Update DupePlus at %link%")
    # Allowed message variable: %prefix%, %link%, %currentversion%, %newversion%
    console-notify-message: "%prefix% | Update DupePlus at %link%"

# Don't edit this
# Previous: 1.2
# config-version: 1.3
config-version: 1.3
```
</details>

## Custom NBT

You can make items undupeable by adding a custom NBT data "dupenotallowed", set it to true and the item will be undupeable.
You have to enable the custom-nbt-item in the configuration to use this.

### 1.20.5 and above
In the new change, Minecraft changed NBTs into components, and they also changed how custom tag works, it has to be in the custom_data. 

Example:
```
/give @p minecraft:diamond_sword[minecraft:custom_data={dupenotallowed:true}]
```

The command gives you a diamond sword with the undupeable tag in the Component format.

### 1.20.4 and below
Instead of using components, you have to use NBT which is JSON formatted, instead of including the custom tag into the custom_data, you don't have to put it anywhere.

```
/give @P minecraft:diamond_sword{dupenotallowed:true}
```
The command gives you a diamond sword with the undupeable tag in the NBT format.

## Features
- Lore [Add Lore to a duplicated item, don't worry this won't affect the original item]
- Times Max Value (Times) [Give certain groups or players a specific maximum time to duplicate an item.]
- ItemsAdder Support (Blacklist) [Don't be limited to only blacklisting minecraft items, you can blacklist itemsadder items by their IDs, like itemsadder:op_weapon]
- Custom NBT [Another way of blocking item, it's easy, just add {"dupenotallowed": true} to the minecraft:custom_data for 1.20.5 and above, or as its own tag for 1.20.4 and below]
- Update Checker [Don't be living under a rock, new features that improve the Dupe System in your server]
- Blacklist [Block items from being duped]
- Times Support [Dupe the item several times.]
- Dupe Hand [Change the item being duped, Offhand dupe or Main hand dupe]
- Permission Dupe [Give certain groups or players /dupe by using a permission plugin like [LuckPerms](https://luckperms.net/)]
- Message customization [Customise the messages sent by the plugin, it's really useful for Servers that use for an example Spanish as their language instead of English]
 

> Note: If you want a feature added, please request it in the Issues tab.


## Stats
[![DupePlus Statistics](https://bstats.org/signatures/bukkit/dupeplus.svg)](https://bstats.org/plugin/bukkit/DupePlus/18772)

### Symbols:
Server Software this plugin doesn't work on: Not bold

Server Software this plugin will support in the future: Not bold & #

Server Software this plugin supports but isn't recommended: Bold & *

Server Software this plugin supports: Bold

Server Software this plugin supports and recommend to use: Bold & ^

### Server Software
- **CraftBukkit***
- **Bukkit***
- **SpigotMC**
- **PaperMC^**
- SpongeMC#
- SpongeForge#
- Fabric#
- Forge#

## Upcoming 1.3.1
- [ ] Support PlaceholderAPI
- [ ] 
> Note: Please request features in the Issues Tab 
