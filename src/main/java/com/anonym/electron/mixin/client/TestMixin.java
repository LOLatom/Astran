package com.anonym.electron.mixin.client;

import com.anonym.electron.registries.client.RenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyesLayer.class)
public class TestMixin {

    @Inject(method = "renderType",at = @At("HEAD"), cancellable = true)
    public void newTypeRenderer(CallbackInfoReturnable<RenderType> cir) {
        RenderType type = RenderTypes.getBloomRenderType(ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/enderman/enderman_eyes.png"));
        if (type != null) {
            cir.setReturnValue(type);
        }
    }
}
