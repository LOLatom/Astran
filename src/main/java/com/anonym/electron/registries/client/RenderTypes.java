package com.anonym.electron.registries.client;

import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderTypes {
    private static final ResourceLocation RENDERTYPE = ResourceLocation.fromNamespaceAndPath("electron", "bloom_rendertype");


    public static RenderType getBloomRenderType(ResourceLocation textureFormattingArg) {
        return VeilRenderType.get(RENDERTYPE, textureFormattingArg.toString());
    }
}
