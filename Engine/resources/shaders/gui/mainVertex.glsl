#version 460 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 textureCoord;

out vec4 passColor;
out vec2 passTextureCoord;

void main() {

    gl_Position = vec4(position, 0.0, 1.0);
    passColor = color;
    passTextureCoord = textureCoord;

}