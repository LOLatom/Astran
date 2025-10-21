package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.torso.BackBaseModel;
import com.anonym.astran.client.models.modules.torso.FrontBaseModel;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.energy.INodeItem;
import com.anonym.astran.systems.gui.theinterface.pages.LimbInterface;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class FrontBaseModule extends CyberModule {


    public FrontBaseModule() {
        super("front_base", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,0,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            VertexConsumer cons;
            cons = buffer.getBuffer(RenderType.entityCutoutNoCull(
                    ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/frontbase/front_base.png")));
            if (Minecraft.getInstance().screen instanceof LimbInterface) {
                this.model().getMainPart().render(poseStack, cons, packedLight, OverlayTexture.NO_OVERLAY, ADJUSTMENT_COLOR.getRGB());

            } else {
                this.model().getMainPart().render(poseStack, cons, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());

            }
        } else {
            poseStack.translate(0,6f/16f,-2f/16f);
            VertexConsumer cons;
            cons = buffer.getBuffer(RenderType.entityCutoutNoCull(
                    ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/frontbase/front_base.png")));
            this.model().getMainPart().render(poseStack, cons, packedLight, OverlayTexture.NO_OVERLAY, Color.WHITE.getRGB());
            SteelHeartReservoirData data = entity.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR);
            if (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().isPresent()) {
                Color color = ((INodeItem) (data.getSteelHeart().get().get(AstranDataComponentRegistry.STEEL_HEART_DATA).firstNode().get().getItem())).getNodeColor();
                VertexConsumer consumer;
                consumer = buffer.getBuffer(RenderType.eyes(
                        ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/module/torso/frontbase/front_base_heart.png")));
                this.model().getMainPart().render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color.getRGB());

            }
        }
        poseStack.popPose();
    }

    @Override
    public boolean hasMask() {
        return true;
    }

    @Override
    public boolean firstMaskActive() {
        return true;
    }

    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new FrontBaseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.FRONT_BASE));
    }

}
