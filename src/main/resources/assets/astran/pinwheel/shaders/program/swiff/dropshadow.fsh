#version 150

uniform sampler2D EffectSampler0;  // source object (alpha is used)
uniform sampler2D DiffuseSampler0; // background (we force alpha = 0)

// Parameters
uniform float Radius;       // blur radius in pixels (e.g. 4.0)
uniform float Offset;       // offset distance in pixels (how far the shadow is shifted)
uniform float Orientation;  // direction in radians (0 = right, PI/2 = down)
uniform float Intensity;    // shadow opacity multiplier (0..1)
uniform vec4 Color;   // shadow rgb (usually vec3(0.0))
const int DEBUG_SHOW_SAMPLE = 0; // 0 = off, 1 = show sampled center (for debugging)

in vec2 texCoord;
out vec4 fragColor;

// maximum allowed blur radius (safety)
const int MAX_BLUR = 20;

void main() {
    vec3 ShadowColor = Color.rgb;

    vec4 bg = texture(DiffuseSampler0, texCoord);
    bg.a = 0.0;

    vec4 src = texture(EffectSampler0, texCoord);

    ivec2 texSizeI = textureSize(EffectSampler0, 0);
    vec2 texSize = vec2(texSizeI);
    vec2 texel = 1.0 / texSize;

    vec2 dir = vec2(cos(Orientation), sin(Orientation));
    vec2 baseOffsetUV = dir * (Offset * texel);
    vec2 sampleCenter = texCoord - baseOffsetUV;

    if (DEBUG_SHOW_SAMPLE == 1) {
        vec4 sampled = texture(EffectSampler0, sampleCenter);
        fragColor = vec4(sampled.a * ShadowColor.r, sampled.a * ShadowColor.g, sampled.a * ShadowColor.b, sampled.a * Intensity);
        return;
    }

    int r = int(ceil(Radius));
    r = clamp(r, 0, MAX_BLUR);

    float sigma = max(Radius * 0.5, 0.0001);
    float twoSigmaSq = 2.0 * sigma * sigma;

    // --- Horizontal pass ---
    float accumH = 0.0;
    float wsumH = 0.0;
    for (int i = -MAX_BLUR; i <= MAX_BLUR; i++) {
        if (i < -r || i > r) continue;

        float d = float(i);
        float w = exp(-(d * d) / twoSigmaSq);

        vec2 uv = sampleCenter + vec2(d * texel.x, 0.0);
        float a = texture(EffectSampler0, uv).a;

        accumH += a * w;
        wsumH += w;
    }
    float horizBlur = (wsumH > 0.0) ? (accumH / wsumH) : 0.0;

    // --- Vertical pass (blurred horizontally as input) ---
    float accumV = 0.0;
    float wsumV = 0.0;
    for (int j = -MAX_BLUR; j <= MAX_BLUR; j++) {
        if (j < -r || j > r) continue;

        float d = float(j);
        float w = exp(-(d * d) / twoSigmaSq);

        vec2 uv = sampleCenter + vec2(0.0, d * texel.y);
        // Here we reuse the horizontally blurred value by weighting it vertically
        float a = texture(EffectSampler0, uv).a;

        accumV += a * w;
        wsumV += w;
    }

    float maskAlpha = ((wsumV + wsumH) > 0.0) ? ((accumV  + accumH) / (wsumV + wsumH)) : 0.0;
    maskAlpha *= Intensity;
    maskAlpha *= (1.0 - src.a);

    vec4 shadowCol = vec4(ShadowColor, clamp(maskAlpha, 0.0, 1.0));

    vec4 withShadow = mix(bg, shadowCol, shadowCol.a);
    vec4 outCol = mix(withShadow, src, src.a);

    fragColor = outCol;
}