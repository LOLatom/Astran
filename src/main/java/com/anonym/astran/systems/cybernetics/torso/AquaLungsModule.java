package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.client.models.modules.torso.AquaLungsModel;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.anonym.astran.systems.energy.INodeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class AquaLungsModule extends CyberModule {


    public AquaLungsModule() {
        super("aqua_lungs_module", LimbType.TORSO);
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
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/aqualungs/aqua_lungs_" + type.getMaterialID() + String.valueOf(i + 1) + ".png")));
                if (i == 0) {
                    this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());
                } else {
                    this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
                }
                poseStack.popPose();
                i++;
            }


        }
    }

    @Override
    public ModuleModel getModelLayer() {
        return new AquaLungsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.AQUA_LUNGS_MODULE));
    }
}
