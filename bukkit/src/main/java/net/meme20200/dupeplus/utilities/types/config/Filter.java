package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.dupeplus.utilities.types.ModeType;

import java.util.Objects;

public final class Filter {
    private final boolean enabled;
    private ModeType modeType;
    private final boolean shulkerBox;
    private final boolean bundle;

    public Filter(boolean enabled, ModeType modeType, boolean shulkerBox, boolean bundle) {
        this.enabled = enabled;
        this.modeType = modeType;
        this.shulkerBox = shulkerBox;
        this.bundle = bundle;
    }

    public boolean enabled() {
        return enabled;
    }

    public ModeType modeType() {
        return modeType;
    }

    public boolean shulkerBox() {
        return shulkerBox;
    }

    public boolean bundle() {
        return bundle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Filter) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.modeType, that.modeType) &&
                this.shulkerBox == that.shulkerBox &&
                this.bundle == that.bundle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, modeType, shulkerBox, bundle);
    }

    @Override
    public String toString() {
        return "Filter[" +
                "enabled=" + enabled + ", " +
                "modeType=" + modeType + ", " +
                "shulkerBox=" + shulkerBox + ", " +
                "bundle=" + bundle + ']';
    }

    public void modeType(ModeType modeType) {
        this.modeType = modeType;
    }
}
