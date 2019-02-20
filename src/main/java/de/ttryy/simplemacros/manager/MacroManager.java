package de.ttryy.simplemacros.manager;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.ttryy.simplemacros.main.SimpleMacrosMod;
import de.ttryy.simplemacros.util.Macro;

public class MacroManager {

	private final SimpleMacrosMod mod;
	
	private ArrayList<Macro> macros;
	
	public MacroManager(SimpleMacrosMod mod) {
		this.mod = mod;
		this.macros = loadMacros();
	}
	
	public ArrayList<Macro> loadMacros(){
		ArrayList<Macro> loadedMacros = Lists.newArrayList();
		
		JsonElement element = mod.getJsonManager().getJsonObjectFromFile();
		if(!element.isJsonArray()) {
			return loadedMacros;
		}
		
		JsonArray array = element.getAsJsonArray();
		
		for(int i = 0; i < array.size(); i++) {
			JsonObject singleMacro = array.get(i).getAsJsonObject();
			String macro = singleMacro.get("macro").getAsString();
			Integer key = singleMacro.get("key").getAsInt();
			Integer scanCode = singleMacro.get("scanCode").getAsInt();
			Boolean mouseKey = singleMacro.get("mouseKey").getAsBoolean();
			
			loadedMacros.add(new Macro(macro, key, scanCode, mouseKey));
		}
		
		return loadedMacros;
	}
	
	public ArrayList<Macro> getMacros(){
		return macros;
	}
	
	public void editMacro(Macro original, Macro edited) {
		JsonElement element = mod.getJsonManager().getJsonObjectFromFile();
		if(!element.isJsonArray()) {
			return;
		}
		
		JsonArray array = element.getAsJsonArray();
		JsonObject object = new JsonObject();
		object.addProperty("macro", edited.getMacro());
		object.addProperty("key", edited.getKey());
		object.addProperty("scanCode", edited.getScanCode());
		object.addProperty("mouseKey", edited.isMouseKey());
		
		array.set(macros.indexOf(original), object);
		
		mod.getJsonManager().writeJsonElementToFile(array);
		
		original.setKey(edited.getKey());
		original.setScanCode(edited.getScanCode());
		original.setMacro(edited.getMacro());
		original.setMouseKey(edited.isMouseKey());
		
	}
	
	public void addMakro(Macro makro) {
		JsonElement element = mod.getJsonManager().getJsonObjectFromFile();
		if(!element.isJsonArray()) {
			return;
		}
		
		JsonArray array = element.getAsJsonArray();
		JsonObject object = new JsonObject();
		object.addProperty("macro", makro.getMacro());
		object.addProperty("key", makro.getKey());
		object.addProperty("scanCode", makro.getScanCode());
		object.addProperty("mouseKey", makro.isMouseKey());
		array.add(object);
		
		mod.getJsonManager().writeJsonElementToFile(array);
		
		this.macros.add(makro);
	}
	
	public void deleteMakro(Macro makro) {
		if(macros.contains(makro)) {
			JsonElement element = mod.getJsonManager().getJsonObjectFromFile();
			if(!element.isJsonArray()) {
				return;
			}
			
			JsonArray array = element.getAsJsonArray();
			array.remove(macros.indexOf(makro));
			
			mod.getJsonManager().writeJsonElementToFile(array);
			
			this.macros.remove(makro);
		}
	}
	
}
