package engine.graphics.mesh;

import engine.graphics.Material;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Mesh {

    protected int[] indices;
    protected int vao, pbo, ibo, cbo, tbo;
    protected Material material;
    protected Vector3f color;
    protected boolean isCreated = false;

    protected int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);

        return bufferID;
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
    public Vector3f getColor() { return color; }


}
