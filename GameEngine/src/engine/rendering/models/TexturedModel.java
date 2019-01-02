package engine.rendering.models;

import engine.rendering.Material;

public class TexturedModel {
	
	private RawModel model;
	private Material material;
    
    public TexturedModel(RawModel model, Material material) {
        this.model = model;
        this.material = material;
    }
 
    public Material getMaterial() {
		return material;
	}
    
    public RawModel getRawModel() {
		return model;
	}
    
}
