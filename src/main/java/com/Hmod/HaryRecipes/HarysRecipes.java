package com.Hmod.HaryRecipes;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HarysRecipes {

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(HarysBlocks.hary_block, 32),
				"AAA", "A A", "AAA", 'A', HarysItems.ingot_hary);

		/*
		 * GameRegistry.addRecipe(new ItemStack(HarysItems.wrench_hary), " BB",
		 * " AB", "A  ", 'A', Items.stick, 'B', Blocks.obsidian);
		 * 
		 * GameRegistry.addRecipe(new ItemStack(HarysItems.remov_hary), " CB",
		 * " AC", "A  ", 'A', Items.stick, 'B', Blocks.obsidian, 'C',
		 * Items.diamond);
		 */

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_pick), "ZZZ",
				" X ", " X ", 'X', HarysItems.ingot_hary, 'Z',
				HarysItems.ingot_compress_hary);

		GameRegistry.addRecipe(new ItemStack(HarysItems.ingot_compress_hary),
				"AAA", "ADA", "AAA", 'A', HarysItems.ingot_hary, 'D',
				Items.diamond);

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_gear), "A A",
				" X ", "A A", 'A', HarysItems.ingot_compress_hary, 'X',
				Items.diamond);

		GameRegistry.addRecipe(new ItemStack(HarysBlocks.hary_furnace), "AAA",
				"ADA", "AAA", 'A', HarysItems.ingot_compress_hary, 'D',
				HarysItems.hary_gear);

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_food, 2), " A ",
				"AXA", " A ", 'A', HarysItems.ingot_hary, 'X',
				HarysItems.hary_gear);

		GameRegistry.addRecipe(new ItemStack(HarysBlocks.hary_barrel), " A ",
				"AXA", " A ", 'A', HarysItems.ingot_compress_hary, 'X',
				Blocks.chest);

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_sword), " A ",
				" A ", " X ", 'A', HarysItems.ingot_compress_hary, 'X',
				HarysItems.hary_gear);

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_bag), "AYA",
				"AXA", "AAA", 'A', Items.leather, 'X',
				HarysBlocks.hary_furnace, 'Y', Items.ender_pearl);

		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_throw, 18), " A ",
				"A A", " A ", 'A', HarysItems.ingot_hary);

		GameRegistry.addShapelessRecipe(new ItemStack(HarysItems.hary_dust),
				new Object[] { new ItemStack(Items.iron_ingot, 1),
						new ItemStack(Items.diamond, 1) });

		GameRegistry.addSmelting(HarysItems.hary_dust, new ItemStack(
				HarysItems.ingot_hary, 2), 5F);

	}

}
