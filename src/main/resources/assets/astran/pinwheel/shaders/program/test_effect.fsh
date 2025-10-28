uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D VeilDynamicNormal;

uniform vec2 ScreenSize;
uniform float GameTime;


in vec2 texCoord;
out vec4 fragColor;

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


void main() {

vec4 tex = texture(DiffuseSampler0, texCoord);

	vec4 normal = texture(DiffuseSampler0, texCoord);
	vec4 depth = texture(DiffuseDepthSampler, texCoord);
	
	vec2 offset = vec2(0.001,0.001);
	
	float factor = 200.;

    vec2 p = texCoord.xy / (tex.xy);
    vec2 uv = vec2(ivec2(texCoord * factor)) / factor;
	
	float depthR = 1. - texture(DiffuseDepthSampler, uv + offset).r;
	float depthG = 1. - texture(DiffuseDepthSampler, uv).r;
	float depthB = 1. - texture(DiffuseDepthSampler, uv - offset).r;
	
	float semiLinearDepth = clamp((1. - depth.r)*40.,0.,1.);

	vec4 addedDepth = vec4(semiLinearDepth,semiLinearDepth,semiLinearDepth,1.);

	
    
	vec4 pixelated = texture(DiffuseSampler0, uv);
	
	vec2 liv = texCoord.yx;
	
	liv *=  1.0 - texCoord.yx;   
    float vig = liv.x*liv.y * 15.0; 
    vig = pow(vig, 0.15); 
    
    float time = GameTime * 200;
    
    vec4 dd = (addedDepth - vec4(depthR,depthR,depthR,1.));

	vec4 seee = vec4(depthR*40.,depthG*40.,depthB*40.,1.);
	fragColor = texture(VeilDynamicNormal, texCoord);
	
	}





