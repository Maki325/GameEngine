#version 150

in vec2 position;

out vec2 passTextCoords;

uniform mat4 transformationMatrix;

void main(void) {
	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
	
	passTextCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}