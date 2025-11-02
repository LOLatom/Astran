package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;

public class ColorEffect extends SwiffEffect {

    public ColorEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/color"));
    }

    public ColorEffect color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    @Override
    public void prepare(ShaderProgram shader) {
        super.prepare(shader);
        applyRGB();
    }
}
