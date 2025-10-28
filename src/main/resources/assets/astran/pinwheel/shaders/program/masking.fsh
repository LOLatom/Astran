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
uniform sampler2D Mask1;
uniform sampler2D Mask2;
uniform sampler2D Mask3;
uniform sampler2D Mask4;
uniform sampler2D Mask5;
uniform sampler2D Mask6;
uniform sampler2D Mask7;
uniform sampler2D Mask8;
uniform sampler2D Mask9;
uniform sampler2D Mask10;
uniform sampler2D Mask11;
uniform sampler2D Mask12;
uniform sampler2D Mask13;

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
    vec4 mask1 = texture(Mask1,texCoord0);
    vec4 mask2 = texture(Mask2,texCoord0);
    vec4 mask3 = texture(Mask3,texCoord0);
    vec4 mask4 = texture(Mask4,texCoord0);
    vec4 mask5 = texture(Mask5,texCoord0);
    vec4 mask6 = texture(Mask6,texCoord0);
    vec4 mask7 = texture(Mask7,texCoord0);
    vec4 mask8 = texture(Mask8,texCoord0);
    vec4 mask9 = texture(Mask9,texCoord0);
    vec4 mask10 = texture(Mask9,texCoord0);
    vec4 mask11 = texture(Mask9,texCoord0);
    vec4 mask12 = texture(Mask9,texCoord0);
    vec4 mask13 = texture(Mask9,texCoord0);

    if (color.a < 0.1) {
        discard;
    }
    if (mask1.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask2.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask3.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask4.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask5.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask6.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask7.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask8.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask9.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask10.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask11.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask12.rgb == vec3(0.0F)) {
        discard;
    }
    if (mask13.rgb == vec3(0.0F)) {
        discard;
    }

    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    fragColor = color;
    //fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}