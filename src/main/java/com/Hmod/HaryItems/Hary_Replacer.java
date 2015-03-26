package com.Hmod.HaryItems;

import java.util.List;

import net.minecraft.block.Block;
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

public class Hary_Replacer extends Item {

	public Hary_Replacer() {
		setUnlocalizedName("hary_replacer");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {

		if (!playerIn.isSneaking()) {
			if (stack.getTagCompound() == null) {

				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("X", pos.getX());
			nbt.setInteger("Y", pos.getY());
			nbt.setInteger("Z", pos.getZ());
			stack.getTagCompound().setTag("replacer", nbt);
			stack.setStackDisplayName(EnumChatFormatting.AQUA + "Hary Replacer");
			
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn,
			EntityPlayer playerIn) {

		/*
		 * if (playerIn.isSneaking()) { if (stack.getTagCompound() != null) { if
		 * (stack.getTagCompound().hasKey("replacer")) { NBTTagCompound nbt =
		 * (NBTTagCompound) stack .getTagCompound().getTag("replacer"); int posX
		 * = nbt.getInteger("X"); int posY = nbt.getInteger("Y"); int posZ =
		 * nbt.getInteger("Z");
		 * 
		 * IBlockState state0 = worldIn.getBlockState(new BlockPos( posX, posY,
		 * posZ));
		 * 
		 * BlockPos posA = playerIn.getPosition();
		 * 
		 * switch(playerIn.getHorizontalFacing()){ case NORTH: posA.add(0, 0,
		 * -2); break; case SOUTH: posA.add(0, 0, +2); break; case EAST:
		 * posA.add(+2, 0, 0); break; case WEST: posA.add(-2, 0, 0); break; }
		 * 
		 * worldIn.setBlockState(posA, state0);
		 * 
		 * } } }
		 */

		if (playerIn.isSneaking()) {

			if (stack.getTagCompound() != null) {
				stack.getTagCompound().removeTag("replacer");
				stack.clearCustomName();
			}
		}

		return stack;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {

		if (stack.getTagCompound() != null) {
			if (stack.getTagCompound().hasKey("replacer")) {
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound()
						.getTag("replacer");
				int posX = nbt.getInteger("X");
				int posY = nbt.getInteger("Y");
				int posZ = nbt.getInteger("Z");
				tooltip.add("X:" + posX + " Y:" + posY + " Z: " + posZ);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		if (stack.getTagCompound() != null) {
			return stack.getTagCompound().hasKey("coords");
		}
		return false;
	}

}
