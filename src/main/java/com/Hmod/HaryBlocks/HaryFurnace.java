package com.Hmod.HaryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.Hmod.container.ContainerFurnace;
import com.Hmod.main.MainHary;
import com.Hmod.tile_entity.TileEntityFurnace;

public class HaryFurnace extends BlockContainer {

	public HaryFurnace() {
		super(Material.wood);

		this.setCreativeTab(MainHary.HaryT);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFurnace();
	}

	@Override
	public boolean onBlockActivated(World worldIn,
			net.minecraft.util.BlockPos pos,
			net.minecraft.block.state.IBlockState state,
			net.minecraft.entity.player.EntityPlayer playerIn,
			net.minecraft.util.EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (!worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityFurnace) {
				playerIn.openGui(MainHary.instance,
						ContainerFurnace.GUI_FURNACE, worldIn, pos.getX(),
						pos.getY(), pos.getZ());
			}
		}

		return true;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

}
