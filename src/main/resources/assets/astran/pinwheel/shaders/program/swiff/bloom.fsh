uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;
uniform float Intensity = 40.4;
uniform float Size = 10.0;
uniform float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);
uniform float GameTime;


in vec2 texCoord;
out vec4 fragColor;


void main() {
    vec3 scene = texture(DiffuseSampler0, texCoord).rgb;
    vec4 sceneSec = texture(EffectSampler0, texCoord);
    vec4 scenec = texture(DiffuseSampler0, texCoord);


    vec2 tex_offset = 1.0 / textureSize(EffectSampler0, 0);
    vec3 result = texture(EffectSampler0, texCoord).rgb * weight[0];
    for(int i = 1; i < 7; ++i)
    {
        result += texture(EffectSampler0, texCoord + vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
        result += texture(EffectSampler0, texCoord - vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
    }
    for(int i = 1; i < 7; ++i)
    {
        result += texture(EffectSampler0, texCoord + vec2(0.0, tex_offset.y * i)).rgb * weight[i];
        result += texture(EffectSampler0, texCoord - vec2(0.0, tex_offset.y * i)).rgb * weight[i];
    }


    if (sceneSec.a > 0.1) {
        fragColor = vec4(scene + result * Intensity, 1.0);
    } else {
        if (re.a > 0.1) {
            fragColor = vec4(scene + result * Intensity, 1.0);
        } else {
            fragColor = vec4(scene + result * Intensity, 1.0);
        }
    }
        //fragColor = vec4(scene + result * Intensity, 1.0);

}


































































