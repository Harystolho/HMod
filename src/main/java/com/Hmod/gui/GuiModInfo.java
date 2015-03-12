package com.Hmod.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiModInfo extends GuiScreen {

	private GuiSlider mySlider;
	private GuiTextField textField;
	private GuiMainMenu menu;
	
	public GuiModInfo(GuiMainMenu menu) {
		this.menu = menu; 
	}

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 - 75, 100,
				20, "CLOSE"));
		buttonList.add(mySlider = new GuiSlider(1, width / 2 - 50,
				height / 2 - 25, 100, 20, "PORCENT: ", " %", 0, 100, 0, true,
				true));

		textField = new GuiTextField(2, fontRendererObj, width / 2 - 50,
				height / 2 + 30, 100, 20);
		textField.setMaxStringLength(15);
		textField.setFocused(true);
		textField.setCanLoseFocus(false);
	}

	@Override
	public void updateScreen() {

		textField.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		textField.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		fontRendererObj.drawString("This is a GUI", width / 2 - 40,
				height / 2 - 100, 16777215);
		textField.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			Minecraft.getMinecraft().currentScreen = menu;
			break;

		}
	}

}
