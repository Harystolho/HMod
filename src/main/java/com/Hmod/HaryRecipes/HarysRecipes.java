package com.Hmod.HaryRecipes;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HarysRecipes {

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(HarysBlocks.hary_block, 32),
				"AAA", "A A", "AAA", 'A', HarysItems.ingot_hary);

		GameRegistry.addRecipe(new ItemStack(HarysItems.wrench_hary), " BB",
				" AB", "A  ", 'A', Items.stick, 'B', Blocks.obsidian);

		GameRegistry.addRecipe(new ItemStack(HarysItems.remov_hary), " CB",
				" AC", "A  ", 'A', Items.stick, 'B', Blocks.obsidian, 'C',
				Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_pick), "ZZZ",
				" X ", " X ", 'X', HarysItems.ingot_hary, 'Z', HarysItems.ingot_compress_hary);

		GameRegistry.addRecipe(new ItemStack(HarysItems.ingot_compress_hary),
				"AAA", "ADA", "AAA", 'A', HarysItems.ingot_hary, 'D', Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_gear),
				"A A", " X ", "A A", 'A', HarysItems.ingot_compress_hary, 'X', Items.diamond);
		
		GameRegistry.addRecipe(new ItemStack(HarysBlocks.hary_furnace),
				"AAA", "ADA", "AAA", 'A', HarysItems.ingot_compress_hary, 'D', HarysItems.hary_gear);
		
		GameRegistry.addRecipe(new ItemStack(HarysItems.hary_food),
				" A ", "AXA", " A ", 'A', HarysItems.ingot_hary, 'X', HarysItems.hary_gear);
		
		GameRegistry.addSmelting(Blocks.diamond_block, new ItemStack(HarysItems.ingot_hary), 5F);
		
	}

}
