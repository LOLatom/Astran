package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AssemblyRecipeButton extends InformativeButton{

    private LimbType type;
    private static final ResourceLocation HEAD =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_head.png");
    private static final ResourceLocation TORSO =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_torso.png");
    private static final ResourceLocation LEFT_SHOULDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_left_shoulder.png");
    private static final ResourceLocation RIGHT_SHOULDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/c_right_shoulder.png");



    public AssemblyRecipeButton(float x, float y, float width, float height, OnPress onPress, LimbType type) {
        super(x, y, width, height, onPress);
        this.type = type;
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        float color = Easing.EASE_IN_OUT_QUAD.ease(this.addedScaleXY) * 0.4f;
        float[] colored = RenderSystem.getShaderColor();

        guiGraphics.setColor((colored[0] * 0.6f) + (colored[0] * color),
                (colored[1] * 0.6f) + (colored[1] * color),
                (colored[2] * 0.6f) + (colored[2] * color),
                colored[3]);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX() + 7, this.getRealY() + 7,0);
        ResourceLocation texture = TORSO;
        if (this.type == LimbType.HEAD) texture = HEAD;
        guiGraphics.blit(texture,0,0,0,0,36,36,36,36);
        guiGraphics.pose().popPose();
        guiGraphics.setColor(1,1,1,1);
    }
}
