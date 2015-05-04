package com.Hmod.handler;

import java.io.File;

import com.typesafe.config.Config;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static boolean PurpleOreSpawn;
	public static int PurpleOreSpawnRate;
	public static boolean HardMode;
	
	public static void init(File conf) {

		Configuration config = new Configuration(conf);

		try {
			config.load();
			
			String SpawnOre = config.CATEGORY_SPLITTER + "OreSpawn Config";
			
			PurpleOreSpawn = config.getBoolean("Purple Ore Spawn", SpawnOre, true, "Can PurpleOre Spawn:");
			PurpleOreSpawnRate = config.getInt("Purple Ore Spawn Rate", SpawnOre, 10, 0, 50, "PurpleOre Spawn Rate:");
			
			String HardCraftMod = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Misc";
			
			HardMode = config.getBoolean("Hard Mode?", HardCraftMod, false, "false = Diamond // true = Diamond Block");
			
		} catch (Exception e) {
			
		} finally {
			config.save();
		}

	}
}
