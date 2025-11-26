in vec3 Position;
in vec2 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    // offset UVs inward so that the center texture stays fixed
    texCoord = UV0;
    //texCoord = mix(5, 1.0 - 5, UV0);
    vertexColor = Color;
}
