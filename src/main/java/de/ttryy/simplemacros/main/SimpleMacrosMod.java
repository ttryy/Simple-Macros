package de.ttryy.simplemacros.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ttryy.simplemacros.listener.InitGuiListener;
import de.ttryy.simplemacros.listener.MacroKeyListener;
import de.ttryy.simplemacros.listener.MacroListener;
import de.ttryy.simplemacros.manager.JsonManager;
import de.ttryy.simplemacros.manager.MacroManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("simplemacros")
public class SimpleMacrosMod {

	private static SimpleMacrosMod instance;
	private static final Logger LOGGER = LogManager.getLogger();
	
	private MacroManager macroManager;
	private JsonManager jsonManager;

	public SimpleMacrosMod() {
		instance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
	}

	private void setup(FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.info("[Loading] Simple Macros");
		MinecraftForge.EVENT_BUS.register(new InitGuiListener(this));
		MinecraftForge.EVENT_BUS.register(new MacroKeyListener(this));
		MinecraftForge.EVENT_BUS.register(new MacroListener(this));
	}

	private void doClientStuff(FMLClientSetupEvent event) {
		jsonManager = new JsonManager(Minecraft.getInstance().gameDir + "//config//simplemacros", "macros.json");
		macroManager = new MacroManager(this);
	}
	
	public MacroManager getMacroManager() {
		return macroManager;
	}
	
	public JsonManager getJsonManager() {
		return jsonManager;
	}

	public static SimpleMacrosMod getInstance() {
		return instance;
	}
}
