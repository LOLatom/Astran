package com.anonym.astran.systems.cybernetics.head;

import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.head.AztecFaceModel;
import com.anonym.astran.client.models.modules.torso.FrontCoverModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;

public class AztecFaceModule extends CyberModule {


    public AztecFaceModule() {
        super("aztec_face", LimbType.HEAD);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,0,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            renderWithMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/aztecface/aztec_face_",2,true);
        } else {
            poseStack.translate(0,-5.5/16f,-2/16f);
            renderMaterialLayer(entity,poseStack,module,buffer,packedLight,partialTicks,"textures/module/torso/aztecface/aztec_face_",2);
        }
        poseStack.popPose();
    }


    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new AztecFaceModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.AZTEC_FACE));
    }

}
