#include astran:swiff/swiff_ui_func

uniform sampler2D Sampler0;
uniform vec2 BleedSize;

in vec2 texCoord;
out vec4 fragColor;

void main()
{
        vec4 bleeded = getBleededTarget(Sampler0,texCoord,BleedSize);
        if (bleeded.a < 0.1) {
            discard;
        }
    fragColor = bleeded;
}