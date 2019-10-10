package engine.objects;

import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.Mesh3D;
import engine.maths.Vector3f;

public class GameObject {

    private Vector3f position, rotation, scale;
    private Mesh3D mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh3D mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void update() {
        position.setZ(position.getZ() - 0.1f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh3D getMesh() {
        return mesh;
    }
}
