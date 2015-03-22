package com.Hmod.HaryBlocks;

import com.Hmod.container.ContainerBarrel;
import com.Hmod.main.MainHary;
import com.Hmod.tile_entity.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

public class hary_barrel extends BlockContainer {

	public hary_barrel() {
		super(Material.wood);

		this.setCreativeTab(MainHary.HaryT);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
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
			if (tileentity instanceof TileEntityBarrel) {
				playerIn.openGui(MainHary.instance, ContainerBarrel.GUI_BARREL,
						worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}

		return true;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

}
