package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.effects.basiceffects.*;
import com.anonym.astran.api.swiff.elements.AbstractElement;

public class TestSwiff extends SwiffUI {


    @Override
    public void createElements() {

        AbstractElement element = new TestElement(this)
                .withPos(width/2f,height/2f)
                .withScale(250,120);


        this.addChild(element);

        this.addChild(new TestElement(this)
                .withPos(width/2f,height/2f)
                .withScale(220,100));
        this.addChild(new TestElement(this)
                .withPos(width/2f,height/2f)
                .withScale(200,80));
        this.addChild(new TestElement(this)
                .withPos(width/2f,height/2f)
                .withScale(200,80));

    }
}
