package com.Hmod.HaryRecipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

public class HaryFurnaceRecipes {
	private static final HaryFurnaceRecipes smeltingBase = new HaryFurnaceRecipes();
	private Map smeltingList = new HashMap();
	private Map experienceList = new HashMap();

	public static HaryFurnaceRecipes instance() {
		return smeltingBase;
	}

	private HaryFurnaceRecipes() {
		  this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
	      this.addSmelting(HarysItems.ingot_hary, new ItemStack(Items.diamond), 0.35F);
	}

	public void addSmeltingRecipeForBlock(Block input, ItemStack stack,
			float experience) {
		this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	}

	public void addSmelting(Item input, ItemStack stack, float experience) {
		this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack,
				experience);
	}

	public void addSmeltingRecipe(ItemStack input, ItemStack stack,
			float experience) {
		this.smeltingList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	public ItemStack getSmeltingResult(ItemStack stack) {
		Iterator iterator = this.smeltingList.entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Entry) iterator.next();
		} while (!this.compareItemStacks(stack, (ItemStack) entry.getKey()));

		return (ItemStack) entry.getValue();
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1
						.getMetadata());
	}

	public Map getSmeltingList() {
		return this.smeltingList;
	}

	public float getSmeltingExperience(ItemStack stack) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		Iterator iterator = this.experienceList.entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext()) {
				return 0.0F;
			}

			entry = (Entry) iterator.next();
		} while (!this.compareItemStacks(stack, (ItemStack) entry.getKey()));

		return ((Float) entry.getValue()).floatValue();
	}
}
