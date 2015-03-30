package com.Hmod.tile_entity;

import java.util.Map;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.main.MainHary;
import com.Hmod.nothing.HaryChestRender;
import com.Hmod.nothing.HaryItemRenderChest;
import com.Hmod.nothing.hary_chest;
import com.google.common.collect.Maps;

public class HaryEntity {

	public static final Map stringToClassMapping = Maps.newHashMap();
	public static final Map idToClassMapping = Maps.newHashMap();
	public static final Map classToStringMapping = Maps.newHashMap();
	private static final Map classToIDMapping = Maps.newHashMap();
	private static final Map stringToIDMapping = Maps.newHashMap();

	public static void register() {

		GameRegistry.registerTileEntity(TileEntityBarrel.class,
				"TileEntityBarrel");

		GameRegistry.registerTileEntity(TileEntityFurnaceH.class,
				"TileEntityFurnaceH");

	}

}
