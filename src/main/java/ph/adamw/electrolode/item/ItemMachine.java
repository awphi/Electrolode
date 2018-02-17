package ph.adamw.electrolode.item;

import ph.adamw.electrolode.block.machine.BlockBaseMachine;
import ph.adamw.electrolode.item.core.ItemBlockDescribed;

public class ItemMachine extends ItemBlockDescribed {
    public ItemMachine(BlockBaseMachine e) {
        super(e);
        setMaxStackSize(1);
    }

}
