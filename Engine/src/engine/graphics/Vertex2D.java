package engine.graphics;

import engine.maths.Vector2f;

public class Vertex2D {

    private Vector2f position;
    private Vector2f textureCoord;

    public Vertex2D(Vector2f position, Vector2f textureCoord) {
        this.position = position;
        this.textureCoord = textureCoord;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getTextureCoord() {
        return textureCoord;
    }

    @Override
    public String toString() {
        return "Position: " + position.toString() + ", Texture: " + textureCoord.toString();
    }

}
