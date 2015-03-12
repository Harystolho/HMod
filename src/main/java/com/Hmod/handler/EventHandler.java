package com.Hmod.handler;

import com.Hmod.gui.GuiModInfo;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	@SubscribeEvent
	public void onPlayerSleep(PlayerSleepInBedEvent event){
		
	}
	
	@SubscribeEvent
	public void onGuiOpened(GuiOpenEvent event){
		if(event.gui instanceof GuiMainMenu){
			event.gui = new GuiModInfo((GuiMainMenu) event.gui);
			
		}
	}
	
}