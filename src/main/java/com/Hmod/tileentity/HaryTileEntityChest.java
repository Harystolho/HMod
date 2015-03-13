package com.Hmod.tileentity;

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

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length) {
				this.chestContents[j] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}
	}

	private void func_174910_a(TileEntityChest p_174910_1_,
			EnumFacing p_174910_2_) {
		if (p_174910_1_.isInvalid()) {
			this.adjacentChestChecked = false;
		} else if (this.adjacentChestChecked) {
			switch (TileEntityChest.SwitchEnumFacing.field_177366_a[p_174910_2_
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
				HaryTileEntityChest tileentitychest = (HaryTileEntityChest) tileentity;
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
	

}
