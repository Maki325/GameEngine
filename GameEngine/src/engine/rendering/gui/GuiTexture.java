package engine.rendering.gui;

import org.lwjgl.opengl.GL15;

import engine.maths.Vector2f;

public class GuiTexture {
	
	private int textureID;
	private Vector2f position;
	private Vector2f scale;
	
	public GuiTexture(int textureID, Vector2f position, Vector2f scale) {
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return textureID;
	}
	
	public void remove() {
		GL15.glDeleteTextures(textureID);
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	

}
