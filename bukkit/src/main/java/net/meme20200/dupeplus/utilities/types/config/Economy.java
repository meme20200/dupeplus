package net.meme20200.dupeplus.utilities.types.config;

import java.util.Objects;

public final class Economy {
    private boolean enabled;
    private boolean chargePerDupe;
    private double price;
    private boolean isPermissionEnabled;

    public Economy(boolean enabled, boolean chargePerDupe, double price, boolean isPermissionEnabled) {
        this.enabled = enabled;
        this.chargePerDupe = chargePerDupe;
        this.price = price;
        this.isPermissionEnabled = isPermissionEnabled;
    }

    public boolean enabled() {
        return enabled;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean chargePerDupe() {
        return chargePerDupe;
    }

    public void chargePerDupe(boolean chargePerDupe) {
        this.chargePerDupe = chargePerDupe;
    }

    public double price() {
        return price;
    }

    public void price(double price) {
        this.price = price;
    }

    public boolean isPermissionEnabled() {
        return isPermissionEnabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Economy) obj;
        return this.enabled == that.enabled &&
                this.chargePerDupe == that.chargePerDupe &&
                Double.doubleToLongBits(this.price) == Double.doubleToLongBits(that.price) &&
                this.isPermissionEnabled == that.isPermissionEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, chargePerDupe, price, isPermissionEnabled);
    }

    @Override
    public String toString() {
        return "Economy[" +
                "enabled=" + enabled + ", " +
                "chargePerDupe=" + chargePerDupe + ", " +
                "price=" + price + ", " +
                "isPermissionEnabled=" + isPermissionEnabled + ']';
    }

    public void setPermissionEnabled(boolean enabled) {
        this.isPermissionEnabled = enabled;
    }
}
