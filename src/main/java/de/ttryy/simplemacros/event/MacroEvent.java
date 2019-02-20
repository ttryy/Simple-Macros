package de.ttryy.simplemacros.event;

import de.ttryy.simplemacros.util.Macro;
import net.minecraftforge.eventbus.api.Event;

public class MacroEvent extends Event{
	
	private final Macro macro;
	private final boolean released;
	
	public MacroEvent(Macro macro, boolean released) {
		this.macro = macro;
		this.released = released;
	}

	public Macro getMacro() {
		return macro;
	}

	public boolean isReleased() {
		return released;
	}

}
