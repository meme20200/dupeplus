package net.meme20200.dupeplus.utilities.types;

public enum ModeType {
    BLACKLIST,
    WHITELIST;

    public static ModeType match(String mode) {
        if (mode == null) return BLACKLIST;
        if (mode.equalsIgnoreCase("whitelist")) return WHITELIST;
        return BLACKLIST;
    }
}
