package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.shared.Obj2IntMap;

import java.util.Objects;

public final class Multiplier {
    private boolean enabled;
    private int min;
    private boolean oneTimeMessage;
    private boolean permission;
    private final Obj2IntMap<String> maxValues;

    public Multiplier(boolean enabled, int min, boolean oneTimeMessage, boolean permission, Obj2IntMap<String> maxValues) {
        this.enabled = enabled;
        this.min = min;
        this.oneTimeMessage = oneTimeMessage;
        this.permission = permission;
        this.maxValues = maxValues;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int min() {
        return min;
    }

    public void min(int min) {
        this.min = min;
    }

    public boolean oneTimeMessage() {
        return oneTimeMessage;
    }

    public void oneTimeMessage(boolean oneTimeMessage) {
        this.oneTimeMessage = oneTimeMessage;
    }

    public boolean permission() {
        return permission;
    }

    public void permission(boolean permissionEnabled) {
        permission = permissionEnabled;
    }

    public Obj2IntMap<String> maxValues() {
        return maxValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Multiplier) obj;
        return this.enabled == that.enabled &&
                this.min == that.min &&
                this.oneTimeMessage == that.oneTimeMessage &&
                this.maxValues == that.maxValues &&
                this.permission == that.permission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, min, oneTimeMessage, permission, maxValues);
    }

    @Override
    public String toString() {
        return "Multiplier[" +
                "enabled=" + enabled + ", " +
                "min=" + min + ", " +
                "oneTimeMessage=" + oneTimeMessage + ", " +
                "maxValues=" + maxValues + ", " +
                "permission=" + permission + ']';
    }
}
