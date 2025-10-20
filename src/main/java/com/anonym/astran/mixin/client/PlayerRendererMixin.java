package com.anonym.astran.mixin.client;

import com.anonym.astran.Astran;
import com.anonym.astran.client.models.CyberneticsLayer;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.mojang.blaze3d.vertex.PoseStack;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {


    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void initNewerModel(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        this.addLayer(new CyberneticsLayer<>(this, context));
    }


    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
    public void onRenderAddedCyber(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {


    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;",at = @At("HEAD"))
    public void getTextureLocation(Entity par1, CallbackInfoReturnable<ResourceLocation> cir) {

    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", shift = At.Shift.AFTER))
    public void renderHandWithCybernetics(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererArmwear, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        float partialTick = mc.getTimer().getGameTimeDeltaPartialTick(true);
        CyberneticsManager manager = CyberneticsManager.getManager(mc.player);
        if (!manager.moduleCache().getEquippedModuleInstances().isEmpty()) {
            for (CyberModule module : manager.moduleCache().getEquippedModuleInstances().values()) {
                if (module.getAttachment() == LimbType.RIGHT_HAND && mc.player.getMainArm() == HumanoidArm.RIGHT
                        ) {
                    poseStack.pushPose();
                    poseStack.translate(-0.22,0.22,0.12);
                    PlayerModel<AbstractClientPlayer> playermodel = (PlayerModel)this.getModel();
                    playermodel.setAllVisible(false);
                    module.getPrimitiveClass().render(module, mc.player, partialTick ,poseStack,buffer, combinedLight, false);
                    poseStack.popPose();

                } else if (module.getAttachment() == LimbType.LEFT_HAND && mc.player.getMainArm() == HumanoidArm.LEFT
                        && mc.player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {


                }
            }

        }
    }

}
