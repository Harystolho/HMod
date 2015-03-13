package com.Hmod.HaryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.gui.GuiHandlerH;
import com.Hmod.gui.GuiHandlerRegistry;
import com.Hmod.helper.Reference;
import com.Hmod.main.MainHary;
import com.Hmod.tileentity.HaryTileEntityChest;
import com.Hmod.tileentity.TileEntityInventoryBasic;

public class HarysBlocks {

	public static Block hary_block;
	public static Block hary_ore;
	public static Block hary_gui;
	public static Block hary_chest;
	
	public static void init() {

		hary_block = new BlocksHarys(Material.cloth).setUnlocalizedName("hary_block").setCreativeTab(MainHary.HaryT);
		hary_ore = new BlocksHarys(Material.iron).setUnlocalizedName("hary_ore").setCreativeTab(MainHary.HaryT);
		
		hary_gui = new hary_gui().setUnlocalizedName("hary_gui").setCreativeTab(MainHary.HaryT);
		hary_chest = new hary_chest(0).setUnlocalizedName("hary_chest").setCreativeTab(MainHary.HaryT);
		
		
	}

	public static void register() {

		GameRegistry.registerBlock(hary_block, hary_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hary_ore, hary_ore.getUnlocalizedName().substring(5));
		
		
		
		GameRegistry.registerBlock(hary_gui, hary_gui.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hary_chest, hary_chest.getUnlocalizedName().substring(5));
		
		
		
		 NetworkRegistry.INSTANCE.registerGuiHandler(MainHary.instance, GuiHandlerRegistry.getInstance());
		 GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerH(), GuiHandlerH.getGuiID());
	}

	public static void registerRenders(){
		
		registerRender(hary_block);
		registerRender(hary_ore);
	}
	public static void registerRender(Block block){
	
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
