package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.tools.SwiffTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;

import javax.annotation.Nullable;

public class DraggablePlaneElement extends DragElement<DraggablePlaneElement>{


    private @Nullable SwiffTexture.Sliced swiffTexture;

    public DraggablePlaneElement(AbstractElement<?> parent) {
        super(parent);
    }

    public DraggablePlaneElement(SwiffUI root) {
        super(root);
    }

    public DraggablePlaneElement withTexture(SwiffTexture.Sliced texture) {
        this.swiffTexture = texture;
        return this;
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //RenderSystem.enableBlend();
        float[][] offsets = {
                { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 },
                { -1, -1 }, { 1, -1 }, { 1, 1 }, { -1, 1 },
                { 0, -2 }, { 2, 0 }, { 0, 2 }, { -2, 0 }
        };
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        if (this.swiffTexture != null) this.swiffTexture.draw(guiGraphics,0,0,(int)this.getScaleX(),(int)this.getScaleY());


        float x1 = this.getAccurateX();
        float x2 = x1 + this.getScaleX();
        float y1 = this.getAccurateY();
        float y2 = y1 + this.getScaleY();

        SwiffTexture.defineBlendMode(SwiffTexture.BlendType.ADDITIVE);

        for (float[] o : offsets) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(o[0] * 0.5,o[1]*0.5,0);
            guiGraphics.setColor(0.01f,0.01f,0.01f,0.05f);
            if (this.swiffTexture != null) this.swiffTexture.draw(guiGraphics,0,0,(int)this.getScaleX(),(int)this.getScaleY());
            guiGraphics.setColor(1f,1f,1f,1f);
            guiGraphics.pose().popPose();

        }

        for (float[] o : offsets) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(o[0]*0.5,o[1]*0.5,0);
            guiGraphics.setColor(0.05f,0.05f,0.05f,0.15f);
            if (this.swiffTexture != null) this.swiffTexture.draw(guiGraphics,0,0,(int)this.getScaleX(),(int)this.getScaleY());
            guiGraphics.setColor(1f,1f,1f,1f);
            guiGraphics.pose().popPose();

        }
        if (this.swiffTexture != null) this.swiffTexture.draw(guiGraphics,0,0,(int)this.getScaleX(),(int)this.getScaleY());
        RenderSystem.blendEquation(32774);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
}
