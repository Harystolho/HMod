package com.Hmod.HaryRecipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;
import com.google.common.collect.Maps;

public class HaryFurnaceRecipes {
	private static final HaryFurnaceRecipes smeltingBase = new HaryFurnaceRecipes();
	/** The list of smelting results. */
    private Map smeltingList = Maps.newHashMap();
    /** A list which contains how many experience points each recipe output will give. */
    private Map experienceList = Maps.newHashMap();


	public static HaryFurnaceRecipes instance() {
		return smeltingBase;
	}

	private HaryFurnaceRecipes() {
		this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
        this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
        this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
        this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
        this.addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
        this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
        this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
        this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
        this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
        this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1F);
        this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
        this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
        this.addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
        this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
        this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
        this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
        this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
        this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F);
        this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
        this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
        this.addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
        this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
        
        // My recipes
        
        this.addSmeltingRecipeForBlock(HarysBlocks.hary_ore, new ItemStack(HarysItems.ingot_hary, 2), 10F);
        this.addSmeltingRecipeForBlock(Blocks.diamond_block, new ItemStack(HarysItems.hary_gear, 4), 20F);
	}

	 public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	    {
	        this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	    }

	    /**
	     * Adds a smelting recipe using an Item as the input item.
	     *  
	     * @param input The input Item to be used for this recipe.
	     * @param stack The output ItemStack for this recipe.
	     * @param experience The amount of experience this recipe will give the player.
	     */
	    public void addSmelting(Item input, ItemStack stack, float experience)
	    {
	        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	    }

	    /**
	     * Adds a smelting recipe using an ItemStack as the input for the recipe.
	     *  
	     * @param input The input ItemStack for this recipe.
	     * @param stack The output ItemStack for this recipe.
	     * @param experience The amount of experience this recipe will give the player.
	     */
	    public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
	    {
	        this.smeltingList.put(input, stack);
	        this.experienceList.put(stack, Float.valueOf(experience));
	    }

	    /**
	     * Returns the smelting result of an item.
	     */
	    public ItemStack getSmeltingResult(ItemStack stack)
	    {
	        Iterator iterator = this.smeltingList.entrySet().iterator();
	        Entry entry;

	        do
	        {
	            if (!iterator.hasNext())
	            {
	                return null;
	            }

	            entry = (Entry)iterator.next();
	        }
	        while (!this.compareItemStacks(stack, (ItemStack)entry.getKey()));

	        return (ItemStack)entry.getValue();
	    }

	    /**
	     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
	     */
	    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	    {
	        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	    }

	    public Map getSmeltingList()
	    {
	        return this.smeltingList;
	    }

	    public float getSmeltingExperience(ItemStack stack)
	    {
	        float ret = stack.getItem().getSmeltingExperience(stack);
	        if (ret != -1) return ret;

	        Iterator iterator = this.experienceList.entrySet().iterator();
	        Entry entry;

	        do
	        {
	            if (!iterator.hasNext())
	            {
	                return 0.0F;
	            }

	            entry = (Entry)iterator.next();
	        }
	        while (!this.compareItemStacks(stack, (ItemStack)entry.getKey()));

	        return ((Float)entry.getValue()).floatValue();
	    }
}
