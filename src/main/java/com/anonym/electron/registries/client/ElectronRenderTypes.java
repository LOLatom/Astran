package com.anonym.electron.registries.client;

import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ElectronRenderTypes {
    private static final ResourceLocation BLOOM = ResourceLocation.fromNamespaceAndPath("electron", "bloom_rendertype");

    private static final ResourceLocation TRAIL = ResourceLocation.fromNamespaceAndPath("electron", "trail_rendertype");


    public static RenderType getBloomRenderType(ResourceLocation textureFormattingArg) {
        return VeilRenderType.get(BLOOM, textureFormattingArg.toString());
    }
    public static RenderType getTrailRenderType(ResourceLocation textureFormattingArg) {
        return VeilRenderType.get(TRAIL, textureFormattingArg.toString());
    }
}
