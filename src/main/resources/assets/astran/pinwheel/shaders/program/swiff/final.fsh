uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;
in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec4 scene = texture(EffectSampler0,texCoord);
    vec4 sceneSec = texture(DiffuseSampler0,texCoord);

	fragColor = sceneSec + scene;
}




