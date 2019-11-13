package de.ttryy.simplemacros.util;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.ttryy.simplemacros.manager.JsonManager;
import net.minecraft.client.Minecraft;

public class KeyNames {
	
	public static Map<Integer, String> keyNames = new HashMap<Integer, String>(){
		{
			put(0,"Mouse 1");
			put(1,"Mouse 2");
			put(2,"Mouse 3");
			put(3,"Mouse 5");
			put(4,"Mouse 6");
			put(5,"Mouse 7");
			put(6,"Mouse 8");
			put(7,"Mouse 9");
			put(32,"Space");
			put(39,"´");
			put(44,",");
			put(45,"-");
			put(46,".");
			put(47,"/");
			put(59,";");
			put(61,"=");
			put(91,"[");
			put(92,"\\");
			put(93,"]");
			put(96,"`");
			put(256,"Esc");
			put(257,"Enter");
			put(258,"Tab");
			put(259,"Backspace");
			put(260,"Ins");
			put(261,"Del");
			put(262,"Right");
			put(263,"Left");
			put(264,"Down");
			put(265,"Up");
			put(266,"PageUp");
			put(267,"PageDown");
			put(268,"Home");
			put(269,"End");
			put(280,"CpsLock");
			put(281,"ScrollLock");
			put(282,"NumLock");
			put(283,"Print");
			put(284,"Pause");
			put(290,"F1");
			put(291,"F2");
			put(292,"F3");
			put(293,"F4");
			put(294,"F5");
			put(295,"F6");
			put(296,"F7");
			put(297,"F8");
			put(298,"F9");
			put(299,"F10");
			put(300,"F11");
			put(301,"F12");
			put(302,"F13");
			put(303,"F14");
			put(304,"F15");
			put(305,"F16");
			put(306,"F17");
			put(307,"F18");
			put(308,"F19");
			put(309,"F20");
			put(310,"F21");
			put(311,"F22");
			put(312,"F23");
			put(313,"F24");
			put(314,"F25");
			put(335,"Enter");
			put(340,"L Shift");
			put(341,"L Control");
			put(342,"L Alt");
			put(343,"L Super");
			put(344,"R Shift");
			put(345,"R Control");
			put(346,"R Alt");
			put(347,"R Super");
			put(348,"Menu");
		}
	};
	
	public static void loadKeyNames() {
		keyNames = Maps.newHashMap();
		JsonManager keyNamesJson = new JsonManager("main/", "keyNames.json");
		JsonElement element = keyNamesJson.getJsonObjectFromFile();
		if(!element.isJsonArray()) {
			return;
		}
		JsonArray array = element.getAsJsonArray();
		for(int i = 0; i < array.size(); i++) {
			JsonObject keyName = array.get(i).getAsJsonObject();
			keyNames.put(keyName.get("key").getAsInt(), keyName.get("name").getAsString());
		}
	}
	
	public static String getKeyName(int key, int scanCode) {
		String name = GLFW.glfwGetKeyName(key, scanCode) == null ? keyNames.get(key) : GLFW.glfwGetKeyName(key, scanCode);
		return name == null ? "None" : name;
	}

}
