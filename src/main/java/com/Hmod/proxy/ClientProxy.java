package com.Hmod.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;
import com.Hmod.container.ContainerCustomFurnace;
import com.Hmod.gui.GuiHandler;
import com.Hmod.handler.GuiHandlerRegistry;
import com.Hmod.main.MainHary;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		HarysItems.registerRenders();
		HarysBlocks.registerRenders();
	}
	
	@Override
	public void registerNetworkStaff(){
		
		NetworkRegistry.INSTANCE.registerGuiHandler(MainHary.instance, new GuiHandler());
		
		
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandler(), ContainerCustomFurnace.GUI_FURNACE);
		
	}
}


