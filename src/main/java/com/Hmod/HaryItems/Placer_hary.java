package com.Hmod.HaryItems;

import java.util.List;

import com.Hmod.HaryBlocks.HarysBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Placer_hary extends Item {

	ItemStack[] item;
	
	public Placer_hary() {

		setUnlocalizedName("wrench_hary");

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {

		int x = pos.getX();
		int y = pos.getY() + 1;
		int z = pos.getZ();

		IBlockState state0 = HarysBlocks.hary_block.getDefaultState();
		BlockPos pos0 = new BlockPos(x, y, z);
		worldIn.setBlockState(pos0, state0);
		BlockPos pos2 = new BlockPos(x - 1, y, z);
		worldIn.setBlockState(pos2, state0);
		BlockPos pos3 = new BlockPos(x + 1, y, z);
		worldIn.setBlockState(pos3, state0);
		BlockPos pos01 = new BlockPos(x, y, z + 1);
		worldIn.setBlockState(pos01, state0);
		BlockPos pos22 = new BlockPos(x - 1, y, z + 1);
		worldIn.setBlockState(pos22, state0);
		BlockPos pos33 = new BlockPos(x + 1, y, z + 1);
		worldIn.setBlockState(pos33, state0);
		BlockPos pos04 = new BlockPos(x, y, z - 1);
		worldIn.setBlockState(pos04, state0);
		BlockPos pos25 = new BlockPos(x - 1, y, z - 1);
		worldIn.setBlockState(pos25, state0);
		BlockPos pos36 = new BlockPos(x + 1, y, z - 1);
		worldIn.setBlockState(pos36, state0);

		return false;
	}

	/*
	 * public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World
	 * worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float
	 * hitZ) {
	 * 
	 * System.out.println(playerIn.getRotationYawHead());
	 * 
	 * 
	 * return false; }
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		// TODO Auto-generated method stub
		stack.setStackDisplayName(EnumChatFormatting.YELLOW + "Block Placer");
	}
	
}
