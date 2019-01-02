package engine.rendering;

import engine.utils.Loader;

public class Material {
	
	private int textureID;
	
	public Material(String file, Loader loader) {
		textureID = loader.getTexture(file);
	}
	
	public Material(int textureID) {
		this.textureID = textureID;
	}

	public int getTextureID() {
		return textureID;
	}
	
}
