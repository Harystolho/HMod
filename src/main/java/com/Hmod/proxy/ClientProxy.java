package com.Hmod.proxy;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;
import com.Hmod.gui.GuiHandler;
import com.Hmod.main.MainHary;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		HarysItems.registerRenders();
		HarysBlocks.registerRenders();
	}
	
	@Override
	public void registerNetworkStaff(){
		
		NetworkRegistry.INSTANCE.registerGuiHandler(MainHary.instance, new GuiHandler());
		
	}
}


