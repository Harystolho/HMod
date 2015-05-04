package com.Hmod.HaryItems;

import com.Hmod.HaryBlocks.HarysBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Hary_Sword_Maker extends Item {

	public Hary_Sword_Maker() {
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {
		IBlockState diamond = worldIn.getBlockState(pos);
		IBlockState d2 = Blocks.diamond_block.getDefaultState();

		if (diamond == d2) {
			ItemStack[] invP = playerIn.inventory.mainInventory;
			Item itemY = HarysItems.hary_dust;

			boolean hasItem = hasItem(itemY, invP);
			if (hasItem) {
				stack.setItem(HarysItems.hary_sword);
				consumeInventoryItem(itemY, invP);
				worldIn.setBlockToAir(pos);
			}
		}

		return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY,
				hitZ);
	}

	public boolean hasItem(Item p_146028_1_, ItemStack[] inv) {
		int i = this.getInventorySlotContainItem(p_146028_1_, inv);
		return i >= 0;
	}

	private int getInventorySlotContainItem(Item itemIn, ItemStack[] inv) {
		ItemStack[] mainInventory = inv;

		for (int i = 0; i < mainInventory.length; ++i) {
			if (mainInventory[i] != null
					&& mainInventory[i].getItem() == itemIn) {
				return i;
			}
		}

		return -1;
	}

	public boolean consumeInventoryItem(Item p_146026_1_, ItemStack[] item) {
		int i = this.getInventorySlotContainItem(p_146026_1_, item);
		ItemStack[] mainInventory = item;

		if (i < 0) {
			return false;
		} else {
			if (--mainInventory[i].stackSize <= 0) {
				mainInventory[i] = null;
			}

			return true;
		}
	}
}
