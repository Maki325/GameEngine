package engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

import engine.utils.Level;
import engine.utils.Logger;

public abstract class Shader {

	private int vertexShaderID, fragmentShaderID, programID;
	
	private String fragmnetFile, vertexFile;
	
	public Shader(String vertexFile, String fragmnetFile) {
		this.vertexFile = vertexFile;
		this.fragmnetFile = fragmnetFile;
	}
	
	public void create() {
		programID = GL20.glCreateProgram();
		vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShaderID, readFile(vertexFile));
		GL20.glCompileShader(vertexShaderID);
		
		if(GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == 0)
			Logger.log(Level.ERROR, "Vertex Shader - ", GL20.glGetShaderInfoLog(vertexShaderID));
		
		fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShaderID, readFile(fragmnetFile));
		GL20.glCompileShader(fragmentShaderID);
		
		if(GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) == 0)
			Logger.log(Level.ERROR, "Fragment Shader - ", GL20.glGetShaderInfoLog(fragmentShaderID));
		
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		
		GL20.glLinkProgram(programID);
		if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0)
			Logger.log(Level.ERROR, "Program Linking - ", GL20.glGetShaderInfoLog(programID));
		
		GL20.glValidateProgram(programID);
		if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0)
			Logger.log(Level.ERROR, "Program Validating - ", GL20.glGetShaderInfoLog(programID));
	}
	
	public abstract void bindAllAttributes();
	
	public void bindAttribute(int index, String location) {
		GL20.glBindAttribLocation(programID, index, location);
	}
	
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	public void remove() {
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	private String readFile(String file) {
		BufferedReader reader = null;
		StringBuilder string = new StringBuilder();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			while((line = reader.readLine()) != null) {
				string.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return string.toString();
	}
	
}
