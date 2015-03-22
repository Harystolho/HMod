package com.Hmod.tile_entity;

import com.Hmod.HaryBlocks.HaryFurnace;
import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;
import com.Hmod.HaryRecipes.HaryFurnaceRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnace extends TileEntity implements ISidedInventory {

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private ItemStack[] furnaceItemStacks = new ItemStack[3];

	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;

	public InventoryBasic inventory;

	private String furnaceName;

	public TileEntityFurnace() {
		inventory = new InventoryBasic("FurnaceInventory", false, 3);
	}

	public void furnaceName(String string) {
		this.furnaceName = string;
	}

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.furnaceItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack;
			if (this.furnaceItemStacks[par1].stackSize <= par2) {
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);
				if (this.furnaceItemStacks[par1].stackSize == 0) {
					this.furnaceItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.furnaceItemStacks[slot] != null) {
			ItemStack itemstack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.furnaceItemStacks[slot] = itemstack;
		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.furnaceName : "hm.furnace";
	}

	@Override
	public boolean hasCustomName() {
		return this.furnaceName != null && this.furnaceName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
				this.furnaceItemStacks[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.furnaceBurnTime = compound.getShort("BurnTime");
		this.cookTime = compound.getShort("CookTime");
		this.totalCookTime = compound.getShort("CookTimeTotal");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

		if (compound.hasKey("CustomName", 8)) {
			this.furnaceCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("BurnTime", (short) this.furnaceBurnTime);
		compound.setShort("CookTime", (short) this.cookTime);
		compound.setShort("CookTimeTotal", (short) this.totalCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
			if (this.furnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.furnaceCustomName);
		}
	}

	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;

		if (this.isBurning()) {
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote) {
			if (!this.isBurning()
					&& (this.furnaceItemStacks[1] == null || this.furnaceItemStacks[0] == null)) {
				if (!this.isBurning() && this.cookTime > 0) {
					this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0,
							this.totalCookTime);
				}
			} else {
				if (!this.isBurning() && this.canSmelt()) {
					this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

					if (this.isBurning()) {
						flag1 = true;

						if (this.furnaceItemStacks[1] != null) {
							--this.furnaceItemStacks[1].stackSize;

							if (this.furnaceItemStacks[1].stackSize == 0) {
								this.furnaceItemStacks[1] = furnaceItemStacks[1]
										.getItem().getContainerItem(
												furnaceItemStacks[1]);
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt()) {
					++this.cookTime;

					if (this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this
								.func_174904_a(this.furnaceItemStacks[0]);
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			}

			if (flag != this.isBurning()) {
				flag1 = true;
				BlockFurnace
						.setState(this.isBurning(), this.worldObj, this.pos);
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	public int func_174904_a(ItemStack p_174904_1_) {
		return 200;
	}

	private boolean canSmelt() {
		if (this.furnaceItemStacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(
					this.furnaceItemStacks[0]);
			if (itemstack == null)
				return false;
			if (this.furnaceItemStacks[2] == null)
				return true;
			if (!this.furnaceItemStacks[2].isItemEqual(itemstack))
				return false;
			int result = furnaceItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit()
					&& result <= this.furnaceItemStacks[2].getMaxStackSize(); // Forge
																				// BugFix:
																				// Make
																				// it
																				// respect
																				// stack
																				// sizes
																				// properly.
		}
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = HaryFurnaceRecipes.instance()
					.getSmeltingResult(this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[2] == null) {
				this.furnaceItemStacks[2] = itemstack.copy();
			} else if (this.furnaceItemStacks[2].getItem() == itemstack
					.getItem()) {
				this.furnaceItemStacks[2].stackSize += itemstack.stackSize; // Forge
																			// BugFix:
																			// Results
																			// may
																			// have
																			// multiple
																			// items
			}

			if (this.furnaceItemStacks[0].getItem() == Item
					.getItemFromBlock(Blocks.sponge)
					&& this.furnaceItemStacks[0].getMetadata() == 1
					&& this.furnaceItemStacks[1] != null
					&& this.furnaceItemStacks[1].getItem() == Items.bucket) {
				this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
			}

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize <= 0) {
				this.furnaceItemStacks[0] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack p_145952_0_) {
		if (p_145952_0_ == null) {
			return 0;
		} else {
			Item item = p_145952_0_.getItem();

			if (item instanceof ItemBlock
					&& Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab) {
					return 150;
				}

				if (block.getMaterial() == Material.wood) {
					return 300;
				}

				if (block == Blocks.coal_block) {
					return 16000;
				}
			}

			if (item instanceof ItemTool
					&& ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword
					&& ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe
					&& ((ItemHoe) item).getMaterialName().equals("WOOD"))
				return 200;
			if (item == Items.stick)
				return 100;
			if (item == Items.coal)
				return 1600;
			if (item == Items.lava_bucket)
				return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling))
				return 100;
			if (item == Items.blaze_rod)
				return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry
					.getFuelValue(p_145952_0_);
		}
	}

	public static boolean isItemFuel(ItemStack p_145954_0_) {
		/**
		 * Returns the number of ticks that the supplied fuel item will keep the
		 * furnace burning, or 0 if the item isn't fuel
		 */
		return getItemBurnTime(p_145954_0_) > 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : player
				.getDistanceSq((double) this.pos.getX() + 0.5D,
						(double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? slotsBottom
				: (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		{
			if (direction == EnumFacing.DOWN && index == 1) {
				Item item = stack.getItem();

				if (item != Items.water_bucket && item != Items.bucket) {
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.furnaceBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.furnaceBurnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		{
			for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
				this.furnaceItemStacks[i] = null;
			}
		}
	}

	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

}
