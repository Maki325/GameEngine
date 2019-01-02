package engine.api;

import java.util.ArrayList;
import java.util.List;

import engine.gui.GuiScreen;

public class GuiRegistry {
	
	private static List<GuiScreen> guis = new ArrayList<GuiScreen>();
	
	public static void addGui(GuiScreen gui) {
		guis.add(gui);
	}
	
	public static List<GuiScreen> getGuis() {
		return guis;
	}
	
}
