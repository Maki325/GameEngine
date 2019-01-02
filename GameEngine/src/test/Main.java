package test;

import org.lwjgl.glfw.GLFW;

import engine.Window;
import engine.api.GuiRegistry;
import engine.gui.GuiScreen;
import engine.io.OBJLoader;
import engine.maths.Vector3f;
import engine.rendering.Renderer;
import engine.rendering.fontRenderer.Font;
import engine.rendering.gui.GuiRenderer;
import engine.rendering.models.Entity;
import engine.rendering.models.TexturedModel;
import engine.utils.Loader;

public class Main {

	public static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	public static final boolean FULLSCREEN = false;
	public static Window window = new Window(WIDTH, HEIGHT, FPS, "Game Engine", "image.png", FULLSCREEN);
	public static Renderer renderer = new Renderer(window);
	public static Loader loader = new Loader();
	public static GuiRenderer guiRenderer = new GuiRenderer(loader);
	public static GuiScreen gui;
	
	public static void main(String[] args) {
		window.create();
		window.setBackgroundColor(0.0f, 0.0f, 0.0f);
    	renderer.init();
    	
    	guiRenderer.init();
    	
		Entity stall = new Entity(new TexturedModel(OBJLoader.loadObjModel("stall", loader), loader.genMaterial("stallTexture.png")), new Vector3f(0, -1, 15), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		
		gui = new GuiTest(loader);
		
		Font font = new Font("res/fonts/verdana.fnt", loader);
		
		while ( !window.shouldClose() ) {
			if(window.isUpdating()) {
				window.update();
				renderer.update();
				
				//entity.addRotation(1f, 1f, 1f);
				//entity.addPosition(0, 0, 0.05f);
				renderer.renderEntity(stall);
				
				GuiRegistry.getGuis().forEach(guiR -> {
					if(!guiR.isActive())
						return;
					
					guiR.update(guiRenderer);
					
					guiR.onKeyPress(window);
					guiR.onMouseClick(window);
				});
				
				stall.addRotation(0f, 1f, 0f);
			
				if(window.isKeyPressed(GLFW.GLFW_KEY_A)) System.out.println("Hey");
				onKeyPress();
				
				window.swapBuffers();
			}
		}
		
		renderer.clean();
		window.stop();
		loader.cleanUp();
	}
	
	private static void onKeyPress() {
		if(window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) window.close();
		if(window.isKeyPressed(GLFW.GLFW_KEY_E)) gui.setActive(!gui.isActive());
	}

}
