package com.anonym.astran.mixin.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftWindowMixin {

    @Shadow
    @Final
    private Window window;

    @Inject(method = "updateTitle", at = @At("HEAD"), cancellable = true)
    public void astran$newTitle(CallbackInfo ci) {
        this.window.setTitle("Minecraft - Astran");
        ci.cancel();
    }

}
