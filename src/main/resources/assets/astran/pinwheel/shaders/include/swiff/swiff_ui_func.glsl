vec4 getBleededTarget(sampler2D tex, vec2 Coordonate, vec2 Bleeding) {
    vec2 shrunkUV = (Coordonate - 0.5) / (1.0 + 2.0 * Bleeding) + 0.5;
    vec4 texColor = texture(tex, shrunkUV);
    if (any(lessThan(shrunkUV, vec2(0.0))) || any(greaterThan(shrunkUV, vec2(1.0)))) {
        texColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
    return texColor;
}