package de.ttryy.simplemacros.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MacroGui extends GuiScreen{
	
	private GuiMacroList makroList;
	private MacroGui macroGui;
	
	public MacroGui() {
		this.macroGui = this;
	}
	
	@Override
	protected void initGui() {
		this.addButton(new GuiButton(1000, width / 2 - 100, (int) (height*0.05) + 15, "Create a new Macro") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				Minecraft.getInstance().displayGuiScreen(new GuiMacroEdit(macroGui, null));
				super.onClick(mouseX, mouseY);
			}
		});
		
		this.addButton(new GuiButton(200, this.width / 2 - 100, this.height - 29, "Done") {
			/**
			 * Called when the left mouse button is pressed over this button. This method is
			 * specific to GuiButton.
			 */
			public void onClick(double mouseX, double mouseY) {
				macroGui.close();
			}
		});
		
		this.makroList = new GuiMacroList(this, this.mc);
	    this.children.add(makroList);
	    this.setFocused(makroList);
		super.initGui();
	}
	
	public void render(int mouseX, int mouseY, float partialTicks) {
	      this.drawDefaultBackground();
	      this.makroList.drawScreen(mouseX, mouseY, partialTicks);
	      this.drawCenteredString(this.fontRenderer, "Macros", this.width / 2, 8, 16777215);
	      super.render(mouseX, mouseY, partialTicks);
	   }
	

}
