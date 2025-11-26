package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.resources.ResourceLocation;

public class AxisYWaveEffect extends SwiffEffect {

    public AxisYWaveEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/wave_y"));
    }

    public AxisYWaveEffect size(float size) {
        this.size = size;
        return this;
    }
    public AxisYWaveEffect timeMul(float timeMul) {
        this.timeMul = timeMul;
        return this;
    }
    public AxisYWaveEffect frequency(float frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public void prepare(ShaderProgram shader) {
        super.prepare(shader);
        applyGameTime();
        applySize();
        applyTimeMul();
        applyFrequency();
    }
}
