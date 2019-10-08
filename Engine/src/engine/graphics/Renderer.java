package engine.graphics;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.objects.Camera;
import engine.objects.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    private Shader shader;
    private Window window;

    public Renderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    public void renderMesh(GameObject object, Camera camera) {
        if(!object.getMesh().isCreated()) return;//throw new Exception("Trying to render an object with a non created mesh!");
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        if(object.getMesh().getMaterial() != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        }

        shader.bind();

        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("useColor", object.getMesh().getColor() != null);

        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        shader.unbind();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
