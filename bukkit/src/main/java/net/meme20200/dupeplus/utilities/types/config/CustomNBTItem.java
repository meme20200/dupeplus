package net.meme20200.dupeplus.utilities.types.config;

import java.util.Objects;

public final class CustomNBTItem {
    private boolean enabled;
    private String tag;

    public CustomNBTItem(boolean enabled, String tag) {
        this.enabled = enabled;
        this.tag = tag;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String tag() {
        return tag;
    }

    public void tag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CustomNBTItem) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, tag);
    }

    @Override
    public String toString() {
        return "CustomNBTItem[" +
                "enabled=" + enabled + ", " +
                "tag=" + tag + ']';
    }

}
