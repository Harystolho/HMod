package com.Hmod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.Hmod.tile_entity.TileEntityFurnace;

public class ContainerFurnace extends Container {

	public static final int GUI_FURNACE = 1;
	private final IInventory tileFurnace;
	
	private int field_178152_f;
    private int field_178153_g;
    private int field_178154_h;
    private int field_178155_i;

	public ContainerFurnace(IInventory playerInv, TileEntityFurnace furnace) {
		int i = -18;
		int j;
		int k;

		int index = 0;

		this.tileFurnace = furnace.inventory;

		// TileEntiry inventorySlots
		this.addSlotToContainer(new Slot(furnace.inventory, index++, 44,
				17 + 18));
		this.addSlotToContainer(new SlotFurnaceOutput(null, furnace.inventory,
				index++, 44 + 4 * 18, 17 + 18));
		this.addSlotToContainer(new SlotFurnaceFuel(furnace.inventory, index++,
				80, 56));

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9,
						8 + k * 18, 102 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 160 + i));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting listener) {
		super.addCraftingToCrafters(listener);
		listener.func_175173_a(this, this.tileFurnace);
	}
	
	 public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        for (int i = 0; i < this.crafters.size(); ++i)
	        {
	            ICrafting icrafting = (ICrafting)this.crafters.get(i);

	            if (this.field_178152_f != this.tileFurnace.getField(2))
	            {
	                icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
	            }

	            if (this.field_178154_h != this.tileFurnace.getField(0))
	            {
	                icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
	            }

	            if (this.field_178155_i != this.tileFurnace.getField(1))
	            {
	                icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
	            }

	            if (this.field_178153_g != this.tileFurnace.getField(3))
	            {
	                icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
	            }
	        }

	        this.field_178152_f = this.tileFurnace.getField(2);
	        this.field_178154_h = this.tileFurnace.getField(0);
	        this.field_178155_i = this.tileFurnace.getField(1);
	        this.field_178153_g = this.tileFurnace.getField(3);
	    }
	
	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int id, int data)
	    {
	        this.tileFurnace.setField(id, data);
	    }


	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;

}
}
