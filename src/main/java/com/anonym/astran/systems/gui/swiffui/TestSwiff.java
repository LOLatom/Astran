package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.effects.basiceffects.BloomEffect;
import com.anonym.astran.api.swiff.effects.basiceffects.BlurEffect;
import com.anonym.astran.api.swiff.effects.basiceffects.ChromaticAberrationEffect;
import com.anonym.astran.api.swiff.effects.basiceffects.FinalBlitEffect;

public class TestSwiff extends SwiffUI {


    @Override
    public void createElements() {

        this.addChild(new TestElement(this)
                .withPos(width/2f - 50f,height/2f)
                .withScale(10,10)
                .withEffectPipeline(this.getEffectPipeline())
                .withEffects(
                        new ChromaticAberrationEffect(),
                        new BlurEffect()
                ));



    }
}
