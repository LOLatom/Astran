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
        this.addGlowingRenderable(new LimbInspectionButton(LimbType.RIGHT_SHOULDER,this.getOffsetX() -18f - 23f,this.getOffsetY() -28f + 20,button -> {

            },this,"RIGHT SHOULDER",true));
        this.addGlowingRenderable(new LimbInspectionButton(LimbType.LEFT_SHOULDER,this.getOffsetX() -18f + 23f,this.getOffsetY() -28f + 20,button -> {

            },this,"LEFT SHOULDER",true));

        this.addGlowingRenderable(new LimbInspectionButton(LimbType.RIGHT_LEG,this.getOffsetX() -18f - 23f,this.getOffsetY() -28f + 20 + 40,button -> {

        },this,"RIGHT LEG",true));
        this.addGlowingRenderable(new LimbInspectionButton(LimbType.LEFT_LEG,this.getOffsetX() -18f + 23f,this.getOffsetY() -28f + 20 + 40,button -> {

        },this,"LEFT LEG",true));

    }
}
