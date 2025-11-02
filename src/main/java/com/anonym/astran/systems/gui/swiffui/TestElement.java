package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffSprites;
import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.elements.AbstractElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;

public class TestElement extends AbstractElement {

    public TestElement(SwiffUI root) {
        super(root);
    }

    @Override
    public void setup() {

    }

    public TestElement(AbstractElement parent) {
        super(parent);
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        SwiffSprites.DARK_BUTTON.draw(guiGraphics,-(this.getWidth()/2),-(this.getHeight()/2),this.getWidth(),this.getHeight());
        guiGraphics.pose().popPose();

    }


}
