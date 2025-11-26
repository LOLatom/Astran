package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffSprites;
import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.elements.CanvasElement;
import com.anonym.astran.api.swiff.elements.DragElement;
import com.anonym.astran.api.swiff.elements.DraggablePlaneElement;
import com.anonym.astran.api.swiff.elements.DraggableStackElement;
import com.anonym.astran.api.swiff.tools.SwiffTexture;
import com.anonym.astran.api.swiff.tools.UIAnchor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;

public class TestSwiff extends SwiffUI {

    @Override
    public void renderPre(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, float packedTick) {

    }

    @Override
    public void renderPost(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, float packedTick) {
    }

    @Override
    public void createElements() {


        DragElement<?> dragElement2 = new DraggablePlaneElement(this)
                .withTexture((SwiffTexture.Sliced) SwiffSprites.ENCHANTED_PLANE)
                .anchor(UIAnchor.CENTER).withScale(200,150);

        dragElement2.addChild(new DraggableStackElement(dragElement2).withItemStack(Items.STONE.getDefaultInstance())
                .anchor(UIAnchor.CENTER).withPos(-20,20).withScale(16,16).withBoundObstruction());
        dragElement2.addChild(new DraggableStackElement(dragElement2).withItemStack(Items.BIRCH_WOOD.getDefaultInstance())
                .anchor(UIAnchor.CENTER).withPos(-20,-20).withScale(16,16).withBoundObstruction());
        dragElement2.addChild(new DraggableStackElement(dragElement2).withItemStack(Items.DEEPSLATE.getDefaultInstance())
                .anchor(UIAnchor.CENTER).withPos(20,-20).withScale(16,16).withBoundObstruction());
        dragElement2.addChild(new DraggableStackElement(dragElement2).withItemStack(Items.DIAMOND.getDefaultInstance())
                .anchor(UIAnchor.CENTER).withPos(20,20).withScale(16,16).withBoundObstruction());
        dragElement2.addChild(new DraggableStackElement(dragElement2).withItemStack(Items.IRON_INGOT.getDefaultInstance())
                .anchor(UIAnchor.CENTER).withScale(16,16).withBoundObstruction());

        this.addChild(dragElement2);
        this.addChild(new DraggablePlaneElement(this)
                .withTexture((SwiffTexture.Sliced) SwiffSprites.ENCHANTED_PLANE)
                .anchor(UIAnchor.RIGHT).withScale(80,80));
        this.addChild(new DraggablePlaneElement(this)
                .withTexture((SwiffTexture.Sliced) SwiffSprites.ENCHANTED_PLANE)
                .anchor(UIAnchor.LEFT).withScale(80,80));
    }
}
