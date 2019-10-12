package engine.graphics.mesh;

import engine.graphics.Material;
import engine.maths.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Mesh<T> {

    protected int[] indices;
    protected T[] vertices;
    protected int vao, pbo, ibo, cbo, tbo;
    protected Material material;
    protected Vector4f color;
    protected boolean isCreated = false;

    protected int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();

        storeData(buffer, index, size, bufferID);

        return bufferID;
    }

    protected void storeData(FloatBuffer buffer, int index, int size, int bufferID) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    protected int storeIndices(IntBuffer indicesBuffer, int buffer) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer);

        return bufferID;
    }

    public abstract void create();

    public abstract void destroy();

    public int[] getIndices() { return indices; }

    public boolean isCreated() { return isCreated; }

    public int getVAO() { return vao; }
    public int getIBO() { return ibo; }
    public int getPBO() { return pbo; }

    public int getCBO() { return cbo; }
    public int getTBO() { return tbo; }

    public Material getMaterial() { return material; }
    public Vector4f getColor() { return color; }

    public void setMaterial(Material material) { this.material = material; }
    public void setColor(Vector4f color) { this.color = color; }

    public void setIndices(int[] indices) { this.indices = indices; }
    public void setVertices(T[] vertices) { this.vertices = vertices; }

}
