package com.anonym.astran.mixin.client;

import com.anonym.astran.helpers.GameRendererAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements GameRendererAccessor {
    @Shadow
    protected abstract double getFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting);

    @Override
    public double accessGetFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting) {
        return getFov(activeRenderInfo,partialTicks,useFOVSetting);
    }

}
