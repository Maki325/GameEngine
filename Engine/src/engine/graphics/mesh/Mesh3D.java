package engine.graphics.mesh;

import engine.graphics.Material;
import engine.graphics.Vertex;
import engine.maths.Vector3f;
import engine.maths.Vector4f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh3D extends Mesh<Vertex> {

    public Mesh3D(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public Mesh3D(Vertex[] vertices, int[] indices, Vector4f color) {
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
            FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
            float[] colorData = new float[vertices.length * 4];
            for (int i = 0; i < vertices.length; i++) {
                colorData[i * 4] = color.getX();
                colorData[i * 4 + 1] = color.getY();
                colorData[i * 4 + 2] = color.getZ();
                colorData[i * 4 + 3] = color.getW();
            }
            colorBuffer.put(colorData).flip();

            cbo = storeData(colorBuffer, 1, 4);
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
        ibo = storeIndices(indicesBuffer, 0);

        isCreated = true;
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(cbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tbo);

        GL30.glDeleteVertexArrays(vao);

        material.destroy();
    }

}
