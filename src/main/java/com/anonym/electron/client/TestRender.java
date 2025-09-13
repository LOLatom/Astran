package com.anonym.electron.client;

import com.anonym.electron.registries.client.RenderTypes;
import com.anonym.electron.server.TestEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;

import static com.anonym.electron.Electron.MODID;

public class TestRender extends EntityRenderer<TestEntity> {


    public TestRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        RenderType type = RenderTypes.getBloomRenderType(ResourceLocation.fromNamespaceAndPath("electron","textures/entity/test.png"));


    }


    @Override
    public ResourceLocation getTextureLocation(TestEntity testEntity) {
        return null;
    }
}
