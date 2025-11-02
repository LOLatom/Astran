uniform sampler2D EffectSampler0;  // The element / target
uniform sampler2D DiffuseSampler0; // The background
uniform float GameTime;
uniform float Intensity;
uniform float Size;
uniform float TimeMul;

in vec2 texCoord;
out vec4 fragColor;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453) * 2.0 - 1.0;
}

float offset(float blocks, vec2 uv) {
    return rand(vec2(GameTime * (0.02 * TimeMul), floor(uv.y * blocks)));
}

void main()
{

    vec4 col = texture(EffectSampler0, texCoord);
    vec4 dif = texture(DiffuseSampler0, texCoord);
    vec4 r = texture(EffectSampler0, texCoord + vec2(offset((64.0 / Size), texCoord) * (0.01 * Intensity), 0.0));
    vec4 g = texture(EffectSampler0, texCoord + vec2(offset((32.0/ Size), texCoord) * (0.01 * Intensity) * 0.16666666, 0.0));
    vec4 b = texture(EffectSampler0, texCoord + vec2(offset((16.0 / Size), texCoord) * (0.01 * Intensity), 0.0));

    //vec4 added = vec4(r.r,g.g,b.b,r.a+g.a+b.a);
    vec4 added = r + g + b;
    added.a = clamp(added.a,0.,1.);

    if (added.a > 0.1) {
        vec4 final = mix(col , added * 0.35, 1.);
        final.a = clamp(final.a,0.,1.);
        fragColor = final;
    } else {
        if (added.a > 0.1) {
            //added.a = 1;
        }
        fragColor = mix(dif , added,1.);
    }



}