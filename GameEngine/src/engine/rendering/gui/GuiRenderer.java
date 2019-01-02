package engine.rendering.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.maths.Maths;
import engine.maths.Matrix4f;
import engine.rendering.gui.shader.GuiShader;
import engine.rendering.models.RawModel;
import engine.utils.Loader;

public class GuiRenderer {

	private RawModel quad;
	private GuiShader shader;
	private Loader loader;
	
	public GuiRenderer(Loader loader){
		this.loader = loader;
	}
	
	public void init() {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions, 2);
		shader = new GuiShader();
		shader.create();
	}

	public void drawTexturedQuad(GuiTexture texture) {
		shader.bind();
		
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
		
		Matrix4f matrix = Maths.createTransformationMatrix(texture.getPosition(), texture.getScale());
		shader.loadTransformation(matrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.unbind();
	}
	
}
