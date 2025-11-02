uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {

    vec4 tex = texture(EffectSampler0, texCoord);
    vec4 tex2 = texture(DiffuseSampler0, texCoord);
    tex2.a = 0;

    fragColor = tex2 + tex;

}


