package de.ttryy.simplemacros.gui;

import org.lwjgl.glfw.GLFW;

import de.ttryy.simplemacros.main.SimpleMacrosMod;
import de.ttryy.simplemacros.util.KeyNames;
import de.ttryy.simplemacros.util.Macro;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiMacroList extends GuiListExtended<GuiMacroList.Entry> {
	private final MacroGui macroGui;
	private final Minecraft mc;
	private int maxListLabelWidth;

	public GuiMacroList(MacroGui controls, Minecraft mcIn) {
		super(mcIn, controls.width + 45, controls.height, 63, controls.height - 32, 20);
		this.macroGui = controls;
		this.mc = mcIn;

		loadMacros();

	}

	private void loadMacros() {
		this.clearEntries();

		for (Macro makro : SimpleMacrosMod.getInstance().getMacroManager().getMacros()) {
			int x = Minecraft.getInstance().fontRenderer.getStringWidth(makro.getMacroDesc());
			if (x > this.maxListLabelWidth) {
				this.maxListLabelWidth = x;
			}

			this.addEntry(new KeyEntry(makro));
		}
	}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 35;
	}

	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	@OnlyIn(Dist.CLIENT)
	public abstract static class Entry extends GuiListExtended.IGuiListEntry<GuiMacroList.Entry> {
	}

	@OnlyIn(Dist.CLIENT)
	public class KeyEntry extends GuiMacroList.Entry {
		/** The keybinding specified for this KeyEntry */
		@SuppressWarnings("unused")
		private final Macro macro;
		/** The localized key description for this KeyEntry */
		private final String makroDesc;
		private final GuiButton btnKey;
		private final GuiButton btnEditMakro;
		private final GuiButton btnDelete;

		private KeyEntry(final Macro macro) {
			this.macro = macro;
			this.makroDesc = macro.getMacroDesc();
			this.btnKey = new GuiButton(0, 0, 0, 60, 20, KeyNames.getKeyName(macro.getKey(), macro.getScanCode())) {
				/**
				 * Called when the left mouse button is pressed over this button. This method is
				 * specific to GuiButton.
				 */
				public void onClick(double mouseX, double mouseY) {
					Minecraft.getInstance().displayGuiScreen(new GuiMacroEdit(macroGui, macro));
				}
			};
			this.btnEditMakro = new GuiButton(0, 0, 0, 60, 20, "Edit") {
				/**
				 * Called when the left mouse button is pressed over this button. This method is
				 * specific to GuiButton.
				 */
				public void onClick(double mouseX, double mouseY) {
					Minecraft.getInstance().displayGuiScreen(new GuiMacroEdit(macroGui, macro));
				}
			};
			this.btnDelete = new GuiButton(0, 0, 0, 50, 20, "Delete") {
				/**
				 * Called when the left mouse button is pressed over this button. This method is
				 * specific to GuiButton.
				 */
				public void onClick(double mouseX, double mouseY) {
					SimpleMacrosMod.getInstance().getMacroManager().deleteMakro(macro);
					loadMacros();
				}
			};
		}

		public void drawEntry(int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_,
				float partialTicks) {
			int i = this.getY();
			int j = this.getX() + GuiMacroList.this.maxListLabelWidth/7;
			GuiMacroList.this.mc.fontRenderer.drawString(this.makroDesc,
					(float) (j - GuiMacroList.this.maxListLabelWidth + 50),
					(float) (i + entryHeight / 2 - GuiMacroList.this.mc.fontRenderer.FONT_HEIGHT / 2), 16777215);
			this.btnDelete.x = j + 210;
			this.btnDelete.y = i;
			this.btnDelete.render(mouseX, mouseY, partialTicks);
			this.btnEditMakro.x = j + 150;
			this.btnEditMakro.y = i;
			this.btnEditMakro.displayString = "Edit";
			this.btnEditMakro.render(mouseX, mouseY, partialTicks);
			this.btnKey.x = j + 90;
			this.btnKey.y = i;
			this.btnKey.render(mouseX, mouseY, partialTicks);
			this.btnKey.enabled = false;
		}

		public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
			if (this.btnEditMakro.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
				return true;
			} else {
				return this.btnDelete.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
			}
		}

		public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
			return this.btnEditMakro.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)
					|| this.btnDelete.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		}
	}
}