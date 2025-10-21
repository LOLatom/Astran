package com.anonym.astran.systems.cybernetics.hands;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.torso.KineticDistributorModel;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.registries.client.AstranRenderTypes;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.energy.INodeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.awt.*;

public class KineticDistributorModule extends CyberModule {


    public KineticDistributorModule() {
        super("kinetic_distributor", LimbType.RIGHT_HAND);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);
        poseStack.pushPose();

        poseStack.scale(1f/1.5f,1f/1.5f,1f/1.5f);

        if (inDisplay) {
            poseStack.translate(0,0,0);
            poseStack.scale(1f/1.2f,1f/1.2f,1f/1.2f);
            renderWithMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/kineticdistributor/kinetic_distributor_",2,true);
        } else {
            poseStack.translate(-1f/16f,8.5f/16f,0);
            renderMaterialLayer(entity,poseStack,module,buffer,packedLight,partialTicks,"textures/module/torso/kineticdistributor/kinetic_distributor_",2);

        }
        poseStack.popPose();
    }


    @Override
    protected void onAttackEntity(CyberModule module, Player player, Entity target) {
        super.onAttackEntity(module, player, target);
        if (target instanceof LivingEntity entity) {
            CyberneticsManager manager = CyberneticsManager.getManager(player);
            for (CyberModule mod : manager.moduleCache().getEquippedTickable().values()) {
                if (mod.getModuleID().equals("kinetic_accumulator")) {
                    if (mod.getAdditionalData().isPresent()) {
                        if (mod.getAdditionalData().get().contains("storedKineticCharge")) {
                            float v = mod.getAdditionalData().get().getFloat("storedKineticCharge");
                            target.addDeltaMovement(player.getLookAngle().multiply(v/25,v/25,v/25));
                            target.hurt(target.damageSources().mobAttack(player),(v/9) * 2.2f);
                            mod.getAdditionalData().get().putFloat("storedKineticCharge",0);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasMask() {
        return true;
    }

    @Override
    public boolean firstMaskActive() {
        return true;
    }


    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new KineticDistributorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.KINETIC_DISTRIBUTOR));
    }

}
