package engine.graphics.gui;

import engine.graphics.Material;
import engine.graphics.Shader;
import engine.graphics.Vertex2D;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.Mesh2D;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class GuiRenderer {

    private Window window;
    private Shader shader;

    public GuiRenderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    public void render(int x, int y, int width, int height, Vector3f color) {
        float xStart = -((window.getWidth() * 1.0f / 2) - x) / (window.getWidth() * 1.0f / 2), xEnd = -((window.getWidth() * 1.0f / 2) - (x + width)) / (window.getWidth() * 1.0f / 2);
        float yStart = ((window.getHeight() * 1.0f / 2) - y) / (window.getHeight() * 1.0f / 2), yEnd = ((window.getHeight() * 1.0f / 2) - (y + height)) / (window.getHeight() * 1.0f / 2);

        Mesh mesh = new Mesh2D(new Vertex2D[] {
                new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            }, new int[] {
                    0, 1, 2,
                    0, 3, 2
            },
            color);
        mesh.create();

        /**
         *
         */

        if(!mesh.isCreated()) return;//throw new Exception("Trying to render an object with a non created mesh!");
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());

        if(mesh.getMaterial() != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        }

        shader.bind();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        shader.setUniform("useColor", mesh.getColor() != null);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        shader.unbind();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

    }

    public void render(int x, int y, int width, int height, Material material) {
        float xStart = -((window.getWidth() * 1.0f / 2) - x) / (window.getWidth() * 1.0f / 2), xEnd = -((window.getWidth() * 1.0f / 2) - (x + width)) / (window.getWidth() * 1.0f / 2);
        float yStart = ((window.getHeight() * 1.0f / 2) - y) / (window.getHeight() * 1.0f / 2), yEnd = ((window.getHeight() * 1.0f / 2) - (y + height)) / (window.getHeight() * 1.0f / 2);

        Mesh mesh = new Mesh2D(new Vertex2D[] {
                new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            }, new int[] {
                    0, 1, 2,
                    0, 3, 2
            },
                material);
        material.create();
        mesh.create();

        /**
         *
         */

        if(!mesh.isCreated()) return;//throw new Exception("Trying to render an object with a non created mesh!");
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());

        if(mesh.getMaterial() != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        }

        shader.bind();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        shader.setUniform("useColor", mesh.getColor() != null);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        shader.unbind();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);

    }

}
