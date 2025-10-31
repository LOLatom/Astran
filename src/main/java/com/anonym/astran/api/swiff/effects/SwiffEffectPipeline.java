package com.anonym.astran.api.swiff.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

/**
 * This is an Effect Pipeline for Shaders inside GUIs
 **/
public class SwiffEffectPipeline {
    public final AdvancedFbo ping;
    public final AdvancedFbo pong;
    private boolean markForSwap = false;
    private final Minecraft mc = Minecraft.getInstance();

    public SwiffEffectPipeline(ResourceLocation pingId, ResourceLocation pongId) {
        FramebufferManager manager = VeilRenderSystem.renderer().getFramebufferManager();
        this.ping = manager.getFramebuffer(pingId);
        this.pong = manager.getFramebuffer(pongId);
    }

    public void begin() {
        this.markForSwap = false;
        this.ping.bind(true);
        this.ping.clear();
    }

    public void apply(SwiffShaderEffect effect, GuiGraphics guiGraphics, float partialTicks) {
        AdvancedFbo input = this.markForSwap ? this.pong : this.ping;
        AdvancedFbo output = this.markForSwap ? this.ping : this.pong;

        effect.apply(input, output, guiGraphics, partialTicks);

        this.markForSwap = !this.markForSwap;
    }

    /**
     * This method is Used to render the Effects Pipeline
     * @param guiGraphics Pass the Graphics from your GUI
     */
    public void renderToScreen(GuiGraphics guiGraphics) {
        AdvancedFbo result = !this.markForSwap ? this.ping : this.pong;
        renderFboToScreen(result, guiGraphics);
    }

    private void renderFboToScreen(AdvancedFbo fbo , GuiGraphics guiGraphics) {

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        int x1 = 0;
        int y1 = 0;
        int x2 = guiGraphics.guiWidth();
        int y2 = guiGraphics.guiHeight();
        RenderSystem.setShaderTexture(0, fbo.getColorTextureAttachment(0).getId());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) 0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) 0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) 0).setUv(1, 0);
        bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) 0).setUv(1, 1);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());

    }

    public void clear() {
        this.ping.clear();
        this.pong.clear();
    }
}
