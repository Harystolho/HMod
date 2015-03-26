package com.Hmod.HaryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.Hmod.container.ContainerCustomFurnace;
import com.Hmod.main.MainHary;
import com.Hmod.nothing.HaryTileEntityChest;
import com.Hmod.tile_entity.TileEntityFurnaceH;

public class HaryFurnace extends BlockContainer {

	public HaryFurnace() {
		super(Material.rock);
		this.setCreativeTab(MainHary.HaryT);

		this.setDefaultState(this.blockState.getBaseState().withProperty(
				FACING, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFurnaceH();
	}

	// Called when the block is right clicked
	// In this block it is used to open the blocks gui when right clicked by a
	// player
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
		playerIn.openGui(MainHary.instance, ContainerCustomFurnace.GUI_FURNACE,
				worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	// This is where you can do something when the block is broken. In this case
	// drop the inventory's contents
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos,
					(IInventory) tileEntity);
		}
		// if (inventory != null){
		// // For each slot in the inventory
		// for (int i = 0; i < inventory.getSizeInventory(); i++){
		// // If the slot is not empty
		// if (inventory.getStackInSlot(i) != null)
		// {
		// // Create a new entity item with the item stack in the slot
		// EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5,
		// pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i));
		//
		// // Apply some random motion to the item
		// float multiplier = 0.1f;
		// float motionX = worldIn.rand.nextFloat() - 0.5f;
		// float motionY = worldIn.rand.nextFloat() - 0.5f;
		// float motionZ = worldIn.rand.nextFloat() - 0.5f;
		//
		// item.motionX = motionX * multiplier;
		// item.motionY = motionY * multiplier;
		// item.motionZ = motionZ * multiplier;
		//
		// // Spawn the item in the world
		// worldIn.spawnEntityInWorld(item);
		// }
		// }
		//
		// // Clear the inventory so nothing else (such as another mod) can do
		// anything with the items
		// inventory.clear();
		// }
		// Super MUST be called last because it removes the tile entity
		super.breakBlock(worldIn, pos, state);
	}

	/*
	 * // The code below isn't necessary for illustrating the Inventory Furnace
	 * // concepts, it's just used for rendering. // For more background
	 * information see MBE03 // we will give our Block a property which tracks
	 * the number of burning // sides, 0 - 4. // This will affect the appearance
	 * of the block model, but does not need to // be stored in metadata (it's
	 * stored in // the tileEntity) so we only need to implement getActualState.
	 * // getStateFromMeta, getMetaFromState aren't required // but we give
	 * defaults anyway because the base class getMetaFromState gives // an error
	 * if we don't // update the block state depending on the number of slots
	 * which contain // burning fuel
	 * 
	 * @Override public IBlockState getActualState(IBlockState state,
	 * IBlockAccess worldIn, BlockPos pos) { TileEntity tileEntity =
	 * worldIn.getTileEntity(pos); if (tileEntity instanceof TileEntityFurnaceH)
	 * { TileEntityFurnaceH tileInventoryFurnace = (TileEntityFurnaceH)
	 * tileEntity; int burningSlots =
	 * tileInventoryFurnace.numberOfBurningFuelSlots(); burningSlots =
	 * MathHelper.clamp_int(burningSlots, 0, 4); return
	 * getDefaultState().withProperty(BURNING_SIDES_COUNT, burningSlots); }
	 * return state; }
	 * 
	 * @Override public IBlockState getStateFromMeta(int meta) { return
	 * this.getDefaultState(); // return
	 * this.getDefaultState().withProperty(BURNING_SIDES_COUNT, //
	 * Integer.valueOf(meta)); }
	 * 
	 * @Override public int getMetaFromState(IBlockState state) { return 0; //
	 * return ((Integer)state.getValue(BURNING_SIDES_COUNT)).intValue(); }
	 * 
	 * // necessary to define which properties your blocks use // will also
	 * affect the variants listed in the blockstates model file. See // MBE03
	 * for more info.
	 * 
	 * @Override protected BlockState createBlockState() { return new
	 * BlockState(this, new IProperty[] { BURNING_SIDES_COUNT }); }
	 * 
	 * 
	 * public static final PropertyInteger BURNING_SIDES_COUNT = PropertyInteger
	 * .create("burning_sides_count", 0, 4); // change the furnace emitted light
	 * ("block light") depending on how many // slots are burning
	 */

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	public static final PropertyDirection FACING = PropertyDirection.create(
			"facing", EnumFacing.Plane.HORIZONTAL);

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			Block block = worldIn.getBlockState(pos.north()).getBlock();
			Block block1 = worldIn.getBlockState(pos.south()).getBlock();
			Block block2 = worldIn.getBlockState(pos.west()).getBlock();
			Block block3 = worldIn.getBlockState(pos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock()
					&& !block1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock()
					&& !block.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && block2.isFullBlock()
					&& !block3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && block3.isFullBlock()
					&& !block2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing),
					2);
		}
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		boolean keepInventory = true;

		if (active) {
			worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState()
					.withProperty(FACING, iblockstate.getValue(FACING)), 3);
			worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState()
					.withProperty(FACING, iblockstate.getValue(FACING)), 3);
		} else {
			worldIn.setBlockState(pos, Blocks.furnace.getDefaultState()
					.withProperty(FACING, iblockstate.getValue(FACING)), 3);
			worldIn.setBlockState(pos, Blocks.furnace.getDefaultState()
					.withProperty(FACING, iblockstate.getValue(FACING)), 3);
		}

		keepInventory = false;

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING,
				placer.getHorizontalFacing().getOpposite());
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	@SideOnly(Side.CLIENT)
	static final class SwitchEnumFacing {
		static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
		private static final String __OBFID = "CL_00002111";

		static {
			try {
				FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 1;
			} catch (NoSuchFieldError var4) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
			} catch (NoSuchFieldError var3) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (NoSuchFieldError var1) {
				;
			}
		}
	}

	private static final int FOUR_SIDE_LIGHT_VALUE = 15; // light value for four
															// sides burning
	private static final int ONE_SIDE_LIGHT_VALUE = 8; // light value for a
														// single side burning

	public int getLightValue(IBlockAccess world, BlockPos pos) {
		int lightValue = 0;
		IBlockState blockState = getActualState(getDefaultState(), world, pos);
		int burningSides = 4;
		if (burningSides == 0) {
			lightValue = 0;
		} else {
			// linearly interpolate the light value depending on how many slots
			// are burning
			lightValue = ONE_SIDE_LIGHT_VALUE
					+ (int) ((FOUR_SIDE_LIGHT_VALUE - ONE_SIDE_LIGHT_VALUE)
							/ (4.0 - 1.0) * burningSides);
		}
		lightValue = MathHelper.clamp_int(lightValue, 0, FOUR_SIDE_LIGHT_VALUE);
		return lightValue;
	}

	// the block will render in the SOLID layer. See
	// http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html
	// for more information.
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
