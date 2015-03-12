package com.Hmod.proxy;

import com.Hmod.HaryBlocks.HarysBlocks;
import com.Hmod.HaryItems.HarysItems;

import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		HarysItems.registerRenders();
		HarysBlocks.registerRenders();
	}
}
