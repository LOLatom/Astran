uniform sampler2D EffectSampler0;   // element
uniform sampler2D DiffuseSampler0;  // background
in vec2 texCoord;
out vec4 fragColor;

const float blurRadius = 8.0;
const float sigma = 3.0;

float gaussian(float x, float sigma) {
    return exp(-(x * x) / (2.0 * sigma * sigma));
}

// Simple luminance / intensity measure
float luminance(vec4 col) {
    return max(max(col.r, col.g), col.b);
}

//THIS IS FOR TEST NOT MADE BY ME AT ALL

void main() {
    vec4 scene = texture(EffectSampler0, texCoord);
    vec4 mainScene = texture(DiffuseSampler0, texCoord);

    vec2 texelSize = 1.0 / vec2(textureSize(EffectSampler0, 0));
    vec4 blurred = vec4(0.0);
    float totalWeight = 0.0;

    // Gaussian blur with pre-compositing
    for (float x = -blurRadius; x <= blurRadius; x++) {
        for (float y = -blurRadius; y <= blurRadius; y++) {
            vec2 offset = vec2(x, y) * texelSize;

            vec4 elemSample = texture(EffectSampler0, texCoord + offset);
            vec4 bgSample = texture(DiffuseSampler0, texCoord + offset);

            // Pre-composite element over background for outside-edge blur
            vec4 comp = mix(bgSample, elemSample, elemSample.a);

            // Weight based on intensity
            float weight = gaussian(length(offset), sigma) * luminance(comp);
            blurred += comp * weight;
            totalWeight += weight;
        }
    }

    blurred /= max(totalWeight, 0.0001);

    // Optional chromatic aberration
    float chromaOffset = 0.0015;
    float r = texture(EffectSampler0, texCoord + vec2(chromaOffset, chromaOffset)).r;
    float g = texture(EffectSampler0, texCoord).g;
    float b = texture(EffectSampler0, texCoord - vec2(chromaOffset, chromaOffset)).b;
    vec4 chroma = vec4(r, g, b, 1.0);

    // Only blend where the element actually exists
    float mask = luminance(blurred + chroma);
    //fragColor = chroma;
    if (mask < 0.05) {
        fragColor = mainScene; // outside the element
    } else {
        fragColor = mix(mainScene, blurred + chroma * 0.2, 0.8);
    }
}