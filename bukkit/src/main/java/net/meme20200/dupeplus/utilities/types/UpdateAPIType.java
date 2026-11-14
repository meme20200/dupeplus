package net.meme20200.dupeplus.utilities.types;

public enum UpdateAPIType {
    Modrinth("Modrinth"),
    SpigotMC("SpigotMC");

    private final String name;

    UpdateAPIType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static UpdateAPIType match(String mode) {
        if (mode == null) return Modrinth;
        if (mode.equalsIgnoreCase("spigotmc")) return SpigotMC;
        return Modrinth;
    }
}
