package de.ttryy.simplemacros.util;

import net.minecraft.client.settings.KeyBinding;

public abstract class SMKeyBinding extends KeyBinding implements IKeyBinding{

	public SMKeyBinding(String description, int keyCode, String category) {
		super(description, keyCode, category);
	}

}
