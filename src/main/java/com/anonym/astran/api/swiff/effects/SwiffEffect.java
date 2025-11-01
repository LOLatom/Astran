package com.anonym.astran.api.swiff.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class SwiffEffect implements SwiffShaderEffect {

    private final ShaderProgram shader;

    public SwiffEffect(ResourceLocation shaderLocation) {
        this.shader = VeilRenderSystem.setShader(shaderLocation);

    }

    @Override
    public void apply(AdvancedFbo input, @Nullable AdvancedFbo output, GuiGraphics guiGraphics, float partialTick) {
        output.bind(true);
        output.clear();

        int x1 = 0;
        int y1 = 0;
        int x2 = guiGraphics.guiWidth();
        int y2 = guiGraphics.guiHeight();
        RenderSystem.setShaderTexture(0, input.getColorTextureAttachment(0).getId());
        this.shader.setSampler("EffectSampler0",input.getColorTextureAttachment(0).getId());
        this.shader.setSampler("DiffuseSampler0",AdvancedFbo.getMainFramebuffer().getColorTextureAttachment(0).getId());
        prepare(this.shader);
        RenderSystem.setShader(this.shader::toShaderInstance);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) 0).setUv(1, 0);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) 0).setUv(1, 1);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());

    }
}
