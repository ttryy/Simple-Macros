package de.ttryy.simplemacros.gui;

import org.lwjgl.glfw.GLFW;

import de.ttryy.simplemacros.main.SimpleMacrosMod;
import de.ttryy.simplemacros.util.Macro;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.init.SoundEvents;

public class GuiMacroEdit extends GuiScreen {

	private MacroGui macroGui;
	private Macro macro;

	private GuiTextField macroTextField;

	private GuiButton keyButton;

	private Integer key = -1;
	private Integer scanCode = -1;
	private boolean mouseKey;

	private boolean clicked;

	public GuiMacroEdit(MacroGui macroGui, Macro macro) {
		this.macroGui = macroGui;

		if (macro != null) {
			this.macro = macro;
			this.key = macro.getKey();
			this.scanCode = macro.getScanCode();
			this.mouseKey = macro.isMouseKey();
		}
	}

	@Override
	protected void initGui() {
		macroTextField = new GuiTextField(2000, Minecraft.getInstance().fontRenderer, this.width / 2 - 100,
				this.height / 6 + 24, 200, 20);
		this.children.add(macroTextField);
		macroTextField.setMaxStringLength(255);
		this.setFocused(macroTextField);

		keyButton = new GuiButton(200, this.width / 2 - 50, this.height / 6 + 52, 100, 20, "NONE") {
			/**
			 * Called when the left mouse button is released over this button. This method
			 * is specific to GuiButton.
			 */
			@Override
			public void onRelease(double mouseX, double mouseY) {
				clicked = true;
				this.displayString = "Press Key";
				this.enabled = false;
			}
		};
		this.addButton(keyButton);

		if (macro != null) {
			macroTextField.setText(macro.getMacro());
			updateKeyButton();
		}

		this.addButton(new GuiButton(200, this.width / 2 - 75, this.height / 6 + 130, 200, 20, "Save") {
			/**
			 * Called when the left mouse button is pressed over this button. This method is
			 * specific to GuiButton.
			 */
			public void onClick(double mouseX, double mouseY) {
				if (key != -1 && macroTextField.getText().length() > 1) {
					if (macro != null) {
						SimpleMacrosMod.getInstance().getMacroManager().editMacro(macro, new Macro(macroTextField.getText(), key, scanCode, mouseKey));
					} else {
						SimpleMacrosMod.getInstance().getMacroManager()
								.addMakro(new Macro(macroTextField.getText(), key, scanCode, mouseKey));
					}
					Minecraft.getInstance().displayGuiScreen(macroGui);
					Minecraft.getInstance().getSoundHandler().play(SimpleSound.getMasterRecord(SoundEvents.ENTITY_VILLAGER_YES, 1.0F));
				} else {
					Minecraft.getInstance().getSoundHandler().play(SimpleSound.getMasterRecord(SoundEvents.ENTITY_VILLAGER_NO, 1.0F));
				}
			}
		});

		this.addButton(new GuiButton(200, this.width / 2 - 125, this.height / 6 + 130, 50, 20, "Cancel") {
			/**
			 * Called when the left mouse button is pressed over this button. This method is
			 * specific to GuiButton.
			 */
			public void onClick(double mouseX, double mouseY) {
				Minecraft.getInstance().displayGuiScreen(macroGui);
			}
		});

		super.initGui();
	}

	public void updateKeyButton() {
		this.clicked = false;
		this.keyButton.enabled = true;
		if (mouseKey) {
			this.keyButton.displayString = ("MOUSE " + (key + 1)).toUpperCase();
		} else if (key == -1) {
			this.keyButton.displayString = "NONE";
		} else {
			try {
				if(319 < key && key < 340) {
					this.keyButton.displayString = "NUM " + GLFW.glfwGetKeyName(key, scanCode).toUpperCase();
				} else {
					this.keyButton.displayString = GLFW.glfwGetKeyName(key, scanCode).toUpperCase();
				}
			} catch (Exception e) {
				this.key = -1;
				this.keyButton.displayString = "NONE";
			}
		}
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	@Override
	public boolean keyReleased(int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
		return super.keyReleased(p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
	}

	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		this.macroTextField.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		this.macroTextField.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Edit Macros", this.width / 2, 8, 16777215);
		this.drawCenteredString(Minecraft.getInstance().fontRenderer, "Macro: ", this.width / 2 - 150,
				this.height / 6 + 29, 16777215);
		this.drawCenteredString(Minecraft.getInstance().fontRenderer, "Key: ", this.width / 2 - 150,
				this.height / 6 + 57, 16777215);
		this.macroTextField.drawTextField(0, 0, 1);
		super.render(mouseX, mouseY, partialTicks);
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public void setMouseKey(boolean mouseKey) {
		this.mouseKey = mouseKey;
	}

	public void setScanCode(Integer scanCode) {
		this.scanCode = scanCode;
	}

}
