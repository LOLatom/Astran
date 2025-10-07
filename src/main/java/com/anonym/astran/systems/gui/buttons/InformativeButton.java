package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InformativeButton extends InterfaceButton implements IGlowModifier{


    public static final ResourceLocation INFORMATIONAL_BUTTON =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/interface/informative_button.png");

    public static final ResourceLocation INFORMATIONAL_BUTTON_BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/interface/informative_button_background.png");

    protected float addedScaleXY = 0f;

    public InformativeButton(float x, float y, float width, float height, OnPress onPress) {
        super(x, y, width, height, onPress);
    }

    public boolean hasSound() {
        return true;
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {


        float x = (float) this.getRealX();
        float y = (float) this.getRealY();

        if (this.isMouseOver(mouseX,mouseY)) {
            if (this.addedScaleXY ==0f) {
                if (hasSound()) {
                    Minecraft.getInstance().player.playSound(AstranSoundRegistry.INTERFACE_SLOT_SELECT.get(), 0.7f, 1f);
                }
            }
            this.addedScaleXY = Math.clamp((this.addedScaleXY + 0.05f),0,1);
        } else if (this.addedScaleXY > 0) {
            this.addedScaleXY = Math.clamp((this.addedScaleXY - 0.05f),0,1);
        }

        float color = Easing.EASE_IN_OUT_QUAD.ease(this.addedScaleXY) * 0.4f;
        float[] colored = RenderSystem.getShaderColor();

        guiGraphics.setColor((colored[0] * 0.6f) + (colored[0] * color),
                (colored[1] * 0.6f) + (colored[1] * color),
                (colored[2] * 0.6f) + (colored[2] * color),
                colored[3]);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y,0);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,0,0,17,17,50,50);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17,0,0);
        guiGraphics.pose().scale(getRealWidth(),1,1);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17,0,16,17,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17 + (16 * getRealWidth()),0,0);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17+16,0,17,17,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,17,0);
        guiGraphics.pose().scale(1,getRealHeight(),1);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,0,17,17,16,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17,17,0);
        guiGraphics.pose().scale(getRealWidth(),getRealHeight(),1);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17,17,16,16,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17 + (16 * getRealWidth()),17,0);
        guiGraphics.pose().scale(1,getRealHeight(),1);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17+16,17,17,16,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,17+(16*getRealHeight()),0);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,0,17+16,17,17,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17,17+(16*getRealHeight()),0);
        guiGraphics.pose().scale(getRealWidth(),1,1);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17,17+16,16,17,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(17 + (16 * getRealWidth()),17+(16*getRealHeight()),0);
        guiGraphics.blit(INFORMATIONAL_BUTTON,0,0,17+16,17+16,17,17,50,50);
        guiGraphics.pose().popPose();
        guiGraphics.pose().popPose();
        guiGraphics.setColor(1,1,1,1f);

        double scale = Minecraft.getInstance().getWindow().getGuiScale();
        int scWidth = (int) ((17+17+(16*getRealWidth())-14));
        int scHeight = (int) ((17+17+(16*getRealHeight())-14));

        int scStartX = (int) ((x+7)*scale);
        int scStartY = (int) (Minecraft.getInstance().getWindow().getHeight() - (((y+7) + scHeight/scale) * scale));


        float addedTicks = (Minecraft.getInstance().player.tickCount + partialTick);
        guiGraphics.setColor(colored[0],
                colored[1],
                colored[2],
                colored[3] * 0.06f);
        guiGraphics.blit(INFORMATIONAL_BUTTON_BACKGROUND, (int) x + 7, (int) y + 7,(addedTicks * 0.4f),addedTicks * 0.3f,scWidth,scHeight,28,28);
        guiGraphics.blit(INFORMATIONAL_BUTTON_BACKGROUND, (int) x + 7, (int) y + 7,(addedTicks * -0.4f),addedTicks * 0.3f,scWidth,scHeight,28,28);

        guiGraphics.setColor(1,1,1,1f);
    }

    public void renderCut(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {




    }


    public float getOffsetX() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth()/2f;
    }

    public float getOffsetY() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight()/2f;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean flag = this.isMouseOver(mouseX,mouseY);
                if (flag) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY, button);
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && this.visible
                && mouseX >= (double)this.getRealX()
                && mouseY >= (double)this.getRealY()
                && mouseX < (double)(this.getRealX() + (this.getRealWidth() * 16) + 17 + 17)
                && mouseY < (double)(this.getRealY() + (this.getRealHeight() * 16)+17+17);
    }

}
