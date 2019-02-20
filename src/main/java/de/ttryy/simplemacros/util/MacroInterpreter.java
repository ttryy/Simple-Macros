package de.ttryy.simplemacros.util;

import java.util.StringTokenizer;

import com.mojang.text2speech.Narrator;

import net.minecraft.client.Minecraft;

public class MacroInterpreter {

	public static void executeMacro(Macro macro) {
		StringTokenizer sT = new StringTokenizer(macro.getMacro(), ";");
		
		while(sT.hasMoreTokens()) {
			String command = sT.nextToken();
			if(!runNarrorator(command)) {
				chatOrExecute(command);
			}
		}
	}

	public static boolean runNarrorator(String text) {
		if (text.startsWith(".tts ")) {
			Narrator.getNarrator().say(text.replaceFirst("(.tts )", ""));
			return true;
		} else return false;
	}
	
	public static void chatOrExecute(String text) {
		Minecraft.getInstance().player.sendChatMessage(text);
	}

}
