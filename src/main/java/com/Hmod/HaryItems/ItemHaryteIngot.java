package com.Hmod.HaryItems;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemHaryteIngot extends Item {

	public ItemHaryteIngot() {
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		// TODO Auto-generated method stub
		tooltip.add("You can also put a PurpleOre in the ");
		tooltip.add("PurpleFurnace to get 2PurpleIngots");
	}

}
