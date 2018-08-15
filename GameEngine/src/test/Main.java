package test;

import org.lwjgl.glfw.GLFW;

import engine.Window;
import engine.render.Model;
import engine.render.Renderer;
import engine.shaders.BasicShader;

public class Main {

	public static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	public static Window window = new Window(WIDTH, HEIGHT, FPS, "Game Engine");
	public static Renderer render = new Renderer();
	public static BasicShader shader = new BasicShader();
	
	public static void main(String[] args) {
		window.create();
		window.setBackgroundColor(0.0f, 0.0f, 0.0f);
		
		shader.create();
		
		Model model = new Model(new float[] { 
			-0.5f, 0.5f, 0.0f, //top left
			0.5f, 0.5f, 0.0f, //top right
			-0.5f, -0.5f, 0.0f, //bottom left
			0.5f, -0.5f, 0.0f, //bottom right
		}, new int[] { 
				0, 1, 2,
				2, 3, 1
		});
		model.create();
		
		while ( !window.shouldClose() ) {
			if(window.isUpdating()) {
				window.update();
				shader.bind();
				render.renderModel(model);
				if(window.isKeyPressed(GLFW.GLFW_KEY_A)) System.out.println("Hey");
				window.swapBuffers();
			}
		}
		
		shader.remove();
		window.stop();
		model.remove();
	}

}
