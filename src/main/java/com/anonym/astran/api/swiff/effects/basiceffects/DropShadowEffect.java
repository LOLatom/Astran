package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;

public class DropShadowEffect extends SwiffEffect {
    public DropShadowEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/dropshadow"));
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.radius = 10.f;
        this.offset = 25f;
        this.intensity = 0.7f;
    }

    public DropShadowEffect radius(float radius) {
        this.radius = radius;
        return this;
    }

    public DropShadowEffect offset(float offset) {
        this.offset = offset;
        return this;
    }

    public DropShadowEffect color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    public DropShadowEffect intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public DropShadowEffect orientation(float orientation) {
        this.orientation = orientation;
        return this;
    }

    @Override
    public void prepare(ShaderProgram shader) {
        super.prepare(shader);
        applyRadius();
        applyOffset();
        applyIntensity();
        applyOrientation();
        applyRGB();
    }
}
