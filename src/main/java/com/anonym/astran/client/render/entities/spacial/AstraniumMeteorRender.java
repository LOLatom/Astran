package com.anonym.astran.client.render.entities.spacial;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.LayerRegistry;
import com.anonym.astran.common.entities.AstraniumMeteor;
import com.anonym.astran.helpers.VertexHelper;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;

public class AstraniumMeteorRender extends EntityRenderer<AstraniumMeteor> {

    private ModelPart model = null;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_texture.png");
    private static final ResourceLocation BREAK1 = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_break1.png");
    private static final ResourceLocation BREAK2 = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_break2.png");
    private static final ResourceLocation BREAK3 = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_break3.png");
    private static final ResourceLocation BREAK4 = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_break4.png");
    private static final ResourceLocation TEXTURE_BLOOM = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/meteor/astranium_meteor_bloom.png");
    private static final ResourceLocation RENDERTYPE_FALL = ResourceLocation.fromNamespaceAndPath(Astran.MODID,
            "textures/entity/random_texture.png");



    public AstraniumMeteorRender(EntityRendererProvider.Context context) {
        super(context);
        this.model = context.bakeLayer(LayerRegistry.ASTRANIUM_METEOR);
    }


    @Override
    public void render(AstraniumMeteor p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if (this.model != null) {
            poseStack.pushPose();
            if (p_entity.landed()) {
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
            }
            poseStack.translate(0,-1.5,0);
            if (!p_entity.landed()) {
                poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, p_entity.yRotO, p_entity.getYRot()) - 90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, p_entity.xRotO - 90, p_entity.getXRot() - 90)));
            }
            ResourceLocation tex =
                    p_entity.getBreakingStage() == 0 ? TEXTURE :
                    p_entity.getBreakingStage() == 1 ? BREAK1 :
                    p_entity.getBreakingStage() == 2 ? BREAK2 :
                    p_entity.getBreakingStage() == 3 ? BREAK3 : BREAK4;

            this.model.render(poseStack,
                    bufferSource.getBuffer(RenderType.entityCutoutNoCull(tex)),
                    packedLight, OverlayTexture.NO_OVERLAY);

            float a = (float) ((Math.clamp(p_entity.getFloorDistance(),5,105)-5) / 100);

            RenderType bloom = AstranRenderTypes.getBloomRenderType(TEXTURE_BLOOM);
            if (bloom != null) {

                if (a < 0.2f) {
                    this.model.render(poseStack,
                            bufferSource.getBuffer(bloom),
                            packedLight, OverlayTexture.NO_OVERLAY);
                }

            }
            poseStack.translate(0,-0.5,0);
            RenderType type = AstranRenderTypes.getTrailRenderType(RENDERTYPE_FALL);
            if (type!= null) {
                VertexConsumer vertexconsumer = bufferSource.getBuffer(type);

                PoseStack.Pose posestack$pose = poseStack.last();


                if (a > 0.0f) {
                    float finalSize = 11f;
                    poseStack.translate(0,-((finalSize* a)/2),0);
                    VertexHelper.uvCube(vertexconsumer, posestack$pose, 3.1f, 4.1f + (a * finalSize), 3.1f, packedLight, new Color(1f, 1f, 1f, a).getAlpha());
                }

            }
            if (Minecraft.getInstance().crosshairPickEntity != null) {
                if (Minecraft.getInstance().crosshairPickEntity.equals(p_entity)) {
                    VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.LINES);

                    PoseStack.Pose posestack$pose = poseStack.last();

                    VertexHelper.uvCube(vertexconsumer, posestack$pose, 3f, 4f, 3f, packedLight,0,0,0,85);

                }
            }
            poseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(AstraniumMeteor astraniumMeteor) {
        return TEXTURE;
    }
}
