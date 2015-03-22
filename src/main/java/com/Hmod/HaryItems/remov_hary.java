package com.Hmod.HaryItems;

import java.util.List;

import com.Hmod.HaryBlocks.HarysBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Remov_hary extends Item {

	public Remov_hary() {

		setUnlocalizedName("remov_hary");

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		IBlockState state0 = HarysBlocks.hary_block.getDefaultState();
		BlockPos pos0 = new BlockPos(x, y, z);
		worldIn.setBlockToAir(pos0);
		BlockPos pos2 = new BlockPos(x - 1, y, z);
		worldIn.setBlockToAir(pos2);
		BlockPos pos3 = new BlockPos(x + 1, y, z);
		worldIn.setBlockToAir(pos3);
		BlockPos pos01 = new BlockPos(x, y, z + 1);
		worldIn.setBlockToAir(pos01);
		BlockPos pos22 = new BlockPos(x - 1, y, z + 1);
		worldIn.setBlockToAir(pos22);
		BlockPos pos33 = new BlockPos(x + 1, y, z + 1);
		worldIn.setBlockToAir(pos33);
		BlockPos pos04 = new BlockPos(x, y, z - 1);
		worldIn.setBlockToAir(pos04);
		BlockPos pos25 = new BlockPos(x - 1, y, z - 1);
		worldIn.setBlockToAir(pos25);
		BlockPos pos36 = new BlockPos(x + 1, y, z - 1);
		worldIn.setBlockToAir(pos36);

		return false;
	}

}
