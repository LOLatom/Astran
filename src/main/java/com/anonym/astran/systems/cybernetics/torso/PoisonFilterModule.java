package com.anonym.astran.systems.cybernetics.torso;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.ModuleLayerRegistry;
import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.client.models.modules.torso.PoisonFilterModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class PoisonFilterModule extends CyberModule {


    public PoisonFilterModule() {
        super("poison_filter", LimbType.TORSO);
    }

    @Override
    public void render(CyberModule module, AbstractClientPlayer entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean inDisplay) {
        super.render(module, entity, partialTicks, poseStack, buffer, packedLight, inDisplay);

        if (inDisplay) {
            this.renderWithMaterialLayer(entity,poseStack,module,buffer,packedLight,"textures/module/torso/poisonfilter/poison_filter_",2,true);
        }
    }

    @Override
    protected void tick(CyberModule module, Player player) {
        super.tick(module, player);
        if (player.hasEffect(MobEffects.POISON) && module.getQuality() != Quality.LESSER) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    @Override
    protected boolean canBeEquipped(CyberModule module, CyberneticsManager manager, SocketData socket) {
        if (manager.moduleCache().getEquippedModules().containsValue(this.getModuleID())) return false;
        return super.canBeEquipped(module, manager, socket);
    }

    @Override
    public ModuleModel getModelLayer() {
        return new PoisonFilterModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModuleLayerRegistry.POISON_FILTER_MODULE));
    }
    @Override
    protected boolean canTick() {
        return true;
    }
}
