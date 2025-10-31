uniform sampler2D EffectSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec4 scene = texture(EffectSampler0, texCoord + vec2(cos(texCoord.y * 50.)*0.1,0.));
    fragColor = scene;
}
