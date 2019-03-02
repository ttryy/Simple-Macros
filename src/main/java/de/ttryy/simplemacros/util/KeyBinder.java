package de.ttryy.simplemacros.util;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;

import de.ttryy.simplemacros.gui.MacroGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBinder {
	
	private ArrayList<KeyBinding> keyBindings;
	
	public KeyBinder() {
		keyBindings = Lists.newArrayList();
		setKeyBindings();
	}
	
	private void setKeyBindings() {
		keyBindings.add(new SMKeyBinding("Macro Menu", GLFW.GLFW_KEY_O, "Macro") {
			@Override
			public void executeKeyBinding() {
				Minecraft.getInstance().displayGuiScreen(new MacroGui());
			}
		});
		
		keyBindings.forEach(keyBinding -> ClientRegistry.registerKeyBinding(keyBinding));
	}

	public ArrayList<KeyBinding> getKeyBindings() {
		return keyBindings;
	}
	
}
