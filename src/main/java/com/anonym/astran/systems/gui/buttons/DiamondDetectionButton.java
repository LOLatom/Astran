package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class DiamondDetectionButton extends Button implements IGlowModifier, IHasInterfaceName {


    private static final Color BRONZINE_COLOR = new Color(201,152,93);
    private static final Color LOCKED_COLOR = new Color(213, 26, 26);
    private static final ResourceLocation LOCKED =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/interface_locked_part.png");

    public float radius;
    public ResourceLocation texture;
    public float addedScaleXY = 0f;
    public boolean isLocked = false;
    public float shakeDecrease = 0f;
    public CyberInterfaceScreen screen;
    public String name;
    public float realX;
    public float realY;

    public DiamondDetectionButton(ResourceLocation texture, float x, float y, int size, OnPress onPress, CyberInterfaceScreen screen, String name) {
        super((int) x, (int) y, size, size, Component.empty(), onPress, Button.DEFAULT_NARRATION);
        this.realX = x;
        this.realY = y;
        this.radius = size/2f;
        this.texture = texture;
        this.screen = screen;
        this.name = name;
    }

    public DiamondDetectionButton(ResourceLocation texture, float x, float y, int size, OnPress onPress, CyberInterfaceScreen screen, String name, boolean isLocked) {
        this(texture,x,y,size,onPress,screen,name);
        this.isLocked = isLocked;

    }

        @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if (this.shakeDecrease > 0) {
            this.shakeDecrease = Math.clamp((this.shakeDecrease - 0.2f),0,1);
        }

        if (this.isMouseOver(mouseX,mouseY)) {
            if (this.addedScaleXY ==0f) {
                Minecraft.getInstance().player.playSound(AstranSoundRegistry.INTERFACE_SLOT_SELECT.get(),0.7f,1f);
            }
            this.addedScaleXY = Math.clamp((this.addedScaleXY + 0.05f),0,1);
        } else if (this.addedScaleXY > 0) {
            this.addedScaleXY = Math.clamp((this.addedScaleXY - 0.05f),0,1);
        }
        if (this.isMouseOver(mouseX,mouseY)) {
            this.screen.setCurrentFocused(this);
        } else {
            if (this.screen.currentFocused() != null) {
                if (this.screen.currentFocused().equals(this)) {
                    this.screen.setCurrentFocused(null);
                }
            }
        }
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getX(),this.getY(),0);

        float scaleXY = Easing.EASE_IN_OUT_QUAD.ease(this.addedScaleXY) * 0.2f;
        float color = Easing.EASE_IN_OUT_QUAD.ease(this.addedScaleXY) * 0.4f;


        float addedX = -(36*scaleXY) / 2;
        float addedY = -(36*scaleXY) / 2;
        guiGraphics.pose().translate(addedX,addedY,0);
        guiGraphics.pose().translate(Math.sin((this.shakeDecrease * 2f) *0.5),Math.cos((this.shakeDecrease * -2f) * 0.5),0);
        guiGraphics.pose().scale(1f+scaleXY,1f+scaleXY,1f);


        float[] colored = RenderSystem.getShaderColor();

        guiGraphics.setColor((colored[0] * 0.6f) + (colored[0] * color),
                (colored[1] * 0.6f) + (colored[1] * color),
                (colored[2] * 0.6f) + (colored[2] * color),
                colored[3]);

        guiGraphics.blit(this.isLocked() ? LOCKED : this.texture,0,0,0,0,36,36,36,36);
        guiGraphics.pose().popPose();
        guiGraphics.setColor(1,1,1,1f);

    }

    @Override
    public void playDownSound(SoundManager handler) {
        if (this.isLocked()) {
            handler.play(SimpleSoundInstance.forUI(AstranSoundRegistry.INTERFACE_ERROR, 1.0F));
            this.shakeDecrease = 10f;
        } else {
            handler.play(SimpleSoundInstance.forUI(AstranSoundRegistry.INTERFACE_SLOT_OPEN, 1.0F));
        }
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isMouseOver(mouseX,mouseY)) {
            return super.mouseClicked(mouseX, mouseY, button);
        } else {
            return false;
        }
    }

    public float getRealX() {
        return this.realX;
    }

    public float getRealY() {
        return this.realY;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        double firstX = this.getRealX() + this.width /2f;
        double firstY = this.getRealY() + this.height /2f;

        double secondX = Math.abs(mouseX - firstX);
        double secondY = Math.abs(mouseY - firstY);

        return secondX + secondY <= this.radius;
    }

    @Override
    public Color getGlowColor() {
        if (this.isLocked) return LOCKED_COLOR;
        return IGlowModifier.super.getGlowColor();
    }

    @Override
    public String name() {
        if (this.isLocked) return "LOCKED";
        return this.name;
    }

    @Override
    public Color nameColor() {
        if (this.isLocked) return LOCKED_COLOR;
        return BRONZINE_COLOR;
    }

}
