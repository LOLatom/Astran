package com.anonym.astran.systems.gui.swiffui;

import com.anonym.astran.api.swiff.SwiffSprites;
import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.elements.AbstractElement;
import com.anonym.astran.api.swiff.elements.CanvasElement;
import com.anonym.astran.api.swiff.elements.DragElement;
import com.anonym.astran.api.swiff.tools.SwiffTexture;
import com.anonym.astran.api.swiff.tools.UIAnchor;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.joml.Vector4f;

public class TestElement extends AbstractElement<TestElement> {

    private ShaderProgram program;

    public TestElement(AbstractElement<?> parent) {
        super(parent);
    }

    public TestElement(SwiffUI root) {
        super(root);
    }


    @Override
    public void setup() {
        this.program = VeilRenderSystem.setShader(
                ResourceLocation.fromNamespaceAndPath("astran","swiffui/bleeded_ui_element"));
        this.addChild(new CanvasElement(this).withScale(180,180).anchor(UIAnchor.CENTER).withWidth(1f));
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        //SwiffSprites.DARK_BUTTON.drawSlicedUsingBleed(guiGraphics,0,0,95,35,10f,3,this.program);
        SwiffSprites.TEST.drawUsingBleed(guiGraphics,0,0,this.getScaleX(),this.getScaleY(),10f,
                this.program, SwiffTexture.BlendType.NORMAL);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();

    }


}
