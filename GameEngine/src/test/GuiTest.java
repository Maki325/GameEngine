package test;

import engine.api.GuiRegistry;
import engine.gui.GuiScreen;
import engine.maths.Vector2f;
import engine.rendering.gui.GuiRenderer;
import engine.rendering.gui.GuiTexture;
import engine.utils.Loader;

public class GuiTest extends GuiScreen {

	GuiTexture texture;
	
	public GuiTest(Loader loader) {
		texture = new GuiTexture(loader.getTexture("image.png"), new Vector2f(0, 0), new Vector2f(0.5f, 0.5f));
		GuiRegistry.addGui(this);
	}
	
	@Override
	public void update(GuiRenderer renderer) {
		drawImage(texture, renderer);
	}
	
}
