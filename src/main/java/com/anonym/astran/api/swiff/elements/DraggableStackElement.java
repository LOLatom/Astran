package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class DraggableStackElement extends DragElement<DraggableStackElement>{

    private @Nullable ItemStack stack;

    public DraggableStackElement(AbstractElement<?> parent) {
        super(parent);
    }

    public DraggableStackElement(SwiffUI root) {
        super(root);
    }

    public DraggableStackElement withItemStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.stack != null) {
            if (!this.stack.isEmpty()) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(getScaleX()/16f,this.getScaleY()/16f,1);
                guiGraphics.renderItem(this.stack,0,0);
                guiGraphics.pose().popPose();

            }
        }
    }
}
