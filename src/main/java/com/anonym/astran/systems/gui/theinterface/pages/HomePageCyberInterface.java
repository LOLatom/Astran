package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.gui.buttons.DiamondDetectionButton;
import com.anonym.astran.systems.gui.theinterface.CameraCyberInterfaceScreen;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import foundry.veil.api.client.util.Easing;
import net.minecraft.resources.ResourceLocation;

public class HomePageCyberInterface extends CyberInterfaceScreen {

    private static final ResourceLocation CYBERNETICS =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/interface_cybernetics.png");
    private static final ResourceLocation SHOP =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/interface_shop.png");
    private static final ResourceLocation STATS =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/interface_stats.png");


    public HomePageCyberInterface(boolean animation) {
        super(12,5,animation);
    }


    @Override
    protected void init() {
        super.init();
        this.addGlowingRenderable(new DiamondDetectionButton(CYBERNETICS, (int) (getOffsetX() - 18), (int) getOffsetY() - 20,36, (button) -> {
            this.addTransition(new LimbCyberInterfaceScreen(),20, Easing.EASE_IN_OUT_BACK);
        },this, "CYBERNETICS"));
        this.addGlowingRenderable(new DiamondDetectionButton(SHOP, (int) (getOffsetX() - 18 -22), (int) getOffsetY() + 22 - 20,36, (button) -> {

        },this, "SHOP"));
        this.addGlowingRenderable(new DiamondDetectionButton(STATS, (int) (getOffsetX() - 18 + 22), (int) getOffsetY() + 22 - 20,36, (button) -> {

        },this, "STATS"));
    }

}
