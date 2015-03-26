package com.Hmod.HaryItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Hary_Test extends Item{

	public Hary_Test(){
		setUnlocalizedName("hary_test");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {

		
		return itemStackIn;
	}
	
}
