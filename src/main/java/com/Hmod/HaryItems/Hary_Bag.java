package com.Hmod.HaryItems;

import com.Hmod.container.ContainerCustomFurnace;
import com.Hmod.main.MainHary;
import com.jcraft.jorbis.Block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Hary_Bag extends Item {

	public Hary_Bag() {
		setUnlocalizedName("hary_bag");
		setCreativeTab(MainHary.HaryT);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn,
			EntityPlayer playerIn) {
		int BposX = 0, BposY = 0, BposZ = 0;
		
		if (stack.getTagCompound() != null) {
			if (stack.getTagCompound().hasKey("bag")) {
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound()
						.getTag("bag");
				BposX = nbt.getInteger("posX");
				BposY = nbt.getInteger("posY");
				BposZ = nbt.getInteger("posZ");
			}
		}

		BlockPos pos0 = new BlockPos(BposX, BposY, BposZ);
		
		if (BposX != 0 && BposY != 0 && BposZ != 0) {
			if (!worldIn.isRemote) {
				playerIn.openGui(MainHary.instance,
						ContainerCustomFurnace.GUI_FURNACE, worldIn, pos0.getX(),
						pos0.getY(), pos0.getZ());
			}
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {

		if (playerIn.isSneaking()) {
			if (stack.getTagCompound() == null) {

				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("posX", pos.getX());
			nbt.setInteger("posY", pos.getY());
			nbt.setInteger("posZ", pos.getZ());
			stack.getTagCompound().setTag("bag", nbt);
			stack.setStackDisplayName(EnumChatFormatting.BLUE
					+ "Purple Bag [LINKED]");

		}

		return false;
	}

}
