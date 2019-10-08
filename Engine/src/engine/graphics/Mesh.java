package engine.graphics;

import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;
    private int vao, pbo, ibo, cbo, tbo;
    private Material material;
    private Vector3f color;
    private boolean isCreated = false;

    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public Mesh(Vertex[] vertices, int[] indices, Vector3f color) {
        this.vertices = vertices;
        this.indices = indices;
        this.color = color;
    }

    public void create() {
        if(material != null) material.create();

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for(int i = 0;i < vertices.length;i++) {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer, 0, 3);

        //
        if(color != null) {
            FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
            float[] colorData = new float[vertices.length * 3];
            for (int i = 0; i < vertices.length; i++) {
                colorData[i * 3] = color.getX();
                colorData[i * 3 + 1] = color.getY();
                colorData[i * 3 + 2] = color.getZ();
            }
            colorBuffer.put(colorData).flip();

            cbo = storeData(colorBuffer, 1, 3);
        }
        //

        //
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for(int i = 0;i < vertices.length;i++) {
            textureData[i * 2] = vertices[i].getTextureCoord().getX();
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, 2, 2);
        //

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        isCreated = true;
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);

        return bufferID;
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(cbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tbo);

        GL30.glDeleteVertexArrays(vao);

        material.destroy();
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return vao;
    }

    public int getPBO() {
        return pbo;
    }

    public int getCBO() {
        return cbo;
    }

    public int getIBO() {
        return ibo;
    }

    public int getTBO() {
        return tbo;
    }

    public Material getMaterial() {
        return material;
    }

    public Vector3f getColor() {
        return color;
    }

    public boolean isCreated() {
        return isCreated;
    }
}
