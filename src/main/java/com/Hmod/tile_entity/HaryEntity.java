package com.Hmod.tile_entity;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryBlocks.hary_chest;
import com.Hmod.itemrender.tileentity.HaryItemRenderChest;
import com.Hmod.render.tile_entity.HaryChestRender;

public class HaryEntity {

	public static void register() {

		GameRegistry.registerTileEntity(TileEntityInventory9x1.class,
				"hary_tile_inventory_basic");

		GameRegistry.registerTileEntity(HaryTileEntityChest.class,
				"hary_chest_tile_inventory_basic");
		
		GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileEntityBarrel");

	}

	public static void ClientRegister(){
		
		ClientRegistry.bindTileEntitySpecialRenderer(HaryTileEntityChest.class, new HaryChestRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(HarysBlocks.hary_chest), new HaryItemRenderChest());
		
	}
	
}
