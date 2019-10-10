#version 460 core

in vec3 passColor;
in vec2 passTextureCoord;

out vec4 outColor;

uniform sampler2D tex;

uniform int useColor;

void main() {
    if(useColor == 1) {
        outColor = vec4(passColor, 1.0);
    } else {
        outColor = texture(tex, passTextureCoord);
    }
}