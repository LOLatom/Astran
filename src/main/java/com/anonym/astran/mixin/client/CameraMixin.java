package com.anonym.astran.mixin.client;

import com.anonym.astran.systems.camera.CameraCynematicsHelper;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {


    @Inject(method = "getPosition", at = @At("HEAD"), cancellable = true)
    public void getCynematicPositions(CallbackInfoReturnable<Vec3> cir) {
        if (CameraCynematicsHelper.isCameraOverriden) {
            cir.setReturnValue(CameraCynematicsHelper.currentCameraPosition);
        }
    }


}
