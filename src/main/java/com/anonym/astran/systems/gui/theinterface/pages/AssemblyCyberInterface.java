package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.buttons.AssemblyRecipeButton;
import com.anonym.astran.systems.gui.buttons.InformativeButton;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;

public class AssemblyCyberInterface extends CyberInterfaceScreen {
    public AssemblyCyberInterface() {
        super(12,9,false);
    }

    @Override
    protected void init() {
        super.init();
        this.addGlowingRenderable(new AssemblyRecipeButton(this.getOffsetX() - (9f*16) + 4f,this.getOffsetY() - ((48+48+(16*this.getInterfaceHeight()))/2)+35,9f,1f, button -> {

        }, LimbType.TORSO));

    }


}
