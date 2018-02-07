package ph.adamw.electrolode.item.tool;

import ph.adamw.electrolode.item.ItemBase;

public class ItemPestleMortar extends ItemBase {
    public ItemPestleMortar() {
        super( 1,true);
        setContainerItem(this);
    }

    public String getItemName() {
        return "pestlemortar";
    }
}
