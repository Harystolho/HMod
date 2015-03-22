package com.Hmod.tile_entity;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.nothing.HaryChestRender;
import com.Hmod.nothing.HaryItemRenderChest;
import com.Hmod.nothing.hary_chest;

public class HaryEntity {

	public static void register() {

		GameRegistry.registerTileEntity(TileEntityBarrel.class,
				"TileEntityBarrel");
		
		GameRegistry.registerTileEntity(TileEntityFurnace.class,
				"TileEntityFurnace");

	}

}
