package ph.adamw.electrolode;

public enum EnumPurifiableMineral {
    CARBON("carbon", 0, "Coal"),
    IRON("iron", 1, "Iron"),
    GOLD("gold", 2, "Gold"),
    MITHRIL("titanium", 3, "Titanium");

    private String identifier;
    private int ordinal;
    private String oreDictSuffix;

    EnumPurifiableMineral(String i, int ord, String x) {
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

    public static EnumPurifiableMineral find(String s) {
        for(EnumPurifiableMineral r : values()) {
            if(r.identifier.equalsIgnoreCase(s)) return r;
        }

        return null;
    }
}
