package com.Hmod.container;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.Hmod.HaryItems.HarysItems;
import com.Hmod.tile_entity.HaryEntity;
import com.Hmod.tile_entity.TileEntityFurnaceH;
import com.google.common.collect.Sets;

public class ContainerCustomFurnace extends Container {

	
	private TileEntityFurnaceH tileFurnace;

	public static final int GUI_FURNACE = 1;

	private int[] cachedFields;

	// 0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers
	// 0 - 8)
	// 9 - 35 = player inventory slots (which map to the InventoryPlayer slot
	// numbers 9 - 35)
	// 36 - 39 = fuel slots (tileEntity 0 - 3)
	// 40 - 44 = input slots (tileEntity 4 - 8)
	// 45 - 49 = output slots (tileEntity 9 - 13)

	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT
			* PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT
			+ PLAYER_INVENTORY_SLOT_COUNT;

	public final int FUEL_SLOTS_COUNT = 1;
	public final int INPUT_SLOTS_COUNT = 1;
	public final int OUTPUT_SLOTS_COUNT = 1;
	public final int UPGRADE_SLOTS_COUNT = 1;
	public final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT
			+ OUTPUT_SLOTS_COUNT + UPGRADE_SLOTS_COUNT;

	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX
			+ VANILLA_SLOT_COUNT;
	private final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX
			+ FUEL_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX
			+ INPUT_SLOTS_COUNT;
	private final int FIRST_UPGRADE_SLOT_INDEX = FIRST_OUTPUT_SLOT_INDEX
			+ OUTPUT_SLOTS_COUNT;

	private final int FIRST_FUEL_SLOT_NUMBER = 0;
	private final int FIRST_INPUT_SLOT_NUMBER = FIRST_FUEL_SLOT_NUMBER
			+ FUEL_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER
			+ INPUT_SLOTS_COUNT;
	private final int FIRST_UPGRADE_SLOT_NUMBER = FIRST_OUTPUT_SLOT_NUMBER
			+ OUTPUT_SLOTS_COUNT;

	public ContainerCustomFurnace(IInventory playerInv,
			TileEntityFurnaceH furnace) {
		this.tileFurnace = furnace;
		int i = -18;
		int j;
		int k;

		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOSF = 120;

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9,
						8 + k * 18, 145 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 203 + i));
		}

		// TileEntiry inventorySlots

		this.addSlotToContainer(new SlotFuel(tileFurnace,
				FIRST_FUEL_SLOT_NUMBER, 49, 94)); // Fuel
		this.addSlotToContainer(new SlotSmeltableInput(tileFurnace,
				FIRST_INPUT_SLOT_NUMBER, 22, 60));
		this.addSlotToContainer(new SlotOutput(tileFurnace,
				FIRST_OUTPUT_SLOT_NUMBER, 137, 60));
		this.addSlotToContainer(new SlotUpgrade(tileFurnace,
				FIRST_UPGRADE_SLOT_NUMBER, 62, 26));

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileFurnace.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player,
			int sourceSlotIndex) {
		Slot sourceSlot = (Slot) inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack())
			return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		// Check if the slot clicked is one of the vanilla container slots
		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX
				&& sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX
						+ VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into one of
			// the furnace slots
			// If the stack is smeltable try to merge merge the stack into the
			// input slots
			if (TileEntityFurnaceH.getSmeltingResultForItem(sourceStack) != null) {
				if (!mergeItemStack(sourceStack, FIRST_INPUT_SLOT_INDEX,
						FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT, false)) {
					return null;
				}
			} else if (TileEntityFurnaceH.getItemBurnTime(sourceStack) > 0) {
				if (!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX,
						FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT, true)) {
					// Setting the boolean to true places the stack in the
					// bottom slot first
					return null;
				}

			} else {
				return null;
			}

		} else if (sourceSlotIndex >= FIRST_FUEL_SLOT_INDEX
				&& sourceSlotIndex < FIRST_FUEL_SLOT_INDEX
						+ FURNACE_SLOTS_COUNT) {
			// This is a furnace slot so merge the stack into the players
			// inventory: try the hotbar first and then the main inventory
			// because the main inventory slots are immediately after the hotbar
			// slots, we can just merge with a single call
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX,
					VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return null;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return null;
		}
		// If stack size == 0 (the entire stack was moved) set slot contents to
		// null
		if (sourceStack.stackSize == 0) {
			sourceSlot.putStack(null);
		} else {
			sourceSlot.onSlotChanged();
		}
		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged[] = new boolean[tileFurnace.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[tileFurnace.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged
					|| cachedFields[i] != tileFurnace.getField(i)) {
				cachedFields[i] = tileFurnace.getField(i);
				fieldHasChanged[i] = true;
			}
		}
		// go through the list of crafters (players using this container) and
		// update them if necessary
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			for (int fieldID = 0; fieldID < tileFurnace.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					// Note that although sendProgressBarUpdate takes 2 ints on
					// a server these are truncated to shorts
					icrafting.sendProgressBarUpdate(this, fieldID,
							cachedFields[fieldID]);
				}

			}
		}

	}

	// Called when a progress bar update is received from the server. The two
	// values (id and data) are the same two
	// values given to sendProgressBarUpdate. In this case we are using fields
	// so we just pass them to the tileEntity.

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		tileFurnace.setField(id, data);
	}

	// SlotFuel is a slot for fuel items
	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPosition,
				int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert
		// the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityFurnaceH.isItemValidForFuelSlot(stack);
		}
	}

	// SlotSmeltableInput is a slot for input items
	public class SlotSmeltableInput extends Slot {
		public SlotSmeltableInput(IInventory inventoryIn, int index,
				int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert
		// the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityFurnaceH.isItemValidForInputSlot(stack);
		}
	}

	// SlotOutput is a slot that will not accept any items
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition,
				int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert
		// the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityFurnaceH.isItemValidForOutputSlot(stack);
		}
	}

	public class SlotUpgrade extends Slot {
		public SlotUpgrade(IInventory inventoryIn, int index, int xPosition,
				int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		// if this function returns false, the player won't be able to insert
		// the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityFurnaceH.isItemValidForUpgradeSlot(stack);
		}
	}
	

}
