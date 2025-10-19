package com.anonym.astran.client.models;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.LayerRegistry;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
            float x = 0;
            float y = 0;
            float z = 0;
            float xRot = 0;
            float yRot = 0;
            float zRot = 0;

            switch (module.getAttachment()) {
                case HEAD -> {
                    x = this.getParentModel().head.getInitialPose().x;
                    y = this.getParentModel().head.getInitialPose().y;
                    z = this.getParentModel().head.getInitialPose().z;
                    xRot = this.getParentModel().head.xRot;
                    yRot = this.getParentModel().head.yRot;
                    zRot = this.getParentModel().head.zRot;

                }
                case TORSO, HIPS -> {
                    x = this.getParentModel().body.getInitialPose().x;
                    y = this.getParentModel().body.getInitialPose().y;
                    z = this.getParentModel().body.getInitialPose().z;
                    xRot = this.getParentModel().body.xRot;
                    yRot = this.getParentModel().body.yRot;
                    zRot = this.getParentModel().body.zRot;
                }
                case RIGHT_SHOULDER, RIGHT_HAND -> {
                    x = this.getParentModel().rightArm.getInitialPose().x;
                    y = this.getParentModel().rightArm.getInitialPose().y;
                    z = this.getParentModel().rightArm.getInitialPose().z;
                    xRot = this.getParentModel().rightArm.xRot;
                    yRot = this.getParentModel().rightArm.yRot;
                    zRot = this.getParentModel().rightArm.zRot;
                }
                case LEFT_SHOULDER, LEFT_HAND -> {
                    x = this.getParentModel().leftArm.getInitialPose().x;
                    y = this.getParentModel().leftArm.getInitialPose().y;
                    z = this.getParentModel().leftArm.getInitialPose().z;
                    xRot = this.getParentModel().leftArm.xRot;
                    yRot = this.getParentModel().leftArm.yRot;
                    zRot = this.getParentModel().leftArm.zRot;
                }
                case RIGHT_LEG -> {
                    x = this.getParentModel().rightLeg.getInitialPose().x;
                    y = this.getParentModel().rightLeg.getInitialPose().y;
                    z = this.getParentModel().rightLeg.getInitialPose().z;
                    xRot = this.getParentModel().rightLeg.xRot;
                    yRot = this.getParentModel().rightLeg.yRot;
                    zRot = this.getParentModel().rightLeg.zRot;
                }
                case LEFT_LEG -> {
                    x = this.getParentModel().leftLeg.getInitialPose().x;
                    y = this.getParentModel().leftLeg.getInitialPose().y;
                    z = this.getParentModel().leftLeg.getInitialPose().z;
                    xRot = this.getParentModel().leftLeg.xRot;
                    yRot = this.getParentModel().leftLeg.yRot;
                    zRot = this.getParentModel().leftLeg.zRot;
                }}


            poseStack.pushPose();
            poseStack.translate(x,y,z);
            poseStack.mulPose((new Quaternionf()).rotationZYX(zRot, yRot, xRot));

            module.getPrimitiveClass().render(
                    module, (AbstractClientPlayer) livingEntity, partialTicks
                    ,poseStack,
                    buffer,packedLight,
                    false);
            poseStack.popPose();


        }
    }
}
