package test;

import org.lwjgl.glfw.GLFW;

import engine.Window;
import engine.maths.Vector3f;
import engine.rendering.Renderer;
import engine.rendering.models.Entity;
import engine.rendering.models.TexturedModel;

public class Main {

	public static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	public static final boolean FULLSCREEN = false;
	public static Window window = new Window(WIDTH, HEIGHT, FPS, "Game Engine", FULLSCREEN);
	public static Renderer renderer;
	
	public static void main(String[] args) {
		window.create();
		window.setBackgroundColor(0.0f, 0.0f, 0.0f);
    	
    	renderer = new Renderer();
		
		TexturedModel model = new TexturedModel(new float[] { 
			-0.5f,  0.5f, 0.0f, //top left
			 0.5f,  0.5f, 0.0f, //top right
			 0.5f, -0.5f, 0.0f, //bottom right
			-0.5f, -0.5f, 0.0f, //bottom left
		}, new float[] { 
				0, 0,//TOP LEFT
				1, 0,//TOP RIGHT
				1, 1,//BOTOOM RIGHT1
				0, 1//BOTTOM LEFT
		}, new int[] { 
				0, 1, 2,
				2, 3, 0
		},
		"image.png");
		
		Entity entity = new Entity(model, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		
		while ( !window.shouldClose() ) {
			if(window.isUpdating()) {
				window.update();
				
				entity.addRotation(1f, 1f, 1f);
				renderer.renderEntity(entity);
			
				if(window.isKeyPressed(GLFW.GLFW_KEY_A)) System.out.println("Hey");
				onKeyPress();
				window.swapBuffers();
			}
		}
		
		renderer.clean();
		window.stop();
		model.remove();
	}
	
	private static void onKeyPress() {
		if(window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) window.close();
	}

}
