package engine.io;

import org.lwjgl.glfw.*;

public class Input {
	
	private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private static boolean[] buttnons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private static double mouseX, mouseY;
	private static double scrollX, scrollY;
	private static boolean mouseLock = false;
	
	private GLFWKeyCallback keyboard;
	private GLFWCursorPosCallback mouseMove;
	private GLFWMouseButtonCallback mouseButtons;
	private GLFWScrollCallback mouseScroll;

	public Input() {
		keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW.GLFW_RELEASE);
			}
		};
		mouseMove = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				mouseX = xpos;
				mouseY = ypos;
			}
		};
		mouseButtons = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				buttnons[button] = (action != GLFW.GLFW_RELEASE);	
			}
		};
		mouseScroll = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				scrollX += xoffset;
				scrollY += yoffset;
			}
		};
	}
	
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
	
	public static boolean isButtonDown(int button) {
		return buttnons[button];
	}

    public static void setMouseLocked(boolean lock) {
		mouseLock = lock;
    }

	public static boolean getMouseLock() {
		return mouseLock;
	}

	public void destroy() {
		keyboard.free();
		mouseMove.free();
		mouseButtons.free();
		mouseScroll.free();
	}

	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}

	public static double getScrollX() {
		return scrollX;
	}

	public static double getScrollY() {
		return scrollY;
	}

	public GLFWKeyCallback getKeyboardCallback() {
		return keyboard;
	}

	public GLFWCursorPosCallback getMouseMoveCallback() {
		return mouseMove;
	}

	public GLFWMouseButtonCallback getMouseButtonsCallback() {
		return mouseButtons;
	}

	public GLFWScrollCallback getMouseScrollCallback() {
		return mouseScroll;
	}
	
	

}
