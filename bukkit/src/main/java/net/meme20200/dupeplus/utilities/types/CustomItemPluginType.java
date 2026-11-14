package net.meme20200.dupeplus.utilities.types;


public enum CustomItemPluginType {
    ItemsAdder("itemsadder"),
    Oraxen("oraxen"),
    Nexo("nexo"),
    MinehutCosmetics("minehutcosmetics");
    private final String key;

    CustomItemPluginType(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
