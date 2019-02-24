package de.ttryy.simplemacros.listener;

import de.ttryy.simplemacros.gui.MacroGui;
import de.ttryy.simplemacros.main.SimpleMacrosMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class InitGuiListener {
	
	@SuppressWarnings("unused")
	private final SimpleMacrosMod mod;
	
	public InitGuiListener(SimpleMacrosMod mod) {
		this.mod = mod;
	}
	
	@SubscribeEvent
	public void guiOpen(InitGuiEvent event) {
		if(! (event.getGui() instanceof GuiOptions)) {
			return;
		}

		GuiOptions optionsGui = (GuiOptions) event.getGui();
		
		event.addButton(new GuiButton(1001, optionsGui.width / 2 - 100, optionsGui.height / 6 + 18, 200, 20, "Macros") {
	         /**
	          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
	          */
	         public void onClick(double mouseX, double mouseY) {
	        	optionsGui.mc.gameSettings.saveOptions();
	            optionsGui.mc.displayGuiScreen(new MacroGui(optionsGui));
	         }
	      });

	}

}
