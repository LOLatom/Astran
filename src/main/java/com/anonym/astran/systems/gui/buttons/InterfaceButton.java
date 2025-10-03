package com.anonym.astran.systems.gui.buttons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class InterfaceButton extends Button {

    private float realX;
    private float realY;
    private final float initialX;
    private final float initialY;

    private float realWidth;
    private float realHeight;

    public InterfaceButton(float x, float y, float width, float height, OnPress onPress) {
        super((int) x, (int) y, (int) width, (int) height, Component.empty(), onPress, DEFAULT_NARRATION);
        this.realX = x;
        this.realY = y;
        this.initialX = x;
        this.initialY = y;

        this.realWidth = width;
        this.realHeight = height;
    }



    public float getInitialX() {
        return this.initialX;
    }

    public float getInitialY() {
        return this.initialY;
    }

    public void setRealX(float realX) {
        this.realX = realX;
    }

    public void setRealY(float realY) {
        this.realY = realY;
    }

    public float getRealY() {
        return this.realY;
    }

    public float getRealX() {
        return this.realX;
    }

    public float getRealWidth() {
        return this.realWidth;
    }

    public float getRealHeight() {
        return this.realHeight;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && this.visible
                && mouseX >= (double)this.getRealX()
                && mouseY >= (double)this.getRealY()
                && mouseX < (double)(this.getRealX() + this.getRealWidth())
                && mouseY < (double)(this.getRealY() + this.getRealHeight());
    }
}
