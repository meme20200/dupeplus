package net.meme20200.dupeplus.utilities.types.config;

import java.util.Objects;

public final class CustomCommand {
    private boolean enabled;
    private String name;

    public CustomCommand(boolean enabled, String name) {
        this.enabled = enabled;
        this.name = name;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CustomCommand) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, name);
    }

    @Override
    public String toString() {
        return "CustomCommand[" +
                "enabled=" + enabled + ", " +
                "name=" + name + ']';
    }
}
