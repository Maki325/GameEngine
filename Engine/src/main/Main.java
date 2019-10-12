package main;

import engine.graphics.Material;
import engine.graphics.Vertex2D;
import engine.graphics.gui.GuiRenderer;
import engine.graphics.mesh.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.mesh.Mesh2D;
import engine.graphics.mesh.Mesh3D;
import engine.io.Input;
import engine.io.OBJLoader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.maths.Vector4f;
import engine.objects.Camera;
import engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public Renderer renderer;
	public GuiRenderer guiRenderer;
	public Shader shader, guiShader;
	public final int WIDTH = 1280, HEIGHT = 720;

	public Mesh3D mesh = OBJLoader.load("/models/stall.obj", "/textures/stallTexture.png");
	public Mesh3D meshColor = OBJLoader.load("/models/stall.obj", new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));/*new Mesh(new Vertex[] {
			//Back face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

			//Front face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Right face
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Left face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Top face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Bottom face
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
	}, new int[] {
			//Back face
			0, 1, 3,
			3, 1, 2,

			//Front face
			4, 5, 7,
			7, 5, 6,

			//Right face
			8, 9, 11,
			11, 9, 10,

			//Left face
			12, 13, 15,
			15, 13, 14,

			//Top face
			16, 17, 19,
			19, 17, 18,

			//Bottom face
			20, 21, 23,
			23, 21, 22
	}, new Material("/textures/desert.png"));*/

	public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 180, 0), new Vector3f(1.0f, 1.0f, 1.0f), mesh);
	public GameObject objectColor = new GameObject(new Vector3f(0, 10, 0), new Vector3f(0, 180, 0), new Vector3f(1.0f, 1.0f, 1.0f), meshColor);

	public Mesh mesh2d = new Mesh2D(new Vertex2D[] {
			new Vertex2D(new Vector2f(-0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex2D(new Vector2f(-0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex2D(new Vector2f( 0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex2D(new Vector2f( 0.5f,  0.5f), new Vector2f(1.0f, 0.0f))
		}, new int[] {
			0, 1, 2,
			0, 3, 2
		},
		new Material("/textures/desert.png"));

	public Camera camera = new Camera(new Vector3f(0, 0, 20), new Vector3f(0, 0, 0));

	Material guiMaterial = new Material("/textures/desert.png");

	public void start() {
		game = new Thread(this, "game");

		game.start();
	}
	
	public void init() {
		window = new Window(WIDTH, HEIGHT, "Game");
		shader = new Shader("/shaders/main/mainVertex.glsl", "/shaders/main/mainFragment.glsl");
		guiShader = new Shader("/shaders/gui/mainVertex.glsl", "/shaders/gui/mainFragment.glsl");
		renderer = new Renderer(window, shader);
		guiRenderer = new GuiRenderer(window, guiShader);
		window.setBackgroundColor(1.0f, 0.0f, 0.0f);
		window.create();

		mesh.create();
		meshColor.create();
		mesh2d.create();

		guiMaterial.create();

		shader.create();
		guiShader.create();
	}
	
	public void run() {
		init();
		while(!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) window.mouseState(false);
		}
		close();
	}
	
	private void update() {
		window.update();
		camera.update();
	}

	private void render() {
		renderer.renderMesh(object, camera);
		//guiRenderer.render(1170, 610, 100, 100, new Vector3f(0, 0, 1));
		guiRenderer.render(window.getWidth() - 110,  window.getHeight() - 110, 100, 100, guiMaterial);
		guiRenderer.render(10,  10, 100, 100, guiMaterial);
		guiRenderer.render(10,  window.getHeight() - 110, 100, 100, new Vector4f(0, 0, 1, 0.25f));
		guiRenderer.render(window.getWidth() - 110,  10, 100, 100, new Vector3f(0, 0, 1));

		renderer.renderMesh(objectColor, camera);
		window.swapBuffers();
	}

	private void close() {
		window.destroy();
		mesh.destroy();
		shader.destroy();
	}

	public static void main(String[] args) {
		new Main().start();
	}
	
}
