package ph.adamw.electrolode.item.core;

public enum EnumPlate {
    CONDUCTIVE("conductive"),
    STRUCTURAL("structural"),
    STRENGTHENED("strengthened");

    private String identifier;

    EnumPlate(String i) {
        identifier = i;
    }

    public String getName() {
        return this.identifier;
    }
}
