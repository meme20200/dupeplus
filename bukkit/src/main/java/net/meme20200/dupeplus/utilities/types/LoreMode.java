package net.meme20200.dupeplus.utilities.types;

public enum LoreMode {
    SET,
    APPEND;

    public static LoreMode match(String mode) {
        if (mode == null) return SET;
        if (mode.equalsIgnoreCase("append")) return APPEND;
        return SET;
    }
}
