package com.anonym.astran.mixin.common;

import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public class PlayerCyberManagerImp implements IContainCyberneticsManager {

    private CyberneticsManager manager;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void initiationAdditive(Level level, BlockPos pos, float yRot, GameProfile gameProfile, CallbackInfo ci) {
        this.manager = new CyberneticsManager(((Player)(Object)this));
    }

    @Override
    public CyberneticsManager manager() {
        return this.manager;
    }
}
