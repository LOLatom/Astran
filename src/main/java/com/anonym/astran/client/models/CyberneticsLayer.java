package com.anonym.astran.client.models;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.LayerRegistry;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;

public class CyberneticsLayer <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public CyberneticsLayer(RenderLayerParent<T, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        CyberneticsManager manager = CyberneticsManager.getManager((Player) livingEntity);


        for (CyberModule module : manager.moduleCache().getEquippedModuleInstances().values()) {
            poseStack.pushPose();

            switch (module.getAttachment()) {
                case HEAD -> this.getParentModel().head.translateAndRotate(poseStack);
                case TORSO, HIPS -> this.getParentModel().body.translateAndRotate(poseStack);
                case RIGHT_SHOULDER, RIGHT_HAND -> this.getParentModel().rightArm.translateAndRotate(poseStack);
                case LEFT_SHOULDER, LEFT_HAND -> this.getParentModel().leftArm.translateAndRotate(poseStack);
                case RIGHT_LEG -> this.getParentModel().rightLeg.translateAndRotate(poseStack);
                case LEFT_LEG -> this.getParentModel().leftLeg.translateAndRotate(poseStack);
            }
            if (module.getPrimitiveClass().model() != null) module.getPrimitiveClass().model().animate(poseStack, (AbstractClientPlayer) livingEntity,module,limbSwing,limbSwingAmount,partialTicks,ageInTicks,netHeadYaw,headPitch);
            module.getPrimitiveClass().render(
                    module,
                    (AbstractClientPlayer) livingEntity,
                    partialTicks,
                    poseStack,
                    buffer,
                    packedLight,
                    false
            );

            poseStack.popPose();

        }
    }
}
