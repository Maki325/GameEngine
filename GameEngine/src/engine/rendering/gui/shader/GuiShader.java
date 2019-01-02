package engine.rendering.gui.shader;

import engine.maths.Matrix4f;
import engine.shaders.Shader;

public class GuiShader extends Shader {
	
	private static final String VERTEX_FILE = "src/engine/rendering/gui/shader/basicVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/engine/rendering/gui/shader/basicFragmentShader.glsl";
    
    private int location_transformationMatrix;
    
	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
        super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniforms() {
		location_transformationMatrix = super.getUniform("transformationMatrix");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

}
