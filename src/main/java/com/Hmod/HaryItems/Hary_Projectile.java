package com.Hmod.HaryItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.Hmod.main.MainHary;

public class Hary_Projectile extends Item {

	public Hary_Projectile() {
		setMaxStackSize(18);
		setUnlocalizedName("hary_projectile");
		setCreativeTab(MainHary.HaryT);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		// IMPORTANT! Only spawn new entities on the server. If the world is not
		// remote,
		// that means you are on the server:
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityArrow(world, player, 10));
		}

		return stack;
	}

}
