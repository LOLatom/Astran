uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
out vec4 fragColor;

float easeOutSine(float x) {
    return sin((x * 3.141592653) / 2.0);

}
float easeOutExpo(float x) {
    if (x == 1.) {
        return 1.;
    } else {
        return 1. - pow(2., -10. * x);
    }
}
float easeInExpo(float x) {
    if (x == 0.) {
        return 0.;
    } else {
        return pow(2., 10. * x - 10.);
    }
}

vec2 hash( vec2 p ) // replace this by something better
{
    p = vec2( dot(p,vec2(127.1,311.7)), dot(p,vec2(269.5,183.3)) );
    return -1.0 + 2.0*fract(sin(p)*43758.5453123);
}

float noise( in vec2 p )
{
    const float K1 = 0.366025404; // (sqrt(3)-1)/2;
    const float K2 = 0.211324865; // (3-sqrt(3))/6;

    vec2  i = floor( p + (p.x+p.y)*K1 );
    vec2  a = p - i + (i.x+i.y)*K2;
    float m = step(a.y,a.x);
    vec2  o = vec2(m,1.0-m);
    vec2  b = a - o + K2;
    vec2  c = a - 1.0 + 2.0*K2;
    vec3  h = max( 0.5-vec3(dot(a,a), dot(b,b), dot(c,c) ), 0.0 );
    vec3  n = h*h*h*h*vec3( dot(a,hash(i+0.0)), dot(b,hash(i+o)), dot(c,hash(i+1.0)));
    return dot( n, vec3(70.0) );
}

// -----------------------------------------------

void main()
{

    vec4 tex = texture(Sampler0, texCoord0) /1.1;

    vec2 p = texCoord0.xy / (tex.xy);


    vec2 uv = p*vec2(tex.x/tex.y,1.0);
    uv = vec2(ivec2(uv * 28.0)) / 32;
    float y = uv.y;
    uv.y -= GameTime * 1000;

    float f = 0.0;


    uv *= 5.0;
    mat2 m = mat2( 1.6,  1.2, -1.2,  1.6 );
    f  = 0.5000*noise( uv); uv = m*uv;
    f += 0.2500*noise( uv); uv = m*uv;
    f += 0.1250*noise( uv); uv = m*uv;
    f += 0.0625*noise( uv); uv = m*uv;


    f = 0.5 + 0.5*f;

    f *= easeInExpo(1.2 - y);

    //fragColor = vec4(1.,1.,1.,1.);
    //fragColor = texture(Sampler0,texCoord0);
    fragColor = (vec4( f, f, f, 0.0 ) * vec4(2.4,1.4,1.8,1.));
}