package engine.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.Window;
import engine.maths.Matrix4f;
import engine.rendering.models.Entity;
import engine.rendering.models.RawModel;
import engine.rendering.models.TexturedModel;
import engine.shaders.BasicShader;

public class Renderer {
	
	private BasicShader shader;
	private Window window;
	
	public Renderer(Window window) {
		shader = new BasicShader();
		this.window = window;
	}
	
	public void init() {
		shader.create();
		shader.loadProjectionMatrix(new Matrix4f().projection(70.f, (float) window.getWidth() / window.getHeight(), 0.1f, 1000.0f));
	}
	
	public void renderModel(RawModel model) {
		GL30.glBindVertexArray(model.getVaoID());

		GL20.glEnableVertexAttribArray(0);
		
		shader.bind();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		
		GL20.glDisableVertexAttribArray(0);
		
		GL30.glBindVertexArray(0);
	}
	
	public void renderTexturedModel(TexturedModel model) {
		GL30.glBindVertexArray(model.getRawModel().getVaoID());

		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		shader.bind();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		
		GL20.glDisableVertexAttribArray(0);
	}
	
	public void renderEntity(Entity entity) {
		RawModel model = entity.getModel().getRawModel();
		
		GL30.glBindVertexArray(model.getVaoID());

		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		shader.bind();
		shader.useMatrices();
		shader.loadTransformationMatrix(entity.getTransformationMatrix());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getMaterial().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		GL30.glBindVertexArray(0);
	}
	
	public void clean() {
		shader.remove();
	}

	public void update() {
		shader.loadProjectionMatrix(new Matrix4f().projection(70.f, (float) window.getWidth() / window.getHeight(), 0.1f, 1000.0f));
	}
	
}
