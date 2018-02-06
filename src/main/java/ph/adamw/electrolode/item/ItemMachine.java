package ph.adamw.electrolode.item;

import ph.adamw.electrolode.block.machine.BlockBaseMachine;

public class ItemMachine extends ItemBlockDescribed {
    public ItemMachine(BlockBaseMachine e) {
        super(e);
        setMaxStackSize(1);
    }

}
