#version 460 core

in vec4 passColor;
in vec2 passTextureCoord;

out vec4 outColor;

uniform sampler2D tex;

uniform int useColor;

void main() {
    if(useColor == 1) {
        outColor = passColor;
    } else {
        outColor = texture(tex, passTextureCoord);
    }
}