package com.Hmod.HaryItems;

import com.Hmod.main.MainHary;

import net.minecraft.block.BlockHugeMushroom.EnumType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Hary_Pickaxe extends ItemPickaxe {

	public Hary_Pickaxe(ToolMaterial material) {
		super(material);
		// TODO Auto-generated constructor stub

		setUnlocalizedName("hary_pick");
		setCreativeTab(MainHary.HaryT);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (playerIn.getHorizontalFacing() == EnumFacing.NORTH
				|| playerIn.getHorizontalFacing() == EnumFacing.SOUTH) {
			worldIn.destroyBlock(new BlockPos(x, y, z), true);
			worldIn.destroyBlock(new BlockPos(x, y + 1, z), true);
			worldIn.destroyBlock(new BlockPos(x - 1, y + 1, z), true);
			worldIn.destroyBlock(new BlockPos(x + 1, y + 1, z), true);
			worldIn.destroyBlock(new BlockPos(x - 1, y - 1, z), true);
			worldIn.destroyBlock(new BlockPos(x + 1, y - 1, z), true);
			worldIn.destroyBlock(new BlockPos(x, y - 1, z), true);
			worldIn.destroyBlock(new BlockPos(x + 1, y, z), true);
			worldIn.destroyBlock(new BlockPos(x - 1, y, z), true);
			playerIn.setHealth(playerIn.getHealth() -1);
		}

		if (playerIn.getHorizontalFacing() == EnumFacing.WEST
				|| playerIn.getHorizontalFacing() == EnumFacing.EAST) {
			worldIn.destroyBlock(new BlockPos(x, y, z), true);
			worldIn.destroyBlock(new BlockPos(x, y + 1, z), true);
			worldIn.destroyBlock(new BlockPos(x, y + 1, z + 1), true);
			worldIn.destroyBlock(new BlockPos(x, y + 1, z - 1), true);
			worldIn.destroyBlock(new BlockPos(x, y - 1, z - 1), true);
			worldIn.destroyBlock(new BlockPos(x, y - 1, z + 1), true);
			worldIn.destroyBlock(new BlockPos(x, y - 1, z), true);
			worldIn.destroyBlock(new BlockPos(x, y, z + 1), true);
			worldIn.destroyBlock(new BlockPos(x, y, z - 1), true);
			playerIn.setHealth(playerIn.getHealth() -1);
		}


		return false;
	}

}
