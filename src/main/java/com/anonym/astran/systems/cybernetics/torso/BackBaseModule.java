package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.torso.AquaLungsModel;
import com.anonym.astran.client.models.modules.torso.BackBaseModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.gui.theinterface.pages.LimbInterface;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;

public class BackBaseModule extends CyberModule {


    public BackBaseModule() {
        super("back_base", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,-0.45,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            renderSingleMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/backbase/back_base_");
        } else {
            poseStack.translate(0,0,1.5f/16f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            renderOnPlayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/backbase/back_base_",1,false);
        }
        poseStack.popPose();
    }

    @Override
    public boolean hasMask() {
        return true;
    }

    @Override
    public boolean secondMaskActive() {
        return true;
    }

    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new BackBaseModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.BACK_BASE));
    }

}
