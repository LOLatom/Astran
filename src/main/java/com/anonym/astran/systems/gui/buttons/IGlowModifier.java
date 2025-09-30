package com.anonym.astran.systems.gui.buttons;

import java.awt.*;

public interface IGlowModifier {
    static final Color base = new Color(255,255,255);

    default Color getGlowColor() {
        return base;
    }

}
