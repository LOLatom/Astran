package com.anonym.astran.mixin.client;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import foundry.veil.api.client.render.shader.ShaderManager;
import foundry.veil.api.client.render.shader.compiler.VeilShaderSource;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
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

            boolean lUpperLeg = false;
            boolean rUpperLeg = false;
            boolean lLowerLeg = false;
            boolean rLowerLeg = false;
            boolean hips = false;

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
                        case HIPS-> {
                            if (primitive.firstMaskActive()) hips = true;
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
                        case RIGHT_LEG -> {
                            if (primitive.firstMaskActive()) rUpperLeg = true;
                            if (primitive.secondMaskActive()) rLowerLeg = true;
                        }
                        case LEFT_LEG -> {
                            if (primitive.firstMaskActive()) lUpperLeg = true;
                            if (primitive.secondMaskActive()) lLowerLeg = true;
                        }
                    }
                }
            }

            ShaderProgram shader = VeilRenderSystem.setShader(Astran.prefix("masking"));
            if (shader == null) return;

            ResourceLocation empty = Astran.prefix("textures/entity/bodymask/mask_empty.png");

            setSampler(shader,"Mask1", back ? Astran.prefix("textures/entity/bodymask/mask_back.png") : empty);
            setSampler(shader,"Mask2",torso ? Astran.prefix("textures/entity/bodymask/mask_torso.png") : empty);
            setSampler(shader,"Mask3", face ? Astran.prefix("textures/entity/bodymask/mask_face.png") : empty);
            setSampler(shader,"Mask4", skull ? Astran.prefix("textures/entity/bodymask/mask_skull.png") : empty);
            setSampler(shader,"Mask5", rshoulder ? Astran.prefix("textures/entity/bodymask/mask_right_shoulder.png") : empty);
            setSampler(shader,"Mask6", lshoulder ? Astran.prefix("textures/entity/bodymask/mask_left_shoulder.png") : empty);
            setSampler(shader,"Mask7", rhand ? Astran.prefix("textures/entity/bodymask/mask_right_hand.png") : empty);
            setSampler(shader,"Mask8", lhand ? Astran.prefix("textures/entity/bodymask/mask_left_hand.png") : empty);
            setSampler(shader,"Mask9", hips ? Astran.prefix("textures/entity/bodymask/mask_hips.png") : empty);
            setSampler(shader,"Mask10", rUpperLeg ? Astran.prefix("textures/entity/bodymask/mask_right_upper_leg.png") : empty);
            setSampler(shader,"Mask11", rLowerLeg ? Astran.prefix("textures/entity/bodymask/mask_right_lower_leg.png") : empty);
            setSampler(shader,"Mask12", lUpperLeg ? Astran.prefix("textures/entity/bodymask/mask_left_upper_leg.png") : empty);
            setSampler(shader,"Mask13", lLowerLeg ? Astran.prefix("textures/entity/bodymask/mask_left_lower_leg.png") : empty);

            cir.setReturnValue(AstranRenderTypes.getMaskingRenderType(player.getSkin().texture(),Astran.prefix("textures/entity/bodymask/mask_back.png")));
        }

    }

    public void setSampler(ShaderProgram program, String samplerName, ResourceLocation texture) {
        AbstractTexture tex = Minecraft.getInstance().getTextureManager().getTexture(texture);
        int id = tex.getId();
        program.setSampler(samplerName,id);


    }


}
