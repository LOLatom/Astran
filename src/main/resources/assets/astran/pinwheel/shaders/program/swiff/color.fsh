uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;

uniform vec4 Color;

in vec2 texCoord;
out vec4 fragColor;


void main() {

    vec4 tex = texture(EffectSampler0, texCoord);
    fragColor = tex + Color;

}


