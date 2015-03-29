package com.Hmod.handler;

import java.io.File;

import com.typesafe.config.Config;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static boolean PurpleOreSpawn;
	public static int PurpleOreSpawnRate;
	
	public static void init(File conf) {

		Configuration config = new Configuration(conf);

		try {
			config.load();
			
			String SpawnOre = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "OreSpawn Config";
			String SpawnOreRate = config.CATEGORY_GENERAL + config.CATEGORY_GENERAL + "";
			
			config.addCustomCategoryComment(SpawnOre, "Spawn Ore");
			config.addCustomCategoryComment(SpawnOre, "Spawn Ore Rate");
			PurpleOreSpawn = config.getBoolean("Purple Ore Spawn", SpawnOre, true, "Can PurpleOre Spawn:");
			PurpleOreSpawnRate = config.getInt("Purple Ore Spawn Rate", SpawnOre, 10, 0, 50, "PurpleOre Spawn Rate:");
			
		} catch (Exception e) {
			
		} finally {
			config.save();
		}

	}
}
