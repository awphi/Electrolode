package ph.adamw.electrolode.item.core;

public enum EnumMineral {
    CARBON("carbon",  "Coal"),
    IRON("iron",  "Iron"),
    GOLD("gold",  "Gold"),
    TITANIUM("titanium",  "Titanium");

    private String identifier;
    private String oreDictSuffix;

    EnumMineral(String i, String x) {
        identifier = i;
        oreDictSuffix = x;
    }

    public String getName() {
        return this.identifier;
    }

    public String getOreDictSuffix() {
        return this.oreDictSuffix;
    }
}
