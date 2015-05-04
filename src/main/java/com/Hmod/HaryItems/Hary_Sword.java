package com.Hmod.HaryItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.Hmod.main.MainHary;

public class Hary_Sword extends ItemSword {

	public Map<Integer, Integer> enchMap = new HashMap<Integer, Integer>();

	public Hary_Sword(ToolMaterial material) {
		super(material);

		setUnlocalizedName("hary_sword");
		setCreativeTab(MainHary.HaryT);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		enchMap.put(Enchantment.sharpness.effectId, 6);
		enchMap.put(Enchantment.looting.effectId, 3);
		EnchantmentHelper.setEnchantments(enchMap, stack);

		NBTTagList nbttaglist = this.getEnchantments(stack);

		if (nbttaglist != null) {
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
				short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");

				if (Enchantment.getEnchantmentById(short1) != null) {
					tooltip.add(Enchantment.getEnchantmentById(short1)
							.getTranslatedName(short2));
				}
			}
		}

	}

	public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
		NBTTagList nbttaglist = this.getEnchantments(stack);
		boolean flag = true;

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);

			if (nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
				if (nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel) {
					nbttagcompound.setShort("lvl",
							(short) enchantment.enchantmentLevel);
				}

				flag = false;
				break;
			}
		}

		if (flag) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setShort("id",
					(short) enchantment.enchantmentobj.effectId);
			nbttagcompound1.setShort("lvl",
					(short) enchantment.enchantmentLevel);
			nbttaglist.appendTag(nbttagcompound1);
		}

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.getTagCompound().setTag("StoredEnchantments", nbttaglist);
	}

	public NBTTagList getEnchantments(ItemStack stack) {
		NBTTagCompound nbttagcompound = stack.getTagCompound();
		return nbttagcompound != null
				&& nbttagcompound.hasKey("StoredEnchantments", 9) ? (NBTTagList) nbttagcompound
				.getTag("StoredEnchantments") : new NBTTagList();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		if (playerIn.isSneaking()) {
			playerIn.setHealth(playerIn.getHealth() + 2);
		}
		return itemStackIn;
	}

}
