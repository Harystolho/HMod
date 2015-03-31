package com.Hmod.tile_entity;

import java.util.Arrays;

import com.Hmod.HaryItems.HarysItems;
import com.Hmod.HaryRecipes.HaryFurnaceRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityFurnaceH extends TileEntity implements IInventory,
		IUpdatePlayerListBox {

	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int INPUT_SLOTS_COUNT = 1;
	public static final int OUTPUT_SLOTS_COUNT = 1;
	public static final int UPGRADE_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT
			+ INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT + UPGRADE_SLOTS_COUNT;

	public static final int FIRST_FUEL_SLOT = 0;
	public static final int FIRST_INPUT_SLOT = FIRST_FUEL_SLOT
			+ FUEL_SLOTS_COUNT;
	public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT
			+ INPUT_SLOTS_COUNT;
	public static final int FIRST_UPGRAE_SLOT = FIRST_OUTPUT_SLOT
			+ OUTPUT_SLOTS_COUNT;

	private ItemStack[] itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];

	/** The number of burn ticks remaining on the current piece of fuel */
	private int[] burnTimeRemaining = new int[FUEL_SLOTS_COUNT];
	/**
	 * The initial fuel value of the currently burning fuel (in ticks of burn
	 * duration)
	 */
	private int[] burnTimeInitialValue = new int[FUEL_SLOTS_COUNT];

	/** The number of ticks the current item has been cooking */
	private short cookTime;
	/** The number of ticks required to cook an item */
	private static short COOK_TIME_FOR_COMPLETION = 1; // vanilla value
	// seconds
	private int cachedNumberOfBurningSlots = -1;

	public double fractionOfFuelRemaining(int fuelSlot) {
		if (burnTimeInitialValue[fuelSlot] <= 0)
			return 0;
		double fraction = burnTimeRemaining[fuelSlot]
				/ (double) burnTimeInitialValue[fuelSlot];
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	public int secondsOfFuelRemaining(int fuelSlot) {
		if (burnTimeRemaining[fuelSlot] <= 0)
			return 0;
		return burnTimeRemaining[fuelSlot] / 20; // 20 ticks per second
	}

	public int numberOfBurningFuelSlots() {
		int burningCount = 0;
		for (int burnTime : burnTimeRemaining) {
			if (burnTime > 0)
				++burningCount;
		}
		return burningCount;
	}

	public double fractionOfCookTimeComplete() {
		double fraction = cookTime / (double) COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}

	@Override
	public void update() {
		// If there is nothing to smelt or there is no room in the output, reset
		// cookTime and return
		if (canSmelt()) {
			int numberOfFuelBurning = burnFuel();
			// If fuel is available, keep cooking the item, otherwise start
			// "uncooking" it at double speed
			if (numberOfFuelBurning > 0) {
				cookTime += numberOfFuelBurning;
			} else {
				cookTime -= 2;
			}
			if (cookTime < 0)
				cookTime = 0;
			// If cookTime has reached maxCookTime smelt the item and reset
			// cookTime
			if (cookTime >= COOK_TIME_FOR_COMPLETION) {
				smeltItem();
				cookTime = 0;
			}
		} else {
			cookTime = 0;
		}
		// when the number of burning slots changes, we need to force the block
		// to re-render, otherwise the change in
		// state will not be visible. Likewise, we need to force a lighting
		// recalculation.
		// The block update (for renderer) is only required on client side, but
		// the lighting is required on both, since
		// the client needs it for rendering and the server needs it for crop
		// growth etc
		int numberBurning = numberOfBurningFuelSlots();
		if (cachedNumberOfBurningSlots != numberBurning) {
			cachedNumberOfBurningSlots = numberBurning;
			if (worldObj.isRemote) {
				worldObj.markBlockForUpdate(pos);
			}
			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
	}

	/*
	 * private boolean hasUpgrade() { if (itemStacks[3] != null) { Item item =
	 * itemStacks[3].getItem(); if (item == HarysItems.hary_upgrades1) {
	 * getCookTime(item);
	 * 
	 * return true; } else if (item == HarysItems.hary_upgrades2) {
	 * getCookTime(item); return true; } else if (item ==
	 * HarysItems.hary_upgrades3) { getCookTime(item); return true; } } else {
	 * COOK_TIME_FOR_COMPLETION = 200; return true; } return false; }
	 * 
	 * private static void getCookTime(Item item) { if (item ==
	 * HarysItems.hary_upgrades1) { COOK_TIME_FOR_COMPLETION = 125; } else if
	 * (item == HarysItems.hary_upgrades2) { COOK_TIME_FOR_COMPLETION = 75; }
	 * else if (item == HarysItems.hary_upgrades3) { COOK_TIME_FOR_COMPLETION =
	 * 1; } }
	 */

	private int burnFuel() {
		int burningCount = 0;
		boolean inventoryChanged = false;
		// Iterate over all the fuel slots
		for (int i = 0; i < FUEL_SLOTS_COUNT; i++) {
			int fuelSlotNumber = i + FIRST_FUEL_SLOT;
			if (burnTimeRemaining[i] > 0) {
				--burnTimeRemaining[i];
				++burningCount;
			}
			if (burnTimeRemaining[i] == 0) {
				if (itemStacks[fuelSlotNumber] != null
						&& getItemBurnTime(itemStacks[fuelSlotNumber]) > 0) {
					// If the stack in this slot is not null and is fuel, set
					// burnTimeRemaining & burnTimeInitialValue to the
					// item's burn time and decrease the stack size
					burnTimeRemaining[i] = burnTimeInitialValue[i] = getItemBurnTime(itemStacks[fuelSlotNumber]);
					--itemStacks[fuelSlotNumber].stackSize;
					++burningCount;
					inventoryChanged = true;
					// If the stack size now equals 0 set the slot contents to
					// the items container item. This is for fuel
					// items such as lava buckets so that the bucket is not
					// consumed. If the item dose not have
					// a container item getContainerItem returns null which sets
					// the slot contents to null
					if (itemStacks[fuelSlotNumber].stackSize == 0) {
						itemStacks[fuelSlotNumber] = itemStacks[fuelSlotNumber]
								.getItem().getContainerItem(
										itemStacks[fuelSlotNumber]);
					}
				}
			}
		}
		if (inventoryChanged)
			markDirty();
		return burningCount;
	}

	private boolean canSmelt() {
		return smeltItem(false);
	}

	private void smeltItem() {
		smeltItem(true);
	}

	private boolean smeltItem(boolean performSmelt) {
		Integer firstSuitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = null;
		// finds the first input slot which is smeltable and whose result fits
		// into an output slot (stacking if possible)
		for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT
				+ INPUT_SLOTS_COUNT; inputSlot++) {
			if (itemStacks[inputSlot] != null) {
				result = getSmeltingResultForItem(itemStacks[inputSlot]);
				if (result != null) {
					// find the first suitable output slot- either empty, or
					// with identical item that has enough space
					for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT
							+ OUTPUT_SLOTS_COUNT; outputSlot++) {
						ItemStack outputStack = itemStacks[outputSlot];
						if (outputStack == null) {
							firstSuitableInputSlot = inputSlot;
							firstSuitableOutputSlot = outputSlot;
							break;
						}
						if (outputStack.getItem() == result.getItem()
								&& (!outputStack.getHasSubtypes() || outputStack
										.getMetadata() == outputStack
										.getMetadata())
								&& ItemStack.areItemStackTagsEqual(outputStack,
										result)) {
							int combinedSize = itemStacks[outputSlot].stackSize
									+ result.stackSize;
							if (combinedSize <= getInventoryStackLimit()
									&& combinedSize <= itemStacks[outputSlot]
											.getMaxStackSize()) {
								firstSuitableInputSlot = inputSlot;
								firstSuitableOutputSlot = outputSlot;
								break;
							}
						}
					}
					if (firstSuitableInputSlot != null)
						break;
				}
			}
		}
		if (firstSuitableInputSlot == null)
			return false;
		if (!performSmelt)
			return true;
		// alter input and output
		itemStacks[firstSuitableInputSlot].stackSize--;
		if (itemStacks[firstSuitableInputSlot].stackSize <= 0)
			itemStacks[firstSuitableInputSlot] = null;
		if (itemStacks[firstSuitableOutputSlot] == null) {
			itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep
																	// .copy()
																	// to avoid
																	// altering
																	// the
																	// recipe
		} else {
			itemStacks[firstSuitableOutputSlot].stackSize += result.stackSize;
		}
		markDirty();
		return true;
	}

	public static ItemStack getSmeltingResultForItem(ItemStack stack) {
		return HaryFurnaceRecipes.instance().getSmeltingResult(stack);
	}

	// returns the number of ticks the given item will burn. Returns 0 if the
	// given item is not a valid fuel
	public static short getItemBurnTime(ItemStack stack) {
		int burntime = TileEntityFurnaceH.getItemBurnTimeHary(stack); // just
																		// use
																		// the
																		// vanilla
		return (short) MathHelper.clamp_int(burntime, 0, Short.MAX_VALUE);
	}

	private static int getItemBurnTimeHary(ItemStack p_145952_0_) {
		{
			if (p_145952_0_ == null) {
				return 0;
			} else {
				Item item = p_145952_0_.getItem();

				if (item instanceof ItemBlock
						&& Block.getBlockFromItem(item) != Blocks.air) {
					Block block = Block.getBlockFromItem(item);

					return 0;
				}

				/*
				 * if (item instanceof ItemTool && ((ItemTool)
				 * item).getToolMaterialName().equals( "WOOD")) return 200; if
				 * (item instanceof ItemSword && ((ItemSword)
				 * item).getToolMaterialName().equals( "WOOD")) return 200; if
				 * (item instanceof ItemHoe && ((ItemHoe)
				 * item).getMaterialName().equals("WOOD")) return 200; if (item
				 * == Items.stick) return 100;
				 */
				if (item == Items.coal)
					return 2;
				if (item == HarysItems.ingot_hary)
					return 5;
				if (item == HarysItems.ingot_compress_hary)
					return 15;

				return net.minecraftforge.fml.common.registry.GameRegistry
						.getFuelValue(p_145952_0_);
			}
		}

	}

	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int i) {
		return itemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot == null)
			return null;
		ItemStack itemStackRemoved;
		if (itemStackInSlot.stackSize <= count) {
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.stackSize == 0) {
				setInventorySlotContents(slotIndex, null);
			}
		}
		markDirty();
		return itemStackRemoved;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks[slotIndex] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	// Return true if the given player is able to use this block. In this case
	// it checks that
	// 1) the world tileentity hasn't been replaced in the meantime, and
	// 2) the player isn't too far away from the centre of the block
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.pos) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 0 * 0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY()
				+ Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) > MAXIMUM_DISTANCE_SQ;
	}

	// Return true if the given stack is allowed to be inserted in the given
	// slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel
	// slots
	static public boolean isItemValidForFuelSlot(ItemStack itemStack) {
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given
	// slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel
	// slots
	static public boolean isItemValidForInputSlot(ItemStack itemStack) {
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given
	// slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel
	// slots
	static public boolean isItemValidForOutputSlot(ItemStack itemStack) {
		return false;
	}

	static public boolean isItemValidForUpgradeSlot(ItemStack itemStack) {
	/*	if (itemStack.getItem() == HarysItems.hary_upgrades1)
			return true;
		if (itemStack.getItem() == HarysItems.hary_upgrades2)
			return true;
		if (itemStack.getItem() == HarysItems.hary_upgrades3)
			return true;
*/
		return false;
	}

	// This is where you save any data that you don't want to lose when the tile
	// entity unloads
	// In this case, it saves the state of the furnace (burn time etc) and the
	// itemstacks stored in the fuel, input, and output slots
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to
												// save and load the tiles
												// location
		// // Save the stored item stacks
		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which
		// is effectively a hashmap of key->value pairs such
		// as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which
		// is similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (this.itemStacks[i] != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for
		// the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);
		// Save everything else
		parentNBTTagCompound.setShort("CookTime", cookTime);
		parentNBTTagCompound.setTag("burnTimeRemaining", new NBTTagIntArray(
				burnTimeRemaining));
		parentNBTTagCompound.setTag("burnTimeInitial", new NBTTagIntArray(
				burnTimeInitialValue));
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound); // The super call is required to save
											// and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for
											// a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items",
				NBT_TYPE_COMPOUND);
		Arrays.fill(itemStacks, null); // set all slots to empty
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
				this.itemStacks[slotNumber] = ItemStack
						.loadItemStackFromNBT(dataForOneSlot);
			}
		}
		// Load everything else. Trim the arrays (or pad with 0) to make sure
		// they have the correct number of elements
		cookTime = nbtTagCompound.getShort("CookTime");
		burnTimeRemaining = Arrays.copyOf(
				nbtTagCompound.getIntArray("burnTimeRemaining"),
				FUEL_SLOTS_COUNT);
		burnTimeInitialValue = Arrays
				.copyOf(nbtTagCompound.getIntArray("burnTimeInitial"),
						FUEL_SLOTS_COUNT);
		cachedNumberOfBurningSlots = -1;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int METADATA = 0;
		return new S35PacketUpdateTileEntity(this.pos, METADATA, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	// will add a key for this container to the lang file so we can name it in
	// the GUI
	@Override
	public String getName() {
		return "Hary Furnace";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	// standard code to look up what the human-readable name is
	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName())
				: new ChatComponentTranslation(this.getName());
	}

	// Fields are used to send non-inventory information from the server to
	// interested clients
	// The container code caches the fields and sends the client any fields
	// which have changed.
	// The field ID is limited to byte, and the field value is limited to short.
	// (if you use more than this, they get cast down
	// in the network packets)
	// If you need more than this, or shorts are too small, use a custom packet
	// in your container instead.

	private static final byte COOK_FIELD_ID = 0;
	private static final byte FIRST_BURN_TIME_REMAINING_FIELD_ID = 1;
	private static final byte FIRST_BURN_TIME_INITIAL_FIELD_ID = FIRST_BURN_TIME_REMAINING_FIELD_ID
			+ (byte) FUEL_SLOTS_COUNT;
	private static final byte NUMBER_OF_FIELDS = FIRST_BURN_TIME_INITIAL_FIELD_ID
			+ (byte) FUEL_SLOTS_COUNT;

	@Override
	public int getField(int id) {
		if (id == COOK_FIELD_ID)
			return cookTime;
		if (id >= FIRST_BURN_TIME_REMAINING_FIELD_ID
				&& id < FIRST_BURN_TIME_REMAINING_FIELD_ID + FUEL_SLOTS_COUNT) {
			return burnTimeRemaining[id - FIRST_BURN_TIME_REMAINING_FIELD_ID];
		}
		if (id >= FIRST_BURN_TIME_INITIAL_FIELD_ID
				&& id < FIRST_BURN_TIME_INITIAL_FIELD_ID + FUEL_SLOTS_COUNT) {
			return burnTimeInitialValue[id - FIRST_BURN_TIME_INITIAL_FIELD_ID];
		}
		System.err
				.println("Invalid field ID in TileInventorySmelting.getField:"
						+ id);
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == COOK_FIELD_ID) {
			cookTime = (short) value;
		} else if (id >= FIRST_BURN_TIME_REMAINING_FIELD_ID
				&& id < FIRST_BURN_TIME_REMAINING_FIELD_ID + FUEL_SLOTS_COUNT) {
			burnTimeRemaining[id - FIRST_BURN_TIME_REMAINING_FIELD_ID] = value;
		} else if (id >= FIRST_BURN_TIME_INITIAL_FIELD_ID
				&& id < FIRST_BURN_TIME_INITIAL_FIELD_ID + FUEL_SLOTS_COUNT) {
			burnTimeInitialValue[id - FIRST_BURN_TIME_INITIAL_FIELD_ID] = value;
		} else {
			System.err
					.println("Invalid field ID in TileInventorySmelting.setField:"
							+ id);
		}
	}

	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return false;
	}

	/**
	 * This method removes the entire contents of the given slot and returns it.
	 * Used by containers such as crafting tables which return any items in
	 * their slots when you close the GUI
	 * 
	 * @param slotIndex
	 * @return
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
			setInventorySlotContents(slotIndex, null);
		return itemStack;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

}