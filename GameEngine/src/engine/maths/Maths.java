package engine.maths;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f().identity().translate(new Vector3f(translation.x, translation.y, 1.0f));
		Matrix4f matrix1 = new Matrix4f().identity().scale(new Vector3f(scale.x, scale.y, 1.0f));
		
		//matrix.translate(new Vector3f(translation.x, translation.x, 1.0f));
		//matrix.scale(new Vector3f(scale.x, scale.x, 1.0f));
		
		return matrix.mul(matrix1);
	}

}
