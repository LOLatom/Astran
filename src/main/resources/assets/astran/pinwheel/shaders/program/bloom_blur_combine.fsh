uniform sampler2D DiffuseSampler0;
uniform sampler2D BloomColor;
uniform sampler2D BloomColorDepth;
uniform sampler2D DiffuseDepthSampler;
uniform float Intensity = 40.4;
uniform float Size = 10.0;
uniform float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);
uniform float GameTime;


in vec2 texCoord;
out vec4 fragColor;


void main() {
	//ivec2 sz = textureSize(BloomColor, 0);
    //float dx = 1.0 / float(sz.x);
    //float dy = 1.0 / float(sz.y);


    vec3 scene = texture(DiffuseSampler0, texCoord).rgb;
    //vec3 bloom = texture(BloomColor, texCoord).rgb;
    //for (int i = -7; i < 7; i++) {
    	//for (int v = -7; v < 7; v++) {
    	//vec2 x = vec2(dx * (v+(v*1.3)),dy* (i+(i*1.3)));
    	//bloom += texture(BloomColor, texCoord + x).rgb * 0.316216;
    	//bloom += texture(BloomColor, texCoord - x).rgb * 0.316216;
    	//bloom += texture(BloomColor, texCoord + x).rgb * 0.070270;
    	//bloom += texture(BloomColor, texCoord - x).rgb * 0.070270;
    	//}
    //}
    //bloom += texture(BloomColor, texCoord).rgb * 10;
    vec2 tex_offset = 1.0 / textureSize(BloomColor, 0);
    //tex_offset = tex_offset * vec2((sin((texCoord.y*1000) +(GameTime)*2400))*7,0.0);
    vec3 result = texture(BloomColor, texCoord).rgb * weight[0];
    //result =+ texture(BloomColor, texCoord).rgb * vec3(0.302, 0.984, 1);
    for(int i = 1; i < 7; ++i)
    {
        result += texture(BloomColor, texCoord + vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
        result += texture(BloomColor, texCoord - vec2(tex_offset.x * i, 0.0)).rgb * weight[i];
    }
    for(int i = 1; i < 7; ++i)
    {
        result += texture(BloomColor, texCoord + vec2(0.0, tex_offset.y * i)).rgb * weight[i];
        result += texture(BloomColor, texCoord - vec2(0.0, tex_offset.y * i)).rgb * weight[i];
    }
    if (texture(DiffuseDepthSampler, texCoord).r >= texture(BloomColorDepth, texCoord).r) {
        fragColor = vec4(scene + result * Intensity, 1.0);
    } else {
        fragColor = vec4(scene, 1.0);
    }
}


































































