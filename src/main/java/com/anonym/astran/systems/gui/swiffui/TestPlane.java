package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffSprites;
import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.elements.AbstractElement;
import com.anonym.astran.api.swiff.tools.UIAnchor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class TestPlane extends AbstractElement<TestPlane> {

    public TestPlane(AbstractElement<?> parent) {
        super(parent);
    }

    public TestPlane(SwiffUI root) {
        super(root);
    }

    @Override
    public void setup() {
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //this.setScaleX((float) (100f+ Math.sin((Minecraft.getInstance().player.tickCount+partialTick)*0.1)*45f));
        SwiffSprites.DARK_BUTTON.draw(guiGraphics,0,0,(int)this.getScaleX(),(int)this.getScaleY());
    }
}
