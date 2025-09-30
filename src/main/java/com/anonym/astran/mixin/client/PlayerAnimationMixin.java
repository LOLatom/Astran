package com.anonym.astran.mixin.client;

import com.anonym.astran.systems.gui.theinterface.CameraCyberInterfaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class PlayerAnimationMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart head;

    @Shadow @Final public ModelPart body;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart rightLeg;

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart leftLeg;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    public void setupNewerAnimations(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {
            if (player.getId() == Minecraft.getInstance().player.getId()) {
                if (Minecraft.getInstance().screen instanceof CameraCyberInterfaceScreen cyberInterface) {
                    cyberInterface.animatePlayerModel(
                            player,limbSwing,limbSwingAmount,
                            ageInTicks,netHeadYaw,headPitch,
                            this.head,this.body,this.rightArm,this.leftArm,this.rightLeg,this.leftLeg);
                    ci.cancel();
                } else {
                    this.leftArm.resetPose();
                    this.rightArm.resetPose();
                    this.rightLeg.resetPose();
                    this.leftLeg.resetPose();
                    this.body.resetPose();
                    this.head.resetPose();
                }
            }
        }
    }

}
