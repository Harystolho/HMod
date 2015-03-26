package com.Hmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.Hmod.container.ContainerBarrel;
import com.Hmod.container.ContainerCustomFurnace;
import com.Hmod.tile_entity.TileEntityBarrel;
import com.Hmod.tile_entity.TileEntityFurnaceH;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case ContainerBarrel.GUI_BARREL:
			return new ContainerBarrel(player.inventory,
					(TileEntityBarrel) world
							.getTileEntity(new BlockPos(x, y, z)));
		case ContainerCustomFurnace.GUI_FURNACE:
			BlockPos xyz = new BlockPos(x, y, z);
			TileEntity tileEntity = world.getTileEntity(xyz);
			if (tileEntity instanceof TileEntityFurnaceH) {
				TileEntityFurnaceH tileInventoryFurnace = (TileEntityFurnaceH) tileEntity;
			return new ContainerCustomFurnace(player.inventory, tileInventoryFurnace);
			}
			return null;
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case ContainerBarrel.GUI_BARREL:
			return new GuiBarrel(new ContainerBarrel(player.inventory,
					(TileEntityBarrel) world
							.getTileEntity(new BlockPos(x, y, z))));
		case ContainerCustomFurnace.GUI_FURNACE:
			BlockPos xyz = new BlockPos(x, y, z);
			TileEntity tileEntity = world.getTileEntity(xyz);
			if (tileEntity instanceof TileEntityFurnaceH) {
				TileEntityFurnaceH tileInventoryFurnace = (TileEntityFurnaceH) tileEntity;
				return new GuiHFurnace(player.inventory, tileInventoryFurnace);
			}
			return null;
		default:
			return null;
		}
	}

}
