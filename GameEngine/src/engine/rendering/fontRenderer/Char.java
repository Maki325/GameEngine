package engine.rendering.fontRenderer;

import engine.maths.Vector2i;

public class Char {

	private int id;
	private Vector2i position;
	private Vector2i size;
	private int xOffset, yOffset;
	private int xAdvance;//?
	
	public Char(int id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance) {
		this.id = id;
		position = new Vector2i(x, y);
		size = new Vector2i(width, height);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
	}
	
	public int getId() {
		return id;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public Vector2i getSize() {
		return size;
	}
	
	public int getxOffset() {
		return xOffset;
	}
	
	public int getyOffset() {
		return yOffset;
	}
	
	public int getxAdvance() {
		return xAdvance;
	}
	
}
