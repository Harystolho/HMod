package com.Hmod.HaryItems;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Heal_Hary extends ItemFood {

	private boolean alwaysEdible = false;

	public Heal_Hary(int amount, boolean isWolfFood) {
		super(amount, isWolfFood);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn,
			EntityPlayer playerIn) {
		--stack.stackSize;
		playerIn.getFoodStats().addStats(this, stack);
		worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F,
				worldIn.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(stack, worldIn, playerIn);
		playerIn.setHealth(playerIn.getMaxHealth());
		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		if (playerIn.canEat(this.alwaysEdible) || playerIn.canEat(!this.alwaysEdible)) {
			playerIn.setItemInUse(itemStackIn,
					this.getMaxItemUseDuration(itemStackIn));
		}

		return itemStackIn;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		
		/*if (stack.getTagCompound() == null) {

			stack.setTagCompound(new NBTTagCompound());
		}
		//NBTTagCompound nbt = new NBTTagCompound();
		//nbt.setString("info", "Restore full life");
		//stack.getTagCompound().setTag("heal", nbt);
*/		tooltip.add("Restore full life");
		stack.setStackDisplayName(EnumChatFormatting.BLUE + "Healer Fish");
		
	}
}
