package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffTexture;
import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.elements.AbstractElement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class TestElement extends AbstractElement {

    public TestElement(SwiffUI root) {
        super(root);
    }
    public TestElement(AbstractElement parent) {
        super(parent);
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().mulPose(Axis.ZN.rotationDegrees(Minecraft.getInstance().player.tickCount+partialTick));
        guiGraphics.blit(SwiffTexture.DARK_BUTTON,-32,8,0,0,64,16,64,16);
        guiGraphics.pose().popPose();

    }


}
