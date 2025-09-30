package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.buttons.cybernetics.LimbInspectionButton;
import com.anonym.astran.systems.gui.theinterface.CameraCyberInterfaceScreen;
import foundry.veil.api.client.util.Easing;

public class LimbCyberInterfaceScreen extends CameraCyberInterfaceScreen {

    public LimbCyberInterfaceScreen() {
        super(8, 9, false);
    }

    @Override
    protected void init() {
        super.init();
        this.addGlowingRenderable(new LimbInspectionButton(LimbType.HEAD,this.getOffsetX() - 18f,this.getOffsetY() -28f,button -> {
            this.addTransition(new HeadCyberInterface(),20, Easing.EASE_IN_BACK);
        },this,"HEAD"));
        this.addGlowingRenderable(new LimbInspectionButton(LimbType.TORSO,this.getOffsetX() - 18f,this.getOffsetY() -28f + 40,button -> {
            this.addTransition(new TorsoCyberInterface(),20, Easing.EASE_IN_BACK);
        },this,"TORSO"));

    }
}
