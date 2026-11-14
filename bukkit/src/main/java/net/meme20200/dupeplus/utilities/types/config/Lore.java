package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.dupeplus.utilities.types.LoreMode;

import java.util.Objects;

public final class Lore {
    private boolean enabled;
    private String text;
    private LoreMode mode;
    private boolean permission;

    public Lore(boolean enabled, String text, LoreMode mode, boolean permission) {
        this.enabled = enabled;
        this.text = text;
        this.mode = mode;
        this.permission = permission;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String text() {
        return text;
    }

    public void text(String text) {
        this.text = text;
    }


    public LoreMode mode() {
        return mode;
    }

    public void mode(LoreMode mode) {
        this.mode = mode;
    }

    public boolean isPermissionEnabled() {
        return permission;
    }
    public void setPermissionEnabled(boolean option) {
        permission = option;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Lore) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.text, that.text) &&
                Objects.equals(this.mode, that.mode) &&
                this.permission == that.permission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, text, mode, permission);
    }

    @Override
    public String toString() {
        return "Lore[" +
                "enabled=" + enabled + ", " +
                "text=" + text + ", " +
                "mode=" + mode + ", " +
                "permission=" + permission + ']';
    }
}
