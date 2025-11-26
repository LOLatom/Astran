package com.anonym.astran.api.swiff.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class SwiffEffect implements SwiffShaderEffect {

    public final ShaderProgram shader;
    public float r = 1f, g = 1f, b = 1f;
    public float radius = 1.0f;
    public float size = 1.0f;
    public float timeMul = 1.0f;
    public float intensity = 1.0f;
    public float orientation = 0.0f;
    public float frequency = 50.0f;
    public float offset = 1.0f;
    public float posX = 1f, posY = 1f, posZ = 1f;

    public SwiffEffect(ResourceLocation shaderLocation) {
        this.shader = VeilRenderSystem.setShader(shaderLocation);
    }

    public void setRGB(float r,float g,float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setTimeMul(float timeMul) {
        this.timeMul = timeMul;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }

    public void applyGameTime() {
        if (this.shader.getUniform("GameTime") != null) {
            this.shader.getUniform("GameTime").setFloat(RenderSystem.getShaderGameTime());
        }
    }

    public void applyRGB() {
        if (this.shader.getUniform("Color") != null) {
            this.shader.getUniform("Color").setVector(this.r, this.g, this.b, 0f);
        }
    }

    public void applyPos() {
        if (this.shader.getUniform("Pos") != null) {
            this.shader.getUniform("Pos").setVector(this.posX, this.posY, this.posZ);
        }
    }

    public void applyIntensity() {
        if (this.shader.getUniform("Intensity") != null) {
            this.shader.getUniform("Intensity").setFloat(this.intensity);
        }
    }

    public void applySize() {
        if (this.shader.getUniform("Size") != null) {
            this.shader.getUniform("Size").setFloat(this.size);
        }
    }

    public void applyTimeMul() {
        if (this.shader.getUniform("TimeMul") != null) {
            this.shader.getUniform("TimeMul").setFloat(this.timeMul);
        }
    }

    public void applyRadius() {
        if (this.shader.getUniform("Radius") != null) {
            this.shader.getUniform("Radius").setFloat(this.radius);
        }
    }
    public void applyOrientation() {
        if (this.shader.getUniform("Orientation") != null) {
            this.shader.getUniform("Orientation").setFloat(this.orientation);
        }
    }
    public void applyFrequency() {
        if (this.shader.getUniform("Frequency") != null) {
            this.shader.getUniform("Frequency").setFloat(this.frequency);
        }
    }

    public void applyOffset() {
        if (this.shader.getUniform("Offset") != null) {
            this.shader.getUniform("Offset").setFloat(this.offset);
        }
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
