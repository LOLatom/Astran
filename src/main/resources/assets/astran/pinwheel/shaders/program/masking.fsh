#version 330 core
#line 0 1
#line 3 0
/* #version 150 */
vec4 linear_fog(vec4 inColor, float vertexDistance, float fogStart, float fogEnd, vec4 fogColor) {
    if(vertexDistance <= fogStart) {
        return inColor;
    }
    float fogValue = vertexDistance < fogEnd ? smoothstep(fogStart, fogEnd, vertexDistance) : 1.0;
    return vec4(mix(inColor.rgb, fogColor.rgb, (fogValue * fogColor.a)), inColor.a);
}
float linear_fog_fade(float vertexDistance, float fogStart, float fogEnd) {
    if(vertexDistance <= fogStart) {
        return 1.0;
    } else {
        if(vertexDistance >= fogEnd) {
            return 0.0;
        }
    }
    return smoothstep(fogEnd, fogStart, vertexDistance);
}
float fog_distance(vec3 pos, int shape) {
    if(shape == 0) {
        return length(pos);
    } else {
        float distXZ = length(pos.xz);
        float distY = abs(pos.y);
        return max(distXZ, distY);
    }
}
uniform sampler2D Sampler0;
uniform sampler2D Sampler3;
uniform sampler2D Sampler4;
uniform sampler2D Sampler5;
uniform sampler2D Sampler6;
uniform sampler2D Sampler7;
uniform sampler2D Sampler8;
uniform sampler2D Sampler9;
uniform sampler2D Sampler10;
uniform sampler2D Sampler11;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
out vec4 fragColor;
void main() {
    vec4 color = texture(Sampler0, texCoord0);
    vec4 mask1 = texture(Sampler3,texCoord0);
    vec4 mask2 = texture(Sampler4,texCoord0);
    vec4 mask3 = texture(Sampler5,texCoord0);
    vec4 mask4 = texture(Sampler6,texCoord0);
    vec4 mask5 = texture(Sampler7,texCoord0);
    vec4 mask6 = texture(Sampler8,texCoord0);
    vec4 mask7 = texture(Sampler9,texCoord0);
    vec4 mask8 = texture(Sampler10,texCoord0);
    vec4 mask9 = texture(Sampler11,texCoord0);


    if (color.a < 0.1) {
        discard;
    }
    if (mask1.rgb == 0.) {
        discard;
    }
    if (mask2.rgb == 0.) {
        discard;
    }
    if (mask3.rgb == 0.) {
        discard;
    }
    if (mask4.rgb == 0.) {
        discard;
    }
    if (mask5.rgb == 0.) {
        discard;
    }
    if (mask6.rgb == 0.) {
        discard;
    }
    if (mask7.rgb == 0.) {
        discard;
    }
    if (mask8.rgb == 0.) {
        discard;
    }
    if (mask9.rgb == 0.) {
        discard;
    }

    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    fragColor = color;
    //fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}





































































