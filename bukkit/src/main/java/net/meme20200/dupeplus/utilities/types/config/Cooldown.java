package net.meme20200.dupeplus.utilities.types.config;

import java.util.Objects;

public final class Cooldown {
    private boolean enabled;
    private long seconds;
    private boolean isPermissionEnabled;

    public Cooldown(boolean enabled, long seconds, boolean isPermissionEnabled) {
        this.enabled = enabled;
        this.seconds = seconds;
        this.isPermissionEnabled = isPermissionEnabled;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long seconds() {
        return seconds;
    }

    public void seconds(long seconds) {
        this.seconds = seconds;
    }

    public boolean isPermissionEnabled() {
        return isPermissionEnabled;
    }

    public void setPermissionEnabled(boolean permissionEnabled) {
        this.isPermissionEnabled = permissionEnabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Cooldown) obj;
        return this.enabled == that.enabled &&
                this.seconds == that.seconds &&
                this.isPermissionEnabled == that.isPermissionEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, seconds, isPermissionEnabled);
    }

    @Override
    public String toString() {
        return "Cooldown[" +
                "enabled=" + enabled + ", " +
                "seconds=" + seconds + ", " +
                "isPermissionEnabled=" + isPermissionEnabled + ']';
    }
}
