package net.meme20200.dupeplus.utilities.types.config;

import net.meme20200.dupeplus.utilities.types.UpdateAPIType;

import java.util.Objects;

public final class UpdateChecker {
    private boolean enabled;
    private UpdateAPIType apiType;
    private boolean playerNotify;
    private boolean consoleNotify;

    public void consoleNotify(boolean consoleNotify) {
        this.consoleNotify = consoleNotify;
    }

    public void playerNotify(boolean playerNotify) {
        this.playerNotify = playerNotify;
    }

    public void apiType(UpdateAPIType apiType) {
        this.apiType = apiType;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

    private final String consoleNotifyMessage;

    public UpdateChecker(boolean enabled, UpdateAPIType apiType, boolean playerNotify, boolean consoleNotify, String consoleNotifyMessage) {
        this.enabled = enabled;
        this.apiType = apiType;
        this.playerNotify = playerNotify;
        this.consoleNotify = consoleNotify;
        this.consoleNotifyMessage = consoleNotifyMessage;
    }

    public boolean enabled() {
        return enabled;
    }

    public UpdateAPIType apiType() {
        return apiType;
    }

    public boolean playerNotify() {
        return playerNotify;
    }

    public boolean consoleNotify() {
        return consoleNotify;
    }

    public String consoleNotifyMessage() {
        return consoleNotifyMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UpdateChecker) obj;
        return this.enabled == that.enabled &&
                Objects.equals(this.apiType, that.apiType) &&
                this.playerNotify == that.playerNotify &&
                this.consoleNotify == that.consoleNotify &&
                Objects.equals(this.consoleNotifyMessage, that.consoleNotifyMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, apiType, playerNotify, consoleNotify, consoleNotifyMessage);
    }

    @Override
    public String toString() {
        return "UpdateChecker[" +
                "enabled=" + enabled + ", " +
                "apiType=" + apiType + ", " +
                "playerNotify=" + playerNotify + ", " +
                "consoleNotify=" + consoleNotify + ", " +
                "consoleNotifyMessage=" + consoleNotifyMessage + ']';
    }
}
