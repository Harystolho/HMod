package com.Hmod.HaryItems;

import java.util.List;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.container.ContainerBarrel;
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

public class ItemWirelessContainer extends Item {

	public ItemWirelessContainer() {
		setUnlocalizedName("hary_bag");
		setCreativeTab(MainHary.HaryT);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {

		tooltip.add("Furnace/Barrel chunk must be loaded");

		super.addInformation(stack, playerIn, tooltip, advanced);
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
		IBlockState pos1 = worldIn.getBlockState(pos0);
		IBlockState posB = HarysBlocks.hary_barrel.getDefaultState();

		if (BposX != 0 && BposY != 0 && BposZ != 0) {
			if (!worldIn.isRemote) {
				if (pos1 == posB) {
					playerIn.openGui(MainHary.instance,
							ContainerBarrel.GUI_BARREL, worldIn, pos0.getX(),
							pos0.getY(), pos0.getZ());
				} else {
					playerIn.openGui(MainHary.instance,
							ContainerCustomFurnace.GUI_FURNACE, worldIn,
							pos0.getX(), pos0.getY(), pos0.getZ());
				}
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
					+ "Haryte Wireless Bag [LINKED]");

		}

		return false;
	}

}
