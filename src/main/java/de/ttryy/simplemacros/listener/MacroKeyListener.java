package de.ttryy.simplemacros.listener;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;

import de.ttryy.simplemacros.event.MacroEvent;
import de.ttryy.simplemacros.gui.GuiMacroEdit;
import de.ttryy.simplemacros.main.SimpleMacrosMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyReleasedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseReleasedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@OnlyIn(Dist.CLIENT)
public class MacroKeyListener {

	private final SimpleMacrosMod mod;
	private final ArrayList<Integer> pressedKeys;
	private final ArrayList<Integer> pressedMouse;
	
	private final Integer[] badKeys = {GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER, GLFW.GLFW_KEY_ESCAPE};
	int i;

	public MacroKeyListener(SimpleMacrosMod mod) {
		this.mod = mod;
		pressedKeys = Lists.newArrayList();
		pressedMouse = Lists.newArrayList();
	}

	@SubscribeEvent
	public void onGuiKeyPress(KeyboardKeyReleasedEvent.Pre event) {
		if(event.getGui() instanceof GuiMacroEdit) {
			
			GuiMacroEdit macroEdit = (GuiMacroEdit) event.getGui();
			
			if(macroEdit.isClicked()) {
				if(Arrays.asList(badKeys).contains(event.getKeyCode())) {
					macroEdit.updateKeyButton();
				}
				macroEdit.setKey(event.getKeyCode());
				macroEdit.setScanCode(event.getScanCode());
				macroEdit.setMouseKey(false);
				macroEdit.updateKeyButton();
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onGuiKeyPress(MouseReleasedEvent.Pre event) {
		if(event.getGui() instanceof GuiMacroEdit) {
			
			GuiMacroEdit macroEdit = (GuiMacroEdit) event.getGui();
			
			if(macroEdit.isClicked()) {
				if(Arrays.asList(badKeys).contains(event.getButton())) {
					macroEdit.updateKeyButton();
				}
				macroEdit.setKey(event.getButton());
				macroEdit.setMouseKey(true);
				macroEdit.updateKeyButton();
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {

		if (Minecraft.getInstance().currentScreen != null) {
			return;
		}

		checkForPressedKeys();

	}

	private void checkForPressedKeys() {

		long window = Minecraft.getInstance().mainWindow.getHandle();

		mod.getMacroManager().getMacros().forEach(macro -> {

			if (macro.isMouseKey()) {
				if (pressedMouse.contains(macro.getKey())) {
					if (GLFW.glfwGetMouseButton(window, macro.getKey()) == GLFW.GLFW_RELEASE) {
						MinecraftForge.EVENT_BUS.post(new MacroEvent(macro, true));
						pressedMouse.remove(macro.getKey());
					}
				} else if (GLFW.glfwGetMouseButton(window, macro.getKey()) == GLFW.GLFW_PRESS) {
					MinecraftForge.EVENT_BUS.post(new MacroEvent(macro, false));
					pressedMouse.add(macro.getKey());
				}
			} else {
				if (pressedKeys.contains(macro.getKey())) {
					if (GLFW.glfwGetKey(window, macro.getKey()) == GLFW.GLFW_RELEASE) {
						MinecraftForge.EVENT_BUS.post(new MacroEvent(macro, true));
						pressedKeys.remove(macro.getKey());
					}
				} else if (GLFW.glfwGetKey(window, macro.getKey()) == GLFW.GLFW_PRESS) {
					MinecraftForge.EVENT_BUS.post(new MacroEvent(macro, false));
					pressedKeys.add(macro.getKey());
				}
			}
		});
	}

}
