package com.Hmod.tile_entity;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBarrel extends TileEntity {

	public InventoryBasic inventory;
	private ItemStack[] itemStacks = new ItemStack[63];

	public TileEntityBarrel() {
		inventory = new InventoryBasic("TileEntityBarrel", false, 63);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
			if (this.inventory.getStackInSlot(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				this.inventory.getStackInSlot(i).writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		compound.setTag("ItemStacks", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("ItemStacks", 10);

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			byte b = tag.getByte("Slot");

			if (b >= 0 && b < this.inventory.getSizeInventory()	) {
 				this.inventory.setInventorySlotContents(b, ItemStack.loadItemStackFromNBT(tag));
			}

		}

	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		final int METADATA = 0;
		return new S35PacketUpdateTileEntity(this.pos, METADATA, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
