package engine.rendering.fontRenderer.shader;

import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.shaders.Shader;

public class FontShader extends Shader {
     
    private static final String VERTEX_FILE = "src/engine/rendering/fontRenderer/shader/basicVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/engine/rendering/fontRenderer/shader/basicFragmentShader.glsl";
    
    private int location_translation;
    private int location_color;
    
    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

	@Override
	protected void getAllUniforms() {
		location_translation = super.getUniform("tvpMatrix");
		location_color = super.getUniform("tvpMatrix");
	}
	
	public void loadColor(Vector3f color) {
		super.loadVector3f(location_color, color);
	}
	
	public void loadTranslation(Vector2f translation) {
		super.loadVector2f(location_translation, translation);
	}
	
}