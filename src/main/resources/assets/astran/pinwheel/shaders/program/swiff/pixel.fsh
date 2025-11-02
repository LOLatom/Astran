uniform sampler2D EffectSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {

    vec4 tex = texture(EffectSampler0, texCoord);


    float factor = 200;

    vec2 p = texCoord.xy / (tex.xy);
    vec2 uv = vec2(ivec2(texCoord * factor)) / factor;

    vec4 pixelated = texture(EffectSampler0, uv);

    fragColor = pixelated;

}


