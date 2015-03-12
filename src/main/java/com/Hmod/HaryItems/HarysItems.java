package com.Hmod.HaryItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.Hmod.helper.Reference;
import com.Hmod.main.MainHary;

public class HarysItems {

	public static Item wrench_hary;
	public static Item remov_hary;
	public static Item ingot_hary;
	public static Item coord_hary;

	public static void init() {
		
		
		wrench_hary = new wrench_hary().setCreativeTab(MainHary.HaryT);
		remov_hary = new remov_hary().setCreativeTab(MainHary.HaryT);
		ingot_hary = new Item().setUnlocalizedName("ingot_hary").setCreativeTab(MainHary.HaryT);
		coord_hary = new coord_hary().setCreativeTab(MainHary.HaryT);
		
	}

	public static void register() {

		GameRegistry.registerItem(wrench_hary, wrench_hary.getUnlocalizedName()
				.substring(5));
		GameRegistry.registerItem(remov_hary, remov_hary.getUnlocalizedName()
				.substring(5));
		GameRegistry.registerItem(ingot_hary, ingot_hary.getUnlocalizedName()
				.substring(5));
		GameRegistry.registerItem(coord_hary, coord_hary.getUnlocalizedName()
				.substring(5));
	}

	public static void registerRenders() {

		registerRender(wrench_hary);
		registerRender(remov_hary);
		registerRender(ingot_hary);
		registerRender(coord_hary);
		
	}

	public static void registerRender(Item item) {
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		
	}
}
