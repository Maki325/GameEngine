package engine;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.maths.Vector3f;

public class Window {

	private int width;
	private int height;
	private String title;
	
	private double fps_cap;
	
	private double time, processedTime = 0;
	
	private long window;
	
	private Vector3f backgroundColor;
	
	private boolean isFullscreen;
	
	private GLFWVidMode vidmode;
	
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public Window(int width, int height, int fps, String title) {
		this(width, height, fps, title, false);
	}
	
	public Window(int width, int height, int fps, String title, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fps_cap = fps;
		this.isFullscreen = fullscreen;
		backgroundColor = new Vector3f(0.0f, 0.0f, 0.0f);
	}
	
	public void create() {
		if ( !GLFW.glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		if(isFullscreen)
			window = GLFW.glfwCreateWindow(vidmode.width(), vidmode.height(), title, GLFW.glfwGetPrimaryMonitor(), window);
		else
			window = GLFW.glfwCreateWindow(width, height, title, 0, window);
		
		if ( window == 0 )
			throw new RuntimeException("Failed to create the GLFW window");
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		//GL11.glEnable(GL11.GL_DEPTH_TEST);

		GLFW.glfwSetWindowPos(
			window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);
		
		GLFW.glfwShowWindow(window);
		
		time = getTime();
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public void close() {
		GLFW.glfwSetWindowShouldClose(window, true);
	}
	
	public void update() {
		for(int i = 0;i < GLFW.GLFW_KEY_LAST;i++) keys[i] = isKeyDown(i);
		for(int i = 0;i < GLFW.GLFW_MOUSE_BUTTON_LAST;i++) mouseButtons[i] = isMouseDown(i);
		
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, widthBuffer, heightBuffer);
		width = widthBuffer.get(0);
		height = heightBuffer.get(0);
		GL11.glViewport(0, 0, width, height);
		
		GL11.glClearColor(backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GLFW.glfwPollEvents();
	}
	
	public void stop() {
		GLFW.glfwTerminate();
		GLFW.glfwDestroyWindow(window);
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}
	
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000;
	}
	
	public boolean isKeyDown(int keyCode) {
		return GLFW.glfwGetKey(window, keyCode) == 1;
	}
	
	public boolean isMouseDown(int mouseButton) {
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	public boolean isMousePressed(int mouseButton) {
		return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
	}
	
	public boolean isMouseReleased(int mouseButton) {
		return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
	}
	
	public double getMouseX() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		
		return buffer.get(0);
	}
	
	public double getMouseY() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		
		return buffer.get(0);
	}
	
	public boolean isUpdating() {
		double nextTime = getTime();
		double passedTime = nextTime - time;
		processedTime += passedTime;
		time = nextTime;
		
		while(processedTime > 1.0 /  fps_cap) {
			processedTime -= 1.0 / fps_cap;
			return true;
		}
		
		return false;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public double getFPS() {
		return fps_cap;
	}

	public long getWindow() {
		return window;
	}
	
	public void setBackgroundColor(float r, float g, float b) {
		backgroundColor = new Vector3f(r, g, b);
	}
	
	public boolean isFullscreen() {
		return isFullscreen;
	}
	
}
