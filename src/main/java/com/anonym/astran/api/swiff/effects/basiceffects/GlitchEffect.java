package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;

public class GlitchEffect extends SwiffEffect {


    public GlitchEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/glitch"));
        this.intensity = 1.0f;
        this.size = 1.0f;
        this.timeMul = 1.0f;
    }

    public SwiffEffect intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }
    public SwiffEffect size(float size) {
        this.size = size;
        return this;
    }
    public SwiffEffect timeMul(float timeMul) {
        this.timeMul = timeMul;
        return this;
    }

    @Override
    public void prepare(ShaderProgram shader) {
        super.prepare(shader);
        applyGameTime();
        applyIntensity();
        applySize();
        applyTimeMul();
    }
}
