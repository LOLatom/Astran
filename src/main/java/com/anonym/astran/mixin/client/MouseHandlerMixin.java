package com.anonym.astran.mixin.client;

import com.anonym.astran.systems.gui.MouseHandlerHelper;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "releaseMouse" , at = @At("HEAD"), cancellable = true)
    public void modifiedRelease(CallbackInfo ci) {
        if (MouseHandlerHelper.isMouseReleaseDisabled) {
            ci.cancel();
        }
    }

    @Inject(method = "grabMouse" , at = @At("HEAD"), cancellable = true)
    public void modifiedGrab(CallbackInfo ci) {
        if (MouseHandlerHelper.isMouseReleaseDisabled) {
            ci.cancel();
        }
    }


}
