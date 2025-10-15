package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.client.models.modules.torso.PoisonFilterModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PoisonFilterModule extends CyberModule {


    public PoisonFilterModule() {
        super("poison_filter_module", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);

        if (inDisplay) {
            VertexConsumer consumer;
            int i = 0;
            for (MaterialType type : module.getMaterials().values()) {
                if (i < 2) {
                    poseStack.pushPose();
                    consumer = buffer.getBuffer(VeilRenderType.entityCutoutNoCull(
                            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/poisonfilter/poison_filter_" + type.getMaterialID() + String.valueOf(i + 1) + ".png")));
                    if (i == 0) {
                        this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());
                    } else {
                        this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
                    }
                    poseStack.popPose();
                }
                i++;
            }
        }
    }

    @Override
    public ModuleModel getModelLayer() {
        return new PoisonFilterModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.POISON_FILTER_MODULE));
    }
}
