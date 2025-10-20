package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.torso.BackCoverModel;
import com.anonym.astran.client.models.modules.torso.BackWingsModel;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.energy.INodeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class BackWingsModule extends CyberModule {


    public BackWingsModule() {
        super("back_wings", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,0,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            renderWithMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/backwings/back_wings_",2,true);
        } else {
            poseStack.translate(0,8.5f/16f,2f/16f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            renderMaterialLayer(entity,poseStack,module,buffer,packedLight,partialTicks,"textures/module/torso/backwings/back_wings_",2);
            SteelHeartReservoirData data = entity.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR);
            if (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().isPresent()) {
                Color color = ((INodeItem) (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().get().getItem())).getNodeColor();
                VertexConsumer consumer;
                consumer = buffer.getBuffer(AstranRenderTypes.getBloomRenderType(
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/backwings/back_wings_glow.png")));
                this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color.darker().getRGB());

            }
        }
        poseStack.popPose();
    }

    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (!manager.moduleCache().getEquippedModules().containsValue("back_base")) return false;
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new BackWingsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.BACK_WINGS));
    }

}
