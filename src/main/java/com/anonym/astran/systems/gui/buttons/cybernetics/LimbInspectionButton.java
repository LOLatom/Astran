package com.anonym.astran.systems.gui.buttons.cybernetics;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.buttons.DiamondDetectionButton;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import net.minecraft.resources.ResourceLocation;

public class LimbInspectionButton extends DiamondDetectionButton {

    public static final ResourceLocation HEAD =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_head.png");
    public static final ResourceLocation TORSO =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_torso.png");
    public static final ResourceLocation LEFT_SHOULDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_left_shoulder.png");
    public static final ResourceLocation RIGHT_SHOULDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_right_shoulder.png");



    public LimbInspectionButton(LimbType type, float x, float y, OnPress onPress, CyberInterfaceScreen screen, String name) {
        super(type == LimbType.HEAD ? HEAD :
                type == LimbType.TORSO ? TORSO :
                        type == LimbType.LEFT_SHOULDER ? LEFT_SHOULDER :
                                type == LimbType.RIGHT_SHOULDER ? RIGHT_SHOULDER : RIGHT_SHOULDER, x, y, 36, onPress, screen, name);
    }

    public LimbInspectionButton(LimbType type, float x, float y, OnPress onPress, CyberInterfaceScreen screen, String name, boolean isLocked) {
        super(type == LimbType.HEAD ? HEAD :
                type == LimbType.TORSO ? TORSO :
                        type == LimbType.LEFT_SHOULDER ? LEFT_SHOULDER :
                                type == LimbType.RIGHT_SHOULDER ? RIGHT_SHOULDER : RIGHT_SHOULDER, x, y, 36, onPress, screen, name, isLocked);
    }


}
