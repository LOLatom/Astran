package com.anonym.astran.mixin.client;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Inject(method = "getRenderType",at = @At("HEAD"), cancellable = true)
    public void getMaskedRender(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
        if (livingEntity instanceof AbstractClientPlayer player) {
            CyberneticsManager manager = CyberneticsManager.getManager(player);
            boolean face = false;
            boolean skull = false;
            boolean torso = false;
            boolean legs = false;
            boolean back = false;
            boolean rshoulder = false;
            boolean lshoulder = false;
            boolean rhand = false;
            boolean lhand = false;

            for (CyberModule module : manager.moduleCache().getEquippedModuleInstances().values()) {
                CyberModule primitive = module.getPrimitiveClass();
                if (primitive.hasMask()) {
                    switch (module.getAttachment()) {
                        case HEAD -> {
                            if (primitive.firstMaskActive()) face = true;
                            if (primitive.secondMaskActive()) skull = true;
                        }
                        case HIPS, RIGHT_LEG, LEFT_LEG -> {
                            if (primitive.firstMaskActive()) legs = true;
                        }
                        case RIGHT_HAND -> {
                            if (primitive.firstMaskActive()) rhand = true;
                        }
                        case LEFT_HAND -> {
                            if (primitive.firstMaskActive()) lhand = true;
                        }
                        case TORSO -> {
                            if (primitive.firstMaskActive()) torso = true;
                            if (primitive.secondMaskActive()) back = true;
                        }
                        case RIGHT_SHOULDER -> {
                            if (primitive.firstMaskActive()) rshoulder = true;
                        }
                        case LEFT_SHOULDER -> {
                            if (primitive.firstMaskActive()) lshoulder = true;
                        }
                    }
                }
            }

            ShaderProgram shader = VeilRenderSystem.setShader(Astran.prefix("masking"));
            if (shader == null) return;

            ResourceLocation empty = Astran.prefix("textures/entity/bodymask/mask_empty.png");

            RenderSystem.setShaderTexture(3, back ? Astran.prefix("textures/entity/bodymask/mask_back.png") : empty);
            shader.setSampler("Mask1", 3);
            RenderSystem.setShaderTexture(4,torso ? Astran.prefix("textures/entity/bodymask/mask_torso.png") : empty);
            shader.setSampler("Mask2", 4);
            RenderSystem.setShaderTexture(5, face ? Astran.prefix("textures/entity/bodymask/mask_face.png") : empty);
            shader.setSampler("Mask3", 5);
            RenderSystem.setShaderTexture(6, skull ? Astran.prefix("textures/entity/bodymask/mask_skull.png") : empty);
            shader.setSampler("Mask4", 6);
            RenderSystem.setShaderTexture(7, rshoulder ? Astran.prefix("textures/entity/bodymask/mask_right_shoulder.png") : empty);
            shader.setSampler("Mask5", 7);
            RenderSystem.setShaderTexture(8, lshoulder ? Astran.prefix("textures/entity/bodymask/mask_left_shoulder.png") : empty);
            shader.setSampler("Mask6", 8);
            RenderSystem.setShaderTexture(9, rhand ? Astran.prefix("textures/entity/bodymask/mask_right_hand.png") : empty);
            shader.setSampler("Mask7", 9);
            RenderSystem.setShaderTexture(10, lhand ? Astran.prefix("textures/entity/bodymask/mask_left_hand.png") : empty);
            shader.setSampler("Mask8", 10);
            RenderSystem.setShaderTexture(11, legs ? Astran.prefix("textures/entity/bodymask/mask_legs.png") : empty);
            shader.setSampler("Mask9", 11);


            cir.setReturnValue(AstranRenderTypes.getMaskingRenderType(player.getSkin().texture(),Astran.prefix("textures/entity/bodymask/mask_back.png")));
        }

    }

}
