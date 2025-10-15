package com.anonym.astran.helpers;

import net.minecraft.client.Camera;

public interface GameRendererAccessor {

    double accessGetFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting);

}

