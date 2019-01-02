package engine.shaders;

import engine.maths.Matrix4f;

public class BasicShader extends Shader {
     
    private static final String VERTEX_FILE = "src/engine/shaders/basicVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/engine/shaders/basicFragmentShader.glsl";
    
    private int tvpMatrixLocation;
    
    private Matrix4f transformationMatrix = new Matrix4f().identity(), projectionMatrix = new Matrix4f().identity();
 
    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

	@Override
	protected void getAllUniforms() {
		tvpMatrixLocation = super.getUniform("tvpMatrix");
	}
	
	public void useMatrices() {
		super.loadMatrix(tvpMatrixLocation, projectionMatrix.mul(transformationMatrix));
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		transformationMatrix = matrix;
	}
}