package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.dupeplus.utilities.types.ModeType;

import java.util.Objects;
import java.util.Set;

public final class Worlds {
    private boolean enabled;
    private ModeType modeType;
    private Set<String> worlds;

    public Worlds(boolean enabled, ModeType modeType, Set<String> worlds) {
        this.enabled = enabled;
        this.modeType = modeType;
        this.worlds = worlds;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ModeType modeType() {
        return modeType;
    }

    public void modeType(ModeType modeType) {
        this.modeType = modeType;
    }

    public Set<String> worlds() {
        return worlds;
    }

    public void worlds(Set<String> worlds) {
        this.worlds = worlds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Worlds) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.modeType, that.modeType) &&
                Objects.equals(this.worlds, that.worlds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, modeType, worlds);
    }

    @Override
    public String toString() {
        return "Worlds[" +
                "enabled=" + enabled + ", " +
                "modeType=" + modeType + ", " +
                "worlds=" + worlds + ']';
    }
}
