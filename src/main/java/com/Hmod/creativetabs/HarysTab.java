package com.Hmod.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.Hmod.HaryItems.HarysItems;


public class HarysTab extends CreativeTabs {

	public HarysTab(String label)
    {	
        super(label);
    }

	@Override
    public Item getTabIconItem()
    {
        return HarysItems.wrench_hary;
    }
}
