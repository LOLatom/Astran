package com.anonym.astran.systems.cybernetics.head;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.anonym.astran.systems.energy.INodeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class EyeModule extends CyberModule {


    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/module/head/eye/eye_module_texture.png");

    public EyeModule() {
        super("eye_module", LimbType.HEAD);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);

        if (inDisplay) {
            VertexConsumer consumer;
            int i = 0;
            for (MaterialType type : module.getMaterials().values()) {

                    poseStack.pushPose();
                    consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/head/eye/eye_module_" + type.getMaterialID() + String.valueOf(i + 1) + ".png")));

                    this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());
                    poseStack.popPose();
                i++;
            }

            SteelHeartReservoirData data = entity.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR);
            if (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().isPresent()) {
                Color color = ((INodeItem) (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().get().getItem())).getNodeColor();

                consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/head/eye/eye_module_glow.png")));
                this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, new Color(
                        (15 / 255) * color.getRed(),(15 / 255) * color.getGreen(),(15 / 255) * color.getBlue()).getRGB());
                consumer = buffer.getBuffer(VeilRenderType.eyes(
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/head/eye/eye_module_glow.png")));
                this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color.getRGB());

            }



        }
    }

    @Override
    public ModuleModel getModelLayer() {
        return new EyeModuleModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.EYE_MODULE));
    }
}
