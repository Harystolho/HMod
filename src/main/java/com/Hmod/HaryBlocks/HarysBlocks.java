package com.Hmod.HaryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.gui.GuiHandlerH9x1;
import com.Hmod.gui.GuiHandlerRegistry9x1;
import com.Hmod.helper.Reference;
import com.Hmod.main.MainHary;
import com.Hmod.tile_entity.HaryTileEntityChest;
import com.Hmod.tile_entity.TileEntityInventory9x1;

public class HarysBlocks {

	public static Block hary_block;
	public static Block hary_ore;
	public static Block hary_gui;
	public static Block hary_chest;
	public static Block hary_barrel;
	
	public static void init() {

		hary_block = new BlocksHarys(Material.cloth).setUnlocalizedName("hary_block").setCreativeTab(MainHary.HaryT);
		hary_ore = new BlocksHarys(Material.iron).setUnlocalizedName("hary_ore").setCreativeTab(MainHary.HaryT);
		
		hary_gui = new hary_gui().setUnlocalizedName("hary_gui").setCreativeTab(MainHary.HaryT);
		hary_chest = new hary_chest(0).setUnlocalizedName("hary_chest").setCreativeTab(MainHary.HaryT);
		
		hary_barrel = new BlockBarrel();
	}

	public static void register() {

		GameRegistry.registerBlock(hary_block, hary_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hary_ore, hary_ore.getUnlocalizedName().substring(5));
		
		
		
		GameRegistry.registerBlock(hary_gui, hary_gui.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hary_chest, hary_chest.getUnlocalizedName().substring(5));
		
		GameRegistry.registerBlock(hary_barrel, hary_barrel.getUnlocalizedName().substring(5));
		
		
		 GuiHandlerRegistry9x1.getInstance().registerGuiHandler(new GuiHandlerH9x1(), GuiHandlerH9x1.getGuiID());
		 
	}

	public static void registerRenders(){
		
		registerRender(hary_block);
		registerRender(hary_ore);
		registerRender(hary_chest);
		registerRender(hary_barrel);
	}
	public static void registerRender(Block block){
	
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
