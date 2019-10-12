package engine.graphics.gui;

import engine.graphics.Material;
import engine.graphics.Shader;
import engine.graphics.Vertex2D;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.Mesh2D;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.maths.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class GuiRenderer {

    private Window window;
    private Shader shader;
    private Mesh mesh;

    public GuiRenderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    public void render(int x, int y, int width, int height, Vector3f color) {
        render(x, y, width, height, new Vector4f(color.getX(), color.getY(), color.getZ(), 1.0f));
    }

    public void render(int x, int y, int width, int height, Vector4f color) {
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

        if(mesh == null) {
            mesh = new Mesh2D(new Vertex2D[] {
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
        } else if(!mesh.isCreated()) {
            mesh.setVertices(new Vertex2D[] {
                    new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                    new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            });
            mesh.setColor(color);
            mesh.setMaterial(null);
            mesh.create();
        } else {
            mesh.setVertices(new Vertex2D[] {
                    new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                    new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            });
            mesh.setColor(color);
            mesh.setMaterial(null);
        }

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

        if(mesh == null) {
            mesh = new Mesh2D(new Vertex2D[] {
                    new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                    new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            }, new int[] {
                    0, 1, 2,
                    0, 3, 2
            },
                    material);
            mesh.create();
        } else if(!mesh.isCreated()) {
            mesh.create();
        } else {
            mesh.setVertices(new Vertex2D[] {
                    new Vertex2D(new Vector2f(xStart, yStart), new Vector2f(0.0f, 0.0f)),
                    new Vertex2D(new Vector2f(xStart, yEnd), new Vector2f(0.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd, yEnd), new Vector2f(1.0f, 1.0f)),
                    new Vertex2D(new Vector2f(xEnd,  yStart), new Vector2f(1.0f, 0.0f))
            });
            mesh.setColor(null);
            mesh.setMaterial(material);
        }

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

    public void render(Gui gui) {
        if(gui.color != null) {
            render(gui.x, gui.y, gui.width, gui.height, gui.color);
        } else if(gui.material != null) {
            render(gui.x, gui.y, gui.width, gui.height, gui.material);
        }
    }

    public void render(List<Gui> guis) {
        LinkedHashMap<Material, LinkedList<Gui>> materialGui = new LinkedHashMap<>();
        LinkedHashMap<Vector4f, LinkedList<Gui>> colorGui = new LinkedHashMap<>();
        for(Gui gui : guis) {
            if(gui.color != null) {
                if(!colorGui.containsKey(gui.color)) {
                    colorGui.put(gui.color, new LinkedList<>());
                }
                colorGui.get(gui.color).add(gui);
            } else if(gui.material != null) {
                if(!materialGui.containsKey(gui.material)) {
                    materialGui.put(gui.material, new LinkedList<>());
                }
                materialGui.get(gui.material).add(gui);
            }
        }

        for(Material material : materialGui.keySet()) {

        }

    }

}
