package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {

    String path;

    private Texture texture;
    private float width, height;
    private int textureID;

    public Material(String path) {
        this.path = path;
    }

    public void create() {
        System.out.println("Material created!");
        String[] dots = path.split("[.]");
        try {
            texture = TextureLoader.getTexture(dots[dots.length-1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);

            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        } catch (IOException e) {
            System.err.println("Can't find texture at: " + path);
            e.printStackTrace();
        }
    }

    public void destroy() {
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
