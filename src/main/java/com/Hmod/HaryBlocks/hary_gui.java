package com.Hmod.HaryBlocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.Hmod.gui.GuiHandlerH9x1;
import com.Hmod.main.MainHary;
import com.Hmod.tile_entity.TileEntityInventory9x1;

public class hary_gui extends BlockContainer {

	public hary_gui() {
		super(Material.rock);

	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityInventory9x1();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		// Uses the gui handler registered to your mod to open the gui for the
		// given gui id
		// open on the server side only (not sure why you shouldn't open client
		// side too... vanilla doesn't, so we better not either)
		if (worldIn.isRemote)
			return true;
		playerIn.openGui(MainHary.instance,
				GuiHandlerH9x1.getGuiID(), worldIn, pos.getX(), pos.getY(),
				pos.getZ());
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		IInventory inventory = worldIn.getTileEntity(pos) instanceof IInventory ? (IInventory) worldIn
				.getTileEntity(pos) : null;
		if (inventory != null) {
			// For each slot in the inventory
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				// If the slot is not empty
				if (inventory.getStackInSlot(i) != null) {
					// Create a new entity item with the item stack in the slot
					EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5,
							pos.getY() + 0.5, pos.getZ() + 0.5,
							inventory.getStackInSlot(i));
					// Apply some random motion to the item
					float multiplier = 0.1f;
					float motionX = worldIn.rand.nextFloat() - 0.5f;
					float motionY = worldIn.rand.nextFloat() - 0.5f;
					float motionZ = worldIn.rand.nextFloat() - 0.5f;
					item.motionX = motionX * multiplier;
					item.motionY = motionY * multiplier;
					item.motionZ = motionZ * multiplier;
					// Spawn the item in the world
					worldIn.spawnEntityInWorld(item);
				}
			}
			// Clear the inventory so nothing else (such as another mod) can do
			// anything with the items
			inventory.clear();
		}
		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.SOLID;
	}

	// used by the renderer to control lighting and visibility of other blocks.
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	// used by the renderer to control lighting and visibility of other blocks,
	// also by
	// (eg) wall or fence to control whether the fence joins itself to this
	// block
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isFullCube() {
		return false;
	}

	// render using a BakedModel
	// not strictly required because the default (super method) is 3.
	@Override
	public int getRenderType() {
		return 3;
	}

}
