package de.ttryy.simplemacros.listener;

import de.ttryy.simplemacros.event.MacroEvent;
import de.ttryy.simplemacros.main.SimpleMacrosMod;
import de.ttryy.simplemacros.util.Macro;
import de.ttryy.simplemacros.util.MacroInterpreter;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MacroListener {
	
	@SuppressWarnings("unused")
	private final SimpleMacrosMod mod;
	
	public MacroListener(SimpleMacrosMod mod) {
		this.mod = mod;
	}
	
	@SubscribeEvent
	public void onMacro(MacroEvent event) {
		
		if(!event.isReleased()) {
			return;
		}
		
		Macro macro = event.getMacro();
		MacroInterpreter.executeMacro(macro);
		
	}
	
}
