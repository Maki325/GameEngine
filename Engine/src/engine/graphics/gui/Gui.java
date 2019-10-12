package engine.graphics.gui;

import engine.graphics.Material;
import engine.maths.Vector4f;

public class Gui {

    public int x, y;
    public int width, height;
    public Vector4f color;
    public Material material;

    public Gui(int x, int y, int width, int height, Vector4f color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Gui(int x, int y, int width, int height, Material material) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.material = material;
    }

}
