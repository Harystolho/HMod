package com.Hmod.tile_entity;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class HaryTileEntityChest extends TileEntityChest {

	private String customName;
	private ItemStack[] chestContents = new ItemStack[27];

	public HaryTileEntityChest adjacentChestZNeg;
	/** Contains the chest tile located adjacent to this one (if any) */
	public HaryTileEntityChest adjacentChestXPos;
	/** Contains the chest tile located adjacent to this one (if any) */
	public HaryTileEntityChest adjacentChestXNeg;
	/** Contains the chest tile located adjacent to this one (if any) */
	public HaryTileEntityChest adjacentChestZPos;

	public int getSizeInventory() {
		return 27;
	}

	public String getName() {
		return this.hasCustomName() ? this.customName : "container.chest";
	}

	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
		super.readFromNBT(parentNBTTagCompound); // The super call is required
													// to save and load the
													// tiles location
		final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for
											// a listing
		NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList("Items",
				NBT_TYPE_COMPOUND);
		Arrays.fill(chestContents, null); // set all slots to empty
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			int slotIndex = dataForOneSlot.getByte("Slot") & 255;
			if (slotIndex >= 0 && slotIndex < this.chestContents.length) {
				this.chestContents[slotIndex] = ItemStack
						.loadItemStackFromNBT(dataForOneSlot);
			}
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to
												// save and load the
												// tileEntity's location
		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which
		// is effectively a hashmap of key->value pairs such
		// as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which
		// is similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.chestContents[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for
		// the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);
	}

	private void func_174910_a(HaryTileEntityChest p_174910_1_,
			EnumFacing p_174910_2_) {
		if (p_174910_1_.isInvalid()) {
			this.adjacentChestChecked = false;
		} else if (this.adjacentChestChecked) {
			switch (HaryTileEntityChest.SwitchEnumFacing.field_177366_a[p_174910_2_
					.ordinal()]) {
			case 1:
				if (this.adjacentChestZNeg != p_174910_1_) {
					this.adjacentChestChecked = false;
				}

				break;
			case 2:
				if (this.adjacentChestZPos != p_174910_1_) {
					this.adjacentChestChecked = false;
				}

				break;
			case 3:
				if (this.adjacentChestXPos != p_174910_1_) {
					this.adjacentChestChecked = false;
				}

				break;
			case 4:
				if (this.adjacentChestXNeg != p_174910_1_) {
					this.adjacentChestChecked = false;
				}
			}
		}
	}

	/**
	 * Performs the check for adjacent chests to determine if this chest is
	 * double or not.
	 */
	public void checkForAdjacentChests() {
		if (!this.adjacentChestChecked) {
			this.adjacentChestChecked = true;
			this.adjacentChestXNeg = this.func_174911_a(EnumFacing.WEST);
			this.adjacentChestXPos = this.func_174911_a(EnumFacing.EAST);
			this.adjacentChestZNeg = this.func_174911_a(EnumFacing.NORTH);
			this.adjacentChestZPos = this.func_174911_a(EnumFacing.SOUTH);
		}
	}

	protected HaryTileEntityChest func_174911_a(EnumFacing p_174911_1_) {
		BlockPos blockpos = this.pos.offset(p_174911_1_);

		if (this.func_174912_b(blockpos)) {
			TileEntity tileentity = this.worldObj.getTileEntity(blockpos);

			if (tileentity instanceof TileEntityChest) {
				HaryTileEntityChest tileentitychest =  (HaryTileEntityChest) tileentity;
				tileentitychest.func_174910_a(this, p_174911_1_.getOpposite());
				return tileentitychest;
			}
		}

		return null;
	}

	private boolean func_174912_b(BlockPos p_174912_1_) {
		if (this.worldObj == null) {
			return false;
		} else {
			Block block = this.worldObj.getBlockState(p_174912_1_).getBlock();
			return block instanceof BlockChest
					&& ((BlockChest) block).chestType == this.getChestType();
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_177366_a = new int[EnumFacing.values().length];
		private static final String __OBFID = "CL_00002041";

		static {
			try {
				field_177366_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (NoSuchFieldError var4) {
				;
			}

			try {
				field_177366_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (NoSuchFieldError var3) {
				;
			}

			try {
				field_177366_a[EnumFacing.EAST.ordinal()] = 3;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				field_177366_a[EnumFacing.WEST.ordinal()] = 4;
			} catch (NoSuchFieldError var1) {
				;
			}
		}
	}
}
