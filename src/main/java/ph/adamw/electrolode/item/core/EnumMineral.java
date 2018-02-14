package ph.adamw.electrolode.item.core;

public enum EnumMineral {
    CARBON("carbon", 0, "Coal"),
    IRON("iron", 1, "Iron"),
    GOLD("gold", 2, "Gold"),
    TITANIUM("titanium", 3, "Titanium");

    private String identifier;
    private int ordinal;
    private String oreDictSuffix;

    EnumMineral(String i, int ord, String x) {
        identifier = i;
        ordinal = ord;
        oreDictSuffix = x;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return this.identifier;
    }

    public String getOreDictSuffix() {
        return this.oreDictSuffix;
    }
}
