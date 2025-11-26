uniform sampler2D Sampler0;
uniform vec2 BleedSize;
uniform vec2 TextureSize;
uniform float SliceSize;

in vec2 texCoord;
out vec4 fragColor;

vec4 getBleededTarget(sampler2D tex, vec2 Coordonate, vec2 Bleeding, vec2 TexSize, vec2 Sub , vec2 Add) {
    vec2 shrunkUV = (Coordonate + Bleeding) / ( -TexSize);
    vec4 texColor = texture(tex, shrunkUV);
    if (any(lessThan(shrunkUV, vec2(0.0) + Add)) || any(greaterThan(shrunkUV, vec2(1.0) - Sub))) {
        texColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
    return texColor;
}

void main()
{
fragColor = getBleededTarget(Sampler0,texCoord,BleedSize,TextureSize,vec2(0.30,0.3),vec2(0.30,0.));
}