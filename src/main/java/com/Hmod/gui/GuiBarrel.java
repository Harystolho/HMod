package com.Hmod.gui;

import java.awt.Color;

import com.Hmod.helper.Reference;
import com.Hmod.tile_entity.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBarrel extends GuiContainer {

	public static final ResourceLocation BACKGROUND = new ResourceLocation(
			Reference.MODID + ":textures/gui/barrel.png");

	public GuiBarrel(Container container) {
		super(container);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x,
			int y) {
		// Bind the image texture of our custom container
		Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	// draw the foreground for the GUI - rendered after the slots, but before
	// the dragged items and tooltips
	// renders relative to the top left corner of the background
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRendererObj.drawString("Hary's Barrel", LABEL_XPOS, LABEL_YPOS, Color.darkGray
				.getRGB());
	}
	
}
