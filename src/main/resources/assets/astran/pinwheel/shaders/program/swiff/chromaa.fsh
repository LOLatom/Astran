uniform sampler2D EffectSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec4 scene = texture(EffectSampler0,texCoord);

    float offset = 0.0015;
    vec4 r = texture(EffectSampler0, texCoord + vec2(offset,offset)).rrra;
    vec4 g = texture(EffectSampler0, texCoord).ggga;
    vec4 b = texture(EffectSampler0, texCoord - vec2(offset,offset)).bbba;

    fragColor = vec4(r.r,g.g,b.b,(r.a/3.)+(g.a/3.)+(b.a/3.));
}


