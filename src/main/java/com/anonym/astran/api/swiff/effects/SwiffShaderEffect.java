package com.anonym.astran.api.swiff.effects;

import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nullable;

public interface SwiffShaderEffect {

    default void prepare(ShaderProgram shader) {}

    void apply(AdvancedFbo input, @Nullable AdvancedFbo output, GuiGraphics guiGraphics, float partialTick);

    default float resolutionScale() { return 1.0f; }
}
