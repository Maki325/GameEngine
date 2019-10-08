package engine.io;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.utils.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static Mesh load(String path, String texturePath) {
        List<Vector3f> positions = new ArrayList<>();
        List<Vector2f> texture = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        List<Vertex> vertices = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if(line.startsWith("v ")) {
                    positions.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
                } else if(line.startsWith("vt ")) {
                    texture.add(new Vector2f(Float.parseFloat(parts[1]), 1 - Float.parseFloat(parts[2])));
                } else if(line.startsWith("vn ")) {

                } else if(line.startsWith("f ")) {
                    if(vertices == null) {
                        vertices = new ArrayList<>(positions.size());
                        for(int i = 0;i < positions.size();i++) vertices.add(null);
                    }
                    for(int i = 1;i < parts.length;i++) {
                        String[] vertex = parts[i].split("/");
                        vertices.set(Integer.parseInt(vertex[0]) - 1, new Vertex( positions.get(Integer.parseInt(vertex[0]) - 1),  texture.get(Integer.parseInt(vertex[1]) - 1 ) ));
                        indices.add(Integer.parseInt(vertex[0]) - 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Counld't find the file at " + path);
            return null;
        }

        Vertex[] verticesArray = new Vertex[vertices.size()];
        int[] indicesArray = new int[indices.size()];
        for(int i = 0;i < vertices.size();i++) {
            System.out.println("Verticy: " + vertices.get(i).toString());
            verticesArray[i] = vertices.get(i);
        }
        for(int i = 0;i < indices.size();i++) {
            System.out.println("Indecy: " + indices.get(i));
            indicesArray[i] = indices.get(i);
        }

        return new Mesh(verticesArray, indicesArray, new Material(texturePath));
    }

}
