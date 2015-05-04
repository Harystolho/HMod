package com.Hmod.HaryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.helper.Reference;
import com.Hmod.main.MainHary;
import com.Hmod.nothing.GuiHandlerH9x1;
import com.Hmod.nothing.GuiHandlerRegistry9x1;
import com.Hmod.nothing.HaryTileEntityChest;
import com.Hmod.nothing.TileEntityInventory9x1;

public class HarysBlocks {

	public static Block hary_block;
	public static Block hary_ore;
	public static Block hary_barrel;
	public static Block hary_furnace;
	
	public static void init() {

		hary_block = new BaseBlock(Material.piston).setUnlocalizedName(
				"hary_block").setCreativeTab(MainHary.HaryT);
		hary_ore = new BaseBlock(Material.iron)
				.setUnlocalizedName("hary_ore").setCreativeTab(MainHary.HaryT);

		hary_barrel = new HaryBarrel().setUnlocalizedName("hary_barrel");
		hary_furnace = new HaryFurnace().setUnlocalizedName("hary_furnace");

	}

	public static void register() {

		GameRegistry.registerBlock(hary_block, hary_block.getUnlocalizedName()
				.substring(5));
		GameRegistry.registerBlock(hary_ore, hary_ore.getUnlocalizedName()
				.substring(5));

		GameRegistry.registerBlock(hary_barrel, hary_barrel
				.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(hary_furnace, hary_furnace
				.getUnlocalizedName().substring(5));

	}

	public static void registerRenders() {

		registerRender(hary_block);
		registerRender(hary_ore);
		registerRender(hary_barrel);
		registerRender(hary_furnace);
	}

	public static void registerRender(Block block) {

		Item item = Item.getItemFromBlock(block);
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						item,
						0,
						new ModelResourceLocation(Reference.MODID + ":"
								+ item.getUnlocalizedName().substring(5),
								"inventory"));
	}

}
