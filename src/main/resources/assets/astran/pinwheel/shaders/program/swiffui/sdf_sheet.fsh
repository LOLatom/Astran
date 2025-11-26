uniform vec2 Pos[50];
uniform int IDs[50];
uniform float Radius[50];
uniform vec4 Color[50];
uniform vec4 WHAB[50];
uniform int Size;
uniform float BlendK;

in vec2 texCoord;
out vec4 fragColor;

float sdCircle( vec2 p, float r )
{
    return length(p) - r;
}
float sdPentagon( vec2 p, float r )
{
    const vec3 k = vec3(0.809016994,0.587785252,0.726542528);
    p.x = abs(p.x);
    p -= 2.0*min(dot(vec2(-k.x,k.y),p),0.0)*vec2(-k.x,k.y);
    p -= 2.0*min(dot(vec2( k.x,k.y),p),0.0)*vec2( k.x,k.y);
    p -= vec2(clamp(p.x,-r*k.z,r*k.z),r);
    return length(p)*sign(p.y);
}
float sdHexagram( vec2 p, float r )
{
    const vec4 k = vec4(-0.5,0.8660254038,0.5773502692,1.7320508076);
    p = abs(p);
    p -= 2.0*min(dot(k.xy,p),0.0)*k.xy;
    p -= 2.0*min(dot(k.yx,p),0.0)*k.yx;
    p -= vec2(clamp(p.x,r*k.z,r*k.w),r);
    return length(p)*sign(p.y);
}
float sdPentagram(vec2 p, float r )
{
    const float k1x = 0.809016994; // cos(π/ 5) = ¼(√5+1)
    const float k2x = 0.309016994; // sin(π/10) = ¼(√5-1)
    const float k1y = 0.587785252; // sin(π/ 5) = ¼√(10-2√5)
    const float k2y = 0.951056516; // cos(π/10) = ¼√(10+2√5)
    const float k1z = 0.726542528; // tan(π/ 5) = √(5-2√5)
    const vec2  v1  = vec2( k1x,-k1y);
    const vec2  v2  = vec2(-k1x,-k1y);
    const vec2  v3  = vec2( k2x,-k2y);

    p.x = abs(p.x);
    p -= 2.0*max(dot(v1,p),0.0)*v1;
    p -= 2.0*max(dot(v2,p),0.0)*v2;
    p.x = abs(p.x);
    p.y -= r;
    return length(p-v3*clamp(dot(p,v3),0.0,k1z*r))
    * sign(p.y*v3.x-p.x*v3.y);
}
float sdBox( vec2 p, vec2 b )
{
    vec2 d = abs(p)-b;
    return length(max(d,0.0)) + min(max(d.x,d.y),0.0);
}
float sdOctogon( vec2 p, float r )
{
    const vec3 k = vec3(-0.9238795325, 0.3826834323, 0.4142135623 );
    p = abs(p);
    p -= 2.0*min(dot(vec2( k.x,k.y),p),0.0)*vec2( k.x,k.y);
    p -= 2.0*min(dot(vec2(-k.x,k.y),p),0.0)*vec2(-k.x,k.y);
    p -= vec2(clamp(p.x, -k.z*r, k.z*r), r);
    return length(p)*sign(p.y);
}
float sdRhombus( vec2 p, vec2 b )
{
    b.y = -b.y;
    p = abs(p);
    float h = clamp( (dot(b,p)+b.y*b.y)/dot(b,b), 0.0, 1.0 );
    p -= b*vec2(h,h-1.0);
    return length(p)*sign(p.x);
}

vec2 smin( float a, float b, float k )
{
    float h = 1.0 - min( abs(a-b)/(4.0*k), 1.0 );
    float w = h*h;
    float m = w*0.5;
    float s = w*k;
    return (a<b) ? vec2(a-s,m) : vec2(b-s,1.0-m);
}

float chooseFromOption(int id) {
    float dis = 0.;
    if (IDs[id] == 0) {
        dis = sdCircle(texCoord - Pos[id],Radius[id]);
    } else if (IDs[id] == 1) {
        dis = sdPentagon(texCoord - Pos[id],Radius[id]);
    } else if (IDs[id] == 2) {
        dis = sdBox(texCoord - Pos[id],WHAB[id].xy);
    } else if (IDs[id] == 3) {
        dis = sdRhombus(texCoord - Pos[id],WHAB[id].xy);
    } else if (IDs[id] == 4) {
        dis = sdPentagram(texCoord - Pos[id],Radius[id]);
    } else if (IDs[id] == 5) {
        dis = sdOctogon(texCoord - Pos[id],Radius[id]);
    } else if (IDs[id] == 6) {
        dis = sdHexagram(texCoord - Pos[id],Radius[id]);
    } else {
        dis = sdBox(texCoord - Pos[id],WHAB[id].xy);
    }
    return dis;
}

void main()
{


    vec2 d = vec2(chooseFromOption(0),0.);
    vec4 sdcolor = Color[0].rgba;
    if (Size > 1) {
        for (int i = 1; i < Size; ++i) {
            d = smin(d.x,chooseFromOption(i),BlendK);
            vec4 nexCol = Color[i].rgba;
            sdcolor = mix(sdcolor,nexCol,d.y);
        }
    }
    float maxed = max(d.x,-0.0001);

    vec4 col = (maxed < 0.0) ? sdcolor : vec4(1.,1.,1.,0.);

    if (col ==  vec4(1.,1.,1.,0.)) {
        discard;
    }

    fragColor = col;
}