package com.anonym.astran.api.swiff.tools;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.function.Supplier;

public abstract class SwiffTexture {

    public final int width;
    public final int height;
    public final ResourceLocation texture;

    public SwiffTexture(ResourceLocation texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public void draw(GuiGraphics guiGraphics,int x, int y, int xScale, int yScale) {
        this.draw(guiGraphics,x,y,xScale,yScale,GameRenderer::getPositionTexShader);
    }

    public static void defineBlendMode(BlendType blendType) {
        switch (blendType) {
            case ADDITIVE -> {
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ONE
                );
            }
            case INVERTED -> {
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                        GlStateManager.DestFactor.ZERO
                );
            }
            case SUBTRACTIVE -> {
                RenderSystem.blendEquation(32778);
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ONE
                );
            }
            case MULTIPLICATIVE -> {
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.DST_COLOR,
                        GlStateManager.DestFactor.ZERO
                );
            }
            case PREMUL_ALPHA -> {
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
                );
            }
            case SCREEN -> {
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR
                );
            }
            case NORMAL -> {
                RenderSystem.blendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO);

            }
        }
    }
    public void drawUsingBleed(GuiGraphics guiGraphics,float x, float y, float xScale, float yScale, float bleeding, ShaderProgram shader) {
        drawUsingBleed(guiGraphics,x,y,xScale,yScale,bleeding,shader,BlendType.NORMAL);
    }

    public void drawUsingBleed(GuiGraphics guiGraphics,float x, float y, float xScale, float yScale, float bleeding, ShaderProgram shader, BlendType blendMode) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(this.texture);
        boolean isNormal = blendMode == BlendType.NORMAL;
        float bleed = (float) ((bleeding*2) /2f);

        float x1 = x - bleed;
        float x2 = x + xScale + bleed;
        float y1 = y - bleed;
        float y2 = y + yScale + bleed;

        if (!isNormal) {
            defineBlendMode(blendMode);
        }
        RenderSystem.setShaderTexture(0, this.texture);
        if (shader != null) {
            RenderSystem.setShader(shader::toShaderInstance);
            if (shader.getUniform("BleedSize") != null) {
                float bleedX = -bleeding / (x2 - x1);
                float bleedY = -bleeding / (y2 - y1);
                shader.getUniform("BleedSize").setVector(new Vector2f(bleedX,bleedY));

            }
        } else {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }
        Matrix4f matrix4f = guiGraphics.pose().last().pose();



        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, (float)0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, x1, y2, (float)0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, x2, y2, (float)0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, x2, y1, (float)0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        if (!isNormal) {
            RenderSystem.blendEquation(32774);
            RenderSystem.defaultBlendFunc();
        }
    }

    public void drawSlicedUsingBleed(GuiGraphics guiGraphics,float x, float y, float xScale, float yScale, float bleeding, float sliceSize, ShaderProgram shader) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(this.texture);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.setShader(shader::toShaderInstance);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();

        float bleed = (float) ((bleeding*2) /2f);

        float x1 = x - bleed;
        float x2 = x + xScale + bleed;
        float y1 = y - bleed;
        float y2 = y + yScale + bleed;

        if (shader.getUniform("BleedSize") != null) {
            float bleedX = -bleeding / (x2 - x1);
            float bleedY = -bleeding / (y2 - y1);
            shader.getUniform("BleedSize").setVector(new Vector2f(bleedX,bleedY));

        }


        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, x1, y1, (float)0).setUv(0, 0);
        bufferbuilder.addVertex(matrix4f, x1, y2, (float)0).setUv(0, 1);
        bufferbuilder.addVertex(matrix4f, x2, y2, (float)0).setUv(1, 1);
        bufferbuilder.addVertex(matrix4f, x2, y1, (float)0).setUv(1, 0);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public abstract void draw(GuiGraphics guiGraphics,int x, int y, int xScale, int yScale,Supplier<ShaderInstance> shader);

    public static void blit(GuiGraphics guiGraphics,ResourceLocation atlasLocation, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight,Supplier<ShaderInstance> shader) {
        blit(guiGraphics,atlasLocation, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight,shader);
    }

    public static void blit(GuiGraphics guiGraphics,ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight,Supplier<ShaderInstance> shader) {
        blit(guiGraphics,atlasLocation, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight,shader);
    }

    public static void blit(GuiGraphics guiGraphics,ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight,Supplier<ShaderInstance> shader) {
        innerBlit(guiGraphics,atlasLocation, x1, x2, y1, y2,
                blitOffset, (uOffset + 0.0F) / (float)textureWidth,
                (uOffset + (float)uWidth) / (float)textureWidth,
                (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight,shader);
    }

    public static void innerBlit(GuiGraphics guiGraphics, ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV , Supplier<ShaderInstance> shader) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(shader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();

        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y1, (float)blitOffset).setUv(minU, minV);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y2, (float)blitOffset).setUv(minU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, (float)blitOffset).setUv(maxU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y1, (float)blitOffset).setUv(maxU, minV);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());

    }

    public enum BlendType {
        ADDITIVE,
        SUBTRACTIVE,
        MULTIPLICATIVE,
        INVERTED,
        PREMUL_ALPHA,
        SCREEN,
        NORMAL
    }

    public static class Stretched extends SwiffTexture {

        public Stretched(ResourceLocation texture, int width, int height) {
            super(texture, width, height);
        }

        @Override
        public void draw(GuiGraphics guiGraphics, int x, int y, int xScale, int yScale,Supplier<ShaderInstance> shader) {
            blit(guiGraphics,this.texture,x,y,0,0,xScale,yScale,this.width,this.height,shader);
        }
    }

    public static class Sliced extends SwiffTexture {

        private final int slice;
        public Sliced(ResourceLocation texture,int width, int height, int slice) {
            super(texture,width, height);
            this.slice = slice;
        }

        public void blit(GuiGraphics guiGraphics, ResourceLocation atlasLocation, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            float blitOffset = 0;
            float minU = (uOffset + 0.0F) / (float)textureWidth;
            float maxU = (uOffset + (float)uWidth) / (float)textureWidth;
            float minV = (vOffset + 0.0F) / (float)textureHeight;
            float maxV = (vOffset + (float)vHeight) / (float)textureHeight;
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.addVertex(matrix4f, (float)x, (float)y, (float)blitOffset).setUv(minU, minV);
            bufferbuilder.addVertex(matrix4f, (float)x, (float)y + height, (float)blitOffset).setUv(minU, maxV);
            bufferbuilder.addVertex(matrix4f, (float)x + width, (float)y + height, (float)blitOffset).setUv(maxU, maxV);
            bufferbuilder.addVertex(matrix4f, (float)x + width, (float)y, (float)blitOffset).setUv(maxU, minV);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        }

        @Override
        public void draw(GuiGraphics guiGraphics,int x, int y, int xScale, int yScale,Supplier<ShaderInstance> shader) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(-this.slice,-this.slice,0);
            //RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.texture);
            this.blit(guiGraphics,this.texture,x,y,this.slice,this.slice,0,0,
                    this.slice,this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice,y,xScale,this.slice,this.slice,0,
                    this.width-this.slice-this.slice,this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice + xScale,y,this.slice,this.slice,this.width-this.slice,0,
                    this.slice,this.slice,this.width,this.height);

            this.blit(guiGraphics,this.texture,x,y + this.slice,this.slice,yScale,0,this.slice,
                    this.slice,this.height-this.slice-this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice,y+ this.slice,xScale,yScale,this.slice,this.slice,
                    this.width-this.slice-this.slice,this.height-this.slice-this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice + xScale,y+ this.slice,this.slice,yScale,this.width-this.slice,this.slice,
                    this.slice,this.height-this.slice-this.slice,this.width,this.height);

            this.blit(guiGraphics,this.texture,x,y+ this.slice + yScale,this.slice,this.slice,0,this.height-this.slice,
                    this.slice,this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice,y+ this.slice + yScale,xScale,this.slice,this.slice,this.height-this.slice,
                    this.width-this.slice-this.slice,this.slice,this.width,this.height);
            this.blit(guiGraphics,this.texture,x + this.slice + xScale,y+ this.slice + yScale,this.slice,this.slice,this.width-this.slice,this.height-this.slice,
                    this.slice,this.slice,this.width,this.height);


            guiGraphics.pose().popPose();

        }


    }
}
