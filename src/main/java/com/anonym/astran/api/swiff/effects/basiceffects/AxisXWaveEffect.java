package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;

public class AxisXWaveEffect extends SwiffEffect {

    public AxisXWaveEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/wave_x"));
    }

    public AxisXWaveEffect size(float size) {
        this.size = size;
        return this;
    }
    public AxisXWaveEffect timeMul(float timeMul) {
        this.timeMul = timeMul;
        return this;
    }
    public AxisXWaveEffect frequency(float frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public void prepare(ShaderProgram shader) {
        super.prepare(shader);
        if (shader.getUniform("GameTime") != null) {
            shader.getUniform("GameTime").setFloat(RenderSystem.getShaderGameTime());
        }
        if (shader.getUniform("Size") != null) {
            shader.getUniform("Size").setFloat(this.size);
        }
        if (shader.getUniform("Multiplier") != null) {
            shader.getUniform("Multiplier").setFloat(this.timeMul);
        }
        if (shader.getUniform("Frequency") != null) {
            shader.getUniform("Frequency").setFloat(this.frequency);
        }
    }
}
