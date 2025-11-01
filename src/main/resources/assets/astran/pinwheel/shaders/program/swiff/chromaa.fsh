uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;

in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec4 scene = texture(EffectSampler0,texCoord);
    vec4 mainScene = texture(DiffuseSampler0,texCoord);

    float offset = 0.0015;
    float r = texture(EffectSampler0, texCoord + vec2(offset,offset)).r;
    float g = texture(EffectSampler0, texCoord).g;
    float b = texture(EffectSampler0, texCoord - vec2(offset,offset)).b;

    vec4 added = vec4(r,g,b,1.);

    if (added.r < 0.1 && added.b < 0.1 && added.g < 0.1) {
        discard;
    }

    if (scene.a > 0.1) {
        fragColor = added;
    } else {
        if (added.a > 0.1) {
            fragColor = mainScene + added;
        } else {
            fragColor = scene;
        }
    }


}


