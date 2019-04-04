package ph.adamw.electrolode.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.tile.machine.core.TileInventoriedMachine;

public abstract class ElectrolodeContainer extends Container {
	public TileInventoriedMachine tileEntity;
	private int TOTAL_PLAYER_INVENTORY_SIZE = 36;

	public ElectrolodeContainer(IInventory playerInventory, TileInventoriedMachine te) {
		this.tileEntity = te;

		addPlayerSlots(playerInventory);
		final IItemHandler tileItemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addOwnSlots(tileItemHandler);
	}

	/**
	 * Edited transferStackInSlot implementation to check that item is valid for the slot.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
		Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		//noinspection ConstantConditions
		if (sourceSlotIndex >= 0 && sourceSlotIndex < TOTAL_PLAYER_INVENTORY_SIZE) {
			if (!mergeItemStack(sourceStack, TOTAL_PLAYER_INVENTORY_SIZE, TOTAL_PLAYER_INVENTORY_SIZE + tileEntity.getCombinedSlots(), false)){
				return ItemStack.EMPTY;
			}
		} else if (sourceSlotIndex >= TOTAL_PLAYER_INVENTORY_SIZE && sourceSlotIndex < TOTAL_PLAYER_INVENTORY_SIZE + tileEntity.getCombinedSlots()) {
			if (!mergeItemStack(sourceStack, 0, TOTAL_PLAYER_INVENTORY_SIZE, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}

		// If stack size == 0 (the entire stack was moved) set slot contents to empty
		if (sourceStack.getCount() == 0) {
			sourceSlot.putStack(ItemStack.EMPTY);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onTake(player, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		boolean flag = false;
		int i = startIndex;

		if (reverseDirection) {
			i = endIndex - 1;
		}

		if (stack.isStackable()) {
			while (!stack.isEmpty()) {
				if (reverseDirection) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				Slot slot = this.inventorySlots.get(i);
				ItemStack itemstack = slot.getStack();

				if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack) && slot.isItemValid(stack)) {
					int j = itemstack.getCount() + stack.getCount();
					int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

					if (j <= maxSize) {
						stack.setCount(0);
						itemstack.setCount(j);
						slot.onSlotChanged();
						flag = true;
					} else if (itemstack.getCount() < maxSize) {
						stack.shrink(maxSize - itemstack.getCount());
						itemstack.setCount(maxSize);
						slot.onSlotChanged();
						flag = true;
					}
				}

				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}

		if (!stack.isEmpty()) {
			if (reverseDirection) {
				i = endIndex - 1;
			} else {
				i = startIndex;
			}

			while (true) {
				if (reverseDirection) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				Slot slot = this.inventorySlots.get(i);
				ItemStack itemstack1 = slot.getStack();

				if (itemstack1.isEmpty() && slot.isItemValid(stack)) {
					if (stack.getCount() > slot.getSlotStackLimit()) {
						slot.putStack(stack.splitStack(slot.getSlotStackLimit()));
					} else {
						slot.putStack(stack.splitStack(stack.getCount()));
					}

					slot.onSlotChanged();
					flag = true;
					break;
				}

				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}

		return flag;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileEntity.canInteractWith(playerIn);
	}

	public abstract void addOwnSlots(IItemHandler itemHandler);

	/**
	 * Default player inventory slot assigning - 27 and 9 for the hotbar.
	 *  - Override if not using the standard 165x175 machine GUI
	 *  - Reflect this change by reassigning TOTAL_PLAYER_INVENTORY_SIZE
	 */
	public void addPlayerSlots(IInventory playerInventory) {
		// Slots for hotbar
		for (int row = 0; row < 9; row ++) {
			int x = 8 + row * 18;
			int y = 141;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}

		// Slots for main inv
		for (int row = 0; row < 3; row ++) {
			for (int col = 0; col < 9; col ++) {
				int x = 8 + col * 18;
				int y = row * 18 + 84;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
			}
		}
	}
}
