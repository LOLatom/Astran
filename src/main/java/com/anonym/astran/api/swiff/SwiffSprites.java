package com.anonym.astran.api.swiff;

import com.anonym.astran.api.swiff.tools.SwiffTexture;
import net.minecraft.resources.ResourceLocation;

public class SwiffSprites {
    private static final ResourceLocation DARK_BUTTON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("astran","textures/swiff/buttons/swiff_dark_button.png");

    public static final SwiffTexture DARK_BUTTON = nineSlice(DARK_BUTTON_TEXTURE,7,7,3);



    public static SwiffTexture nineSlice(ResourceLocation tex, int width, int height, int slice) {
        return new SwiffTexture.Sliced(tex,width,height,slice);
    }

}
