package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.tools.UIAnchor;
import com.anonym.astran.systems.gui.swiffui.TestElement;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class DragElement<T extends DragElement<T>> extends AbstractElement<DragElement<T>>{

    private float grabX = 0,grabY = 0;
    private boolean isGrabbring = false;

    public DragElement(AbstractElement<?> parent) {
        super(parent);
    }

    public DragElement(SwiffUI root) {
        super(root);
    }

    @Override
    public void setup() {
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isGrabbring) this.isGrabbring = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public float[] getDragOffset() {
        float[] offset = {0,0};
        if (this.anchorPoint != null) {
            if (this.anchorPoint != UIAnchor.NONE) {
                switch (this.anchorPoint) {
                    case TOP_LEFT, TOP, TOP_RIGHT -> {
                        offset[1] = 0;
                    }
                    case LEFT, CENTER, RIGHT -> {
                        offset[1] = this.getScaleY()/2f;
                    }
                    case BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT -> {
                        offset[1] = this.getScaleY();
                    }
                }
                switch (this.anchorPoint) {
                    case LEFT, TOP_LEFT, BOTTOM_LEFT -> {
                        offset[0] = 0;
                    }
                    case TOP, CENTER, BOTTOM -> {
                        offset[0] = this.getScaleX()/2f;
                    }
                    case RIGHT, TOP_RIGHT, BOTTOM_RIGHT -> {
                        offset[0] = this.getScaleX();
                    }
                }
            }
        }
        return offset;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isInteractingWithElement()) {
            if (this.insideBounds(mouseX, mouseY)) {
                this.setInteraction();
                float[] offset = this.getOffsetFromAnchor();

                this.grabX = (float) (mouseX - this.getAccurateX() + offset[0]);
                this.grabY = (float) (mouseY - this.getAccurateY() + offset[1]);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.isInteractingWithElement()) {
            if (this.insideBounds(mouseX, mouseY)) {
                if (!this.isGrabbring) this.isGrabbring = true;
            }
            if (this.isGrabbring) {
                float posX = (float) (mouseX - this.getAnchoring(false)) - this.grabX;
                float posY = (float) (mouseY - this.getAnchoring(true)) - this.grabY;
                if (this.hasParent()) {
                    posX = (float) (mouseX -  this.getParent().getAccurateX()) + (this.getDragOffset()[0]) - this.grabX;
                    posY = (float) (mouseY -  this.getParent().getAccurateY()) + (this.getDragOffset()[1]) - this.grabY;

                }


                this.setPosX(posX);
                this.setPosY(posY);

            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
}
