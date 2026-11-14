package net.meme20200.dupeplus.utilities.types.config;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

import java.util.Objects;

public final class SoundFeedback {
    private boolean enabled;
    private Sound.Source category;
    private Key sound;
    private float pitch;
    private float volume;
    private boolean permission;

    public SoundFeedback(boolean enabled, Sound.Source category, Key sound, float pitch, float volume, boolean permission) {
        this.enabled = enabled;
        this.category = category;
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
        this.permission = permission;
    }

    public boolean enabled() {
        return enabled;
    }

    public Sound.Source category() {
        return category;
    }

    public Key sound() {
        return sound;
    }

    public boolean isPermissionEnabled() {
        return permission;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void category(Sound.Source category) {
        this.category = category;
    }

    public void sound(Key sound) {
        this.sound = sound;
    }

    public void pitch(float pitch) {
        this.pitch = pitch;
    }

    public float pitch() {
        return pitch;
    }

    public float volume() {
        return volume;
    }

    public void volume(float volume) {
        this.volume = volume;
    }

    public void setPermissionEnabled(boolean permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SoundFeedback) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.category, that.category) &&
                Objects.equals(this.sound, that.sound) &&
                Float.floatToIntBits(this.pitch) == Float.floatToIntBits(that.pitch) &&
                Float.floatToIntBits(this.volume) == Float.floatToIntBits(that.volume) &&
                this.permission == that.permission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, category, sound, pitch, volume, permission);
    }

    @Override
    public String toString() {
        return "SoundFeedback[" +
                "enabled=" + enabled + ", " +
                "category=" + category + ", " +
                "sound=" + sound + ", " +
                "pitch=" + pitch + ", " +
                "volume=" + volume + ", " +
                "isPermissionEnabled=" + permission + ']';
    }
}
