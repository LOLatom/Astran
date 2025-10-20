package com.anonym.astran.client.models.modules;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;

public abstract class ModuleModel {


    public abstract ModelPart getMainPart();

    public void animate(PoseStack poseStack, AbstractClientPlayer player, CyberModule module, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }


}
