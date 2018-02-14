package ph.adamw.electrolode.item.core;

public enum EnumPlate {
    CONDUCTIVE("conductive", 0),
    STRUCTURAL("structural", 1),
    STRENGTHENED("strengthened", 2);

    private String identifier;
    private int ordinal;

    EnumPlate(String i, int ord) {
        identifier = i;
        ordinal = ord;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return this.identifier;
    }
}
