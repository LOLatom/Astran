package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.torso.BackBaseModel;
import com.anonym.astran.client.models.modules.torso.BackCoverModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;

public class BackCoverModule extends CyberModule {


    public BackCoverModule() {
        super("back_cover", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,0,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            renderWithMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/backcover/back_cover_",2,true);
        } else {
            poseStack.translate(0,8.5f/16f,2f/16f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            renderMaterialLayer(entity,poseStack,module,buffer,packedLight,partialTicks,"textures/module/torso/backcover/back_cover_",2);
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
        return new BackCoverModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.BACK_COVER));
    }

}
