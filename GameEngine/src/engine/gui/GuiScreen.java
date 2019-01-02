package engine.gui;

import engine.Window;
import engine.rendering.gui.GuiRenderer;
import engine.rendering.gui.GuiTexture;

public class GuiScreen {

	private boolean isActive;
	
	public void onKeyPress(Window window) {}
	
	public void onMouseClick(Window window) {}
	
	public void update(GuiRenderer renderer) {}
	
	public void drawImage(GuiTexture texture, GuiRenderer renderer) {
		renderer.drawTexturedQuad(texture);
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
}
