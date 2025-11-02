uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec4 scene = texture(EffectSampler0,texCoord);
    vec4 mainScene = texture(DiffuseSampler0,texCoord);
    mainScene.a = 0;

    float offset = 0.0015;
    vec4 r = texture(EffectSampler0, texCoord + vec2(offset,offset));
    vec4 g = texture(EffectSampler0, texCoord);
    vec4 b = texture(EffectSampler0, texCoord - vec2(offset,offset));

    vec4 added = vec4(r.r,g.g,b.b,r.a+g.a+b.a);
    if (scene.a > 0.1) {
        vec4 final = mix(scene,added,1.);
        final.a = clamp(final.a,0.,1.);
        fragColor = final;
    } else {
        if (added.a > 0.1) {
            added.a = 1;
        }
        fragColor = mainScene + added;
    }

}


