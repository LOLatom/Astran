uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;
uniform float Radius;


in vec2 texCoord;
out vec4 fragColor;

const float sigma = 3.0;

float gaussian(float x, float sigma) {
    return exp(-(x * x) / (2.0 * sigma * sigma));
}

void main() {
    vec4 e = texture(EffectSampler0, texCoord);
    vec2 texelSize = 1.0 / vec2(textureSize(EffectSampler0, 0));

    vec3 accumColor = vec3(0.0);
    float accumAlpha = 0.0;
    float totalWeight = 0.0;

    for (float x = -Radius; x <= Radius; x++) {
        for (float y = -Radius; y <= Radius; y++) {
            vec2 offset = vec2(x, y) * texelSize;
            vec4 elemSample = texture(EffectSampler0, texCoord + offset); //woaf woaf grr grr skibidi
            vec4 bgSample  = vec4(0,0,0,0);

            vec4 thi = mix(bgSample, elemSample, elemSample.a);

        float weight = gaussian(length(offset), sigma);
        accumColor += thi.rgb * thi.a * weight;
        accumAlpha += thi.a * weight;
        totalWeight += weight;
        }
    }

    vec3 blurredColor = accumColor / max(accumAlpha, 0.000000001);



    float blurredAlpha = accumAlpha / max(totalWeight, 0.0001);

    fragColor = vec4(blurredColor, blurredAlpha);

}