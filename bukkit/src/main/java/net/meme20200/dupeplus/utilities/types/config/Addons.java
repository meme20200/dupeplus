package net.meme20200.dupeplus.utilities.types.config;

import java.util.Objects;

public final class Addons {
    private boolean isPlaceholderAPIEnabled;
    private final boolean isPlaceholderAPIInstalled;
    private boolean isVaultEnabled;
    private final boolean isVaultInstalled;

    public Addons(boolean isPlaceholderAPIEnabled, boolean isPlaceholderAPIInstalled, boolean isVaultEnabled, boolean isVaultInstalled) {
        this.isPlaceholderAPIEnabled = isPlaceholderAPIEnabled;
        this.isPlaceholderAPIInstalled = isPlaceholderAPIInstalled;
        this.isVaultEnabled = isVaultEnabled;
        this.isVaultInstalled = isVaultInstalled;
    }

    public boolean isPlaceholderAPIEnabled() {
        return isPlaceholderAPIEnabled;
    }

    public boolean isPlaceholderAPIInstalled() {
        return isPlaceholderAPIInstalled;
    }

    public boolean isVaultEnabled() {
        return isVaultEnabled;
    }

    public boolean isVaultInstalled() {
        return isVaultInstalled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Addons) obj;
        return this.isPlaceholderAPIEnabled == that.isPlaceholderAPIEnabled &&
                this.isPlaceholderAPIInstalled == that.isPlaceholderAPIInstalled &&
                this.isVaultEnabled == that.isVaultEnabled &&
                this.isVaultInstalled == that.isVaultInstalled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPlaceholderAPIEnabled, isPlaceholderAPIInstalled, isVaultEnabled, isVaultInstalled);
    }

    @Override
    public String toString() {
        return "Addons[" +
                "isPlaceholderAPIEnabled=" + isPlaceholderAPIEnabled + ", " +
                "isPlaceholderAPIInstalled=" + isPlaceholderAPIInstalled + ", " +
                "isVaultEnabled=" + isVaultEnabled + ", " +
                "isVaultInstalled=" + isVaultInstalled + ']';
    }

    public void setVaultEnabled(boolean enabled) {
        this.isVaultEnabled = enabled;
    }

    public void setPlaceholderAPIEnabled(boolean enabled) {
        this.isPlaceholderAPIEnabled = enabled;
    }
}
