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
# The command's settings
# Message Variables
# %dupe_item% = The Duplicated item
# %prefix% = The default prefix or the prefix you set
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
  permission-message: "%prefix% <dark_gray>|</dark_gray> <red>You are not allowed to use this command</red>"

  # Leave blank for no message (Default: "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>")
  # Allowed Message Variables: %prefix%, %dupe_item%
  message: "%prefix% <dark_gray>|</dark_gray> <gray>Duped %dupe_item%</gray>"

  # The message sent whenever the player tries to dupe nothing (Air) (Default: "")
  # Leave blank for no message
  duping-nothing-message: ""

  # When using /dupe, in which hand will it dupe? (Default: MainHand)
  # OffHand
  # MainHand
  dupe-on: MainHand

  # Message to the sender if they try to dupe as a non-player (Default: "DupePlus | You can't do that!")
  console-message: "DupePlus | You can't do that!"

  blacklist:
    # Make it false to disable this feature, make it true to enable this feature (Default: false)
    enabled: false
    # If the player tries to dupe an item that is in (Default: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>")
    # Allowed message variable: %prefix%, %dupe_item%
    blocked-message: "%prefix% <dark_gray>|</dark_gray> <red>The item is blocked from being duped!</red>"
    # Add or remove items to block them from being duped, also do not include spaces, for an example (NO: "diamond block" YES: "diamond_block")
    items:
      - barrier

  # Settings for this (ignore if you don't want this) players can still run /dupe
  # /dupe <times>
  times:
    # Make it false to disable this feature, make it true to enable this feature (Default: false)
    enabled: false

    # 0 for no maximum amount of times the player can run (Default: 5) (Recommended: 5)
    # Permission to let a player/group have no maximum regardless of this option: dupeplus.times.max.unlimited
    max: 5

    # If the player puts the value above the maximum then what's the message? (Default: "%prefix% <dark_gray>|</dark_gray> <red>This is higher than maximum! Do something lower than %max%</red>")
    # Allowed message variable: %prefix%, %max%, %min%
    max-message: "%prefix% <dark_gray>|</dark_gray> <red>This is higher than maximum! Do something lower than %max%</red>"

    # 0 for no minimum amount of times the player can run (Default: 0) (Recommended: 0)
    # Permission to let a player/group have no minimum regardless of this option: dupeplus.times.min.unlimited
    min: 0

    # If the player puts the value below the minimum (Default: "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>")
    # Allowed message variable: %prefix%, %max%, %min%
    min-message: "%prefix% <dark_gray>|</dark_gray> <red>This is lower than minimum! Do something higher than %min%</red>"

# Don't edit this
# Previous: 1.0
# config-version: 1.1
config-version: 1.1
```
</details>

## Features
- (NEW) Blacklist [Block items from being duped]
- Times Support [Dupe the item several times.]
- Dupe Hand [Change the item being duped, Off hand dupe or Main hand dupe]
- Permission Dupe [Give certain groups or players /dupe by using a permission plugin like [LuckPerms](https://luckperms.net/)]
- Message customization [Customise the messages sent by the plugin, it's really useful for Servers that use for an example Spanish as their language instead of English]
 

> Note: If you want a feature to be added, then please request the feature in the Github Repository Issues.


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
