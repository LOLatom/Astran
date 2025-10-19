package com.anonym.astran.registries.client;

import com.anonym.astran.Astran;
import foundry.veil.api.client.registry.VeilShaderBufferRegistry;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class AstranRenderTypes {
    private static final ResourceLocation BLOOM = ResourceLocation.fromNamespaceAndPath("astran", "bloom_rendertype");

    private static final ResourceLocation TRAIL = ResourceLocation.fromNamespaceAndPath("astran", "trail_rendertype");

    private static final ResourceLocation MASK_BACK = ResourceLocation.fromNamespaceAndPath("astran", "mask_back");
    private static final ResourceLocation MASK_TORSO = ResourceLocation.fromNamespaceAndPath("astran", "mask_torso");
    private static final ResourceLocation MASK_FACE = ResourceLocation.fromNamespaceAndPath("astran", "mask_face");
    private static final ResourceLocation MASK_SKULL = ResourceLocation.fromNamespaceAndPath("astran", "mask_skull");
    private static final ResourceLocation MASK_RIGHT_SHOULDER = ResourceLocation.fromNamespaceAndPath("astran", "mask_right_shoulder");
    private static final ResourceLocation MASK_LEFT_SHOULDER = ResourceLocation.fromNamespaceAndPath("astran", "mask_left_shoulder");
    private static final ResourceLocation MASK_RIGHT_HAND = ResourceLocation.fromNamespaceAndPath("astran", "mask_right_hand");
    private static final ResourceLocation MASK_LEFT_HAND = ResourceLocation.fromNamespaceAndPath("astran", "mask_left_hand");
    private static final ResourceLocation MASK_RIGHT_LEG = ResourceLocation.fromNamespaceAndPath("astran", "mask_right_leg");
    private static final ResourceLocation MASK_LEFT_LEG = ResourceLocation.fromNamespaceAndPath("astran", "mask_left_leg");
    private static final ResourceLocation MASK_HIPS = ResourceLocation.fromNamespaceAndPath("astran", "mask_hips");


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

    public static RenderType getMaskBackRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_BACK, texture.toString());
    }
    public static RenderType getMaskTorsoRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_TORSO, texture.toString());
    }
    public static RenderType getMaskHipsRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_HIPS, texture.toString());
    }
    public static RenderType getMaskFaceRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_FACE, texture.toString());
    }
    public static RenderType getMaskSkullRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_SKULL, texture.toString());
    }
    public static RenderType getMaskRightLegRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_RIGHT_LEG, texture.toString());
    }
    public static RenderType getMaskLeftLegRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_LEFT_LEG, texture.toString());
    }
    public static RenderType getMaskRightHandRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_RIGHT_HAND, texture.toString());
    }
    public static RenderType getMaskLeftHandRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_LEFT_HAND, texture.toString());
    }
    public static RenderType getMaskRightShoulderRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_RIGHT_SHOULDER, texture.toString());
    }
    public static RenderType getMaskLeftShoulderRenderType(ResourceLocation texture) {
        return VeilRenderType.get(MASK_LEFT_SHOULDER, texture.toString());
    }
}
