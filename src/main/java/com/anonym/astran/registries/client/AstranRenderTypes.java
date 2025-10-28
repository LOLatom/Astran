package com.anonym.astran.registries.client;

import com.anonym.astran.Astran;
import foundry.veil.api.client.registry.VeilShaderBufferRegistry;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class AstranRenderTypes {
    private static final ResourceLocation BLOOM = ResourceLocation.fromNamespaceAndPath("astran", "bloom_rendertype");

    private static final ResourceLocation TRAIL = ResourceLocation.fromNamespaceAndPath("astran", "trail_rendertype");


    private static final ResourceLocation MASKING = ResourceLocation.fromNamespaceAndPath("astran", "masking");


    public static RenderType getBloomRenderType(ResourceLocation textureFormattingArg) {
        return VeilRenderType.get(BLOOM, textureFormattingArg.toString());
    }
    public static RenderType getTrailRenderType(ResourceLocation textureFormattingArg) {
    return VeilRenderType.get(TRAIL, textureFormattingArg.toString());
    }

    public static RenderType getMaskingRenderType(ResourceLocation texture, ResourceLocation mask) {
        return VeilRenderType.get(MASKING, texture.toString(), mask.toString());
    }

}
