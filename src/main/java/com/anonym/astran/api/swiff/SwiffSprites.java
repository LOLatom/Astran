package com.anonym.astran.api.swiff;

import com.anonym.astran.api.swiff.tools.SwiffTexture;
import net.minecraft.resources.ResourceLocation;

public class SwiffSprites {
    private static final ResourceLocation DARK_BUTTON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("astran","textures/swiff/buttons/swiff_dark_button.png");
    private static final ResourceLocation TESTING =
            ResourceLocation.fromNamespaceAndPath("astran","textures/item/astranium_core.png");
    private static final ResourceLocation ENCHANTED_PLANE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("astran","textures/swiff/plane/enchanted_plane.png");

    public static final SwiffTexture DARK_BUTTON = nineSlice(DARK_BUTTON_TEXTURE,7,7,3);

    public static final SwiffTexture TEST = stretched(TESTING,16,16);
    public static final SwiffTexture ENCHANTED_PLANE = nineSlice(ENCHANTED_PLANE_TEXTURE,48,48,17);


    public static SwiffTexture nineSlice(ResourceLocation tex, int width, int height, int slice) {
        return new SwiffTexture.Sliced(tex,width,height,slice);
    }
    public static SwiffTexture stretched(ResourceLocation tex, int width, int height) {
        return new SwiffTexture.Stretched(tex,width,height);
    }

}
