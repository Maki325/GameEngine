package engine.objects;

import engine.io.Input;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private Vector3f position, rotation;
    private float moveSpeed = 0.05f, mouseSensitivity = 0.15f, speedUp = 1;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX = 0, newMouseY = 0, oldScroll = 0, newScroll = 0;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
        newScroll = Input.getScrollY();

        speedUp = (float) Math.max(1, Math.min(speedUp + (newScroll - oldScroll), 50));
        oldScroll = newScroll;

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed * speedUp;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed * speedUp;

        if(Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z,0, x));
        if(Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z,0, -x));
        if(Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x,0, -z));
        if(Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(x,0, z));
        if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0,moveSpeed * speedUp, 0));
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0,-moveSpeed * speedUp , 0));

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        if(!Input.getMouseLock()) return;

        rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));
        rotation.setX(Math.max(Math.min(90, rotation.getX()), -90));
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
