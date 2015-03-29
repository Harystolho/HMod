package com.Hmod.achievements;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

public class HaryAchiev {

	public static Achievement AchievMining;
	public static Achievement AchievCrafting;
	public static Achievement AchievSmelting;
	
	public static void init(){
		
		AchievMining = new Achievement("achievement.orePicked", "orePicked", 0, 0, HarysBlocks.hary_block, null);
		AchievSmelting = new Achievement("achievement.itemSmelted", "itemSmelted", 2, 0, HarysItems.ingot_hary, AchievMining);
		AchievCrafting = new Achievement("achievement.IngotCreated", "IngotCreated", 0, 2, HarysItems.ingot_compress_hary, AchievMining);
		
		
		
		AchievementPage.registerAchievementPage(new AchievementPage("InstaFurnace", new Achievement[]{AchievMining, AchievSmelting, AchievCrafting}));
		
		FMLCommonHandler.instance().bus().register(new HaryAchievEvent());
		
	}
	
}
