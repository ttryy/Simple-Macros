package de.ttryy.simplemacros.util;

public class Macro {

	private String macro;
	private String macroDesc;
	private Integer key;
	private Integer scanCode;
	private boolean mouseKey;

	public Macro(String macro, Integer key, Integer scanCode, boolean mouseKey) {
		this.macro = macro;
		this.key = key;
		this.scanCode = scanCode;
		this.mouseKey = mouseKey;
		
		this.macroDesc = buildMacroDesc();
	}

	private String buildMacroDesc(){
		if(macro.length() > 30) {
			return macro.substring(0, 24) + "...";
		} else {
			return macro;
		}
	}
	
	public Integer getMacroKey() {
		return key;
	}

	public String getMacro() {
		return macro;
	}

	public void setMacro(String macro) {
		this.macro = macro;
		this.macroDesc = buildMacroDesc();
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getScanCode() {
		return scanCode;
	}

	public void setScanCode(Integer scanCode) {
		this.scanCode = scanCode;
	}

	public boolean isMouseKey() {
		return mouseKey;
	}

	public void setMouseKey(boolean isMouseKey) {
		this.mouseKey = isMouseKey;
	}

	public String getMacroDesc() {
		return macroDesc;
	}

}
