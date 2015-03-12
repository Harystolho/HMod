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
				"AAA", "A A", "AAA", 'A', Blocks.obsidian);

		GameRegistry.addRecipe(new ItemStack(HarysItems.wrench_hary), " BB",
				" AB", "A  ", 'A', Items.stick, 'B', Blocks.obsidian);

		GameRegistry.addRecipe(new ItemStack(HarysItems.remov_hary), " CB",
				" AC", "A  ", 'A', Items.stick, 'B', Blocks.obsidian, 'C',
				Items.diamond);

	
		
	//Furnace
		
		GameRegistry.addSmelting(HarysBlocks.hary_ore, new ItemStack(HarysItems.ingot_hary, 2), 5F);
		
		
	}

}
