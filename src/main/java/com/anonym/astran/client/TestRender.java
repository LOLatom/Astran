package com.anonym.astran.client;

import com.anonym.astran.helpers.VertexHelper;
import com.anonym.astran.registries.client.ElectronRenderTypes;
import com.anonym.astran.server.TestEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TestRender extends EntityRenderer<TestEntity> {


    public TestRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        RenderType type = ElectronRenderTypes.getTrailRenderType(ResourceLocation.fromNamespaceAndPath("astran","textures/entity/random_texture.png"));
        if (type!= null) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(type);
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.translate(0,-2,0);
            PoseStack.Pose posestack$pose = poseStack.last();
            //System.out.println("CUBE");
            VertexHelper.uvCube(vertexconsumer, posestack$pose, 3, 4, 3, packedLight,255);
            poseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TestEntity testEntity) {
        return null;
    }
}
