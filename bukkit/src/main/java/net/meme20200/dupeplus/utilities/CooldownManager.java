package net.meme20200.dupeplus.utilities;

import net.meme20200.dupeplus.BukkitDupePlus;
import net.meme20200.shared.Obj2LongMap;

import java.time.Duration;
import java.util.UUID;

public class CooldownManager {
    /**
     * The cooldown map
     */
    private final Obj2LongMap<UUID> cooldowns;

    public CooldownManager(BukkitDupePlus plugin) {
        cooldowns = UtilAPIs.createObj2LongMap();
        cooldowns.defaultReturnValue(-1L);
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::cleanExpiredCooldowns, 20L, 6000L);
    }

    /**
     * Sets a cooldown to the player
     *
     * @param key      The player's UUID {@link org.bukkit.entity.Player#getUniqueId() Player.getUniqueId()}
     * @param duration The cooldown that will affect the player
     */
    public void setCooldown(UUID key, Duration duration) {
        cooldowns.put(key, System.currentTimeMillis() + duration.toMillis());
    }

    /**
     * Checks if the player is in cooldown
     *
     * @param key The player's UUID {@link org.bukkit.entity.Player#getUniqueId() Player.getUniqueId()}
     * @return True if the player has a cooldown.
     */
    public boolean hasCooldown(UUID key) {
        long expireTime = cooldowns.get(key);
        if (expireTime == -1L) return false;

        if (System.currentTimeMillis() >= expireTime) {
            cooldowns.remove(key);
            return false;
        }
        return true;
    }

    /**
     * Removes the cooldown
     *
     * @param key The player's UUID {@link org.bukkit.entity.Player#getUniqueId() Player.getUniqueId()}
     * @return The original value or 0 (you can ignore the return)
     */
    public long removeCooldown(UUID key) {
        return cooldowns.remove(key);
    }


    /**
     * Retrieves the remaining cooldown time
     *
     * @param key The player's UUID {@link org.bukkit.entity.Player#getUniqueId() Player.getUniqueId()}
     * @return The remaining cooldown time if the player has a cooldown. Otherwise, a duration of zero.
     */
    public Duration getRemainingCooldown(UUID key) {
        long cooldown = cooldowns.get(key);
        long timeLeft = cooldown - System.currentTimeMillis();
        if (cooldown == -1L || timeLeft <= 0L) return Duration.ZERO;
        return Duration.ofMillis(timeLeft);
    }

    /**
     * Clears out any cooldown to prevent any memory leaks with the cooldown.
     */
    private void cleanExpiredCooldowns() {
        final long now = System.currentTimeMillis();
        if (cooldowns.isEmpty()) return;
        cooldowns.removeIf((key, value) -> value <= now);
    }
}