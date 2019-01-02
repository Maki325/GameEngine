package engine.rendering.fontRenderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.utils.Level;
import engine.utils.Loader;
import engine.utils.Logger;

public class Font {

	private int fontTextureID;
	
	private List<Char> chars;
	
	private Loader loader;
	
	public Font(String fontFile, Loader loader) {
		chars = new ArrayList<Char>();
		this.loader = loader;
		init(fontFile);
	}
	
	public void init(String file) {
		StringBuilder string = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
            	string.append(line).append("\n");
            	if(line.startsWith("char ")){
            		processChar(line);
            		continue;
            	}
            	if(line.startsWith("page")) {
            		Logger.log(Level.INFO, formPoint(line.replaceAll("\"", ""), "file="));
            		fontTextureID = loader.getTexture(formPoint(line.replaceAll("\"", ""), "file="));
            		continue;
            	}
            }
            reader.close();
        } catch (IOException e) {
        	Logger.log(Level.ERROR, "Couldn't find file: ", file);
            System.exit(-1);
        }
	}
	
	public int getFontID() {
		return fontTextureID;
	}
	
	private void processChar(String line) {
		int id = lineToInt(line, "id=");
		int x = lineToInt(line, "x="), y = lineToInt(line, "y=");
		int width = lineToInt(line, "width="), height = lineToInt(line, "height=");
		int xOffset = lineToInt(line, "xoffset="), yOffset = lineToInt(line, "yoffset=");
		int xAdvance = lineToInt(line, "xadvance=");
		chars.add(new Char(id, x, y, width, height, xOffset, yOffset, xAdvance));
		
	}
	
	private String formPoint(String line, String thing) {
		return line.substring(line.indexOf(thing)+thing.length());
	}
	
	private String linePart(String line, String thing, String end) {
		return line.substring(line.indexOf(thing) + thing.length(), line.indexOf(end, line.indexOf(thing) + thing.length()-1));
	}
	
	private String linePart(String line, String thing) {
		return linePart(line, thing, " ");
	}
	
	private int lineToInt(String line, String thing) {
		return Integer.parseInt(linePart(line, thing).trim());
	}
	
}
