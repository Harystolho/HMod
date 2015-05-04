package com.Hmod.achievements;


import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

public class HaryAchievEvent {

	@SubscribeEvent
	public void onOrePicked(PlayerEvent.ItemPickupEvent e){
		if(e.pickedUp.getEntityItem().isItemEqual(new ItemStack(HarysBlocks.hary_ore))){
			e.player.addStat(HaryAchiev.AchievMining, 1);
		}
	}
	
	@SubscribeEvent
	public void onItemSmelted(PlayerEvent.ItemSmeltedEvent e){
		if(e.smelting.getItem().equals(HarysItems.ingot_hary)){
			e.player.addStat(HaryAchiev.AchievSmelting, 1);
		}
	}
	
	@SubscribeEvent
	public void OnCraftEvent(PlayerEvent.ItemCraftedEvent e){
		if(e.crafting.getItem().equals(HarysItems.ingot_compress_hary)){
			e.player.addStat(HaryAchiev.AchievCrafting, 1);
		}
	}
	
	
}
