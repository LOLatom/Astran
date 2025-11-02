uniform sampler2D EffectSampler0;
uniform sampler2D DiffuseSampler0;

uniform float GameTime;
uniform float TimeMul;
uniform float Frequency;
uniform float Size;

in vec2 texCoord;
out vec4 fragColor;


void main() {

    vec4 bg = texture(DiffuseSampler0, texCoord);
    bg.a = 0;
    float time = GameTime * TimeMul;
    float offsetY = sin(texCoord.x * Frequency + time) * (Size * 0.01);

    vec2 distortedUV = texCoord + vec2(0.0, offsetY);

    vec4 element = texture(EffectSampler0, distortedUV);

    fragColor = mix(bg, element, element.a);
}


