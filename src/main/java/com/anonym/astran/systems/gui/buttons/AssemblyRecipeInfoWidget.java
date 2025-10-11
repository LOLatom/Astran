package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.buttons.cybernetics.LimbInspectionButton;
import com.anonym.astran.systems.gui.theinterface.pages.AssemblyCyberInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.Optional;

public class AssemblyRecipeInfoWidget extends InformativeButton {

    private static final ResourceLocation MODEL_BACKGROUND_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/model_display_slot.png");


    private AssemblyCyberInterface screen;
    public AssemblyRecipeInfoWidget(float x, float y, AssemblyCyberInterface screen) {
        super(x, y, 9.5f, 10, (button -> {}));
        this.screen = screen;
    }

    @Override
    public float getRealX() {
        float easedProportion = Easing.EASE_IN_OUT_BACK.ease(screen.assemblyShown);
        return super.getRealX() - ((6f*16)*easedProportion) - 2f;
    }

    @Override
    public float getRealWidth() {
        float easedProportion = 1f - Easing.EASE_IN_OUT_BACK.ease(screen.assemblyShown);
        return Math.clamp(super.getRealWidth() - (easedProportion * 16),0.5f,20f);
    }

    @Override
    public boolean hasSound() {
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        if (this.screen.selectedRecipe != null) {
            int lengthOffset = 10;
            int first = new Color(33,25,24,100).getRGB();
            int second = new Color(230, 192, 106,255).getRGB();

            LimbType type = this.screen.selectedRecipe.getResultModule().get().getAttachment();
            Component name = this.screen.selectedRecipe.getRecipeName();
            Color color = AstranMaterialTypeRegistry.BRONZINE.get().getColorPaletteModifier().getColorPalette().get(2);
            int nameSize = Minecraft.getInstance().font.width(name);
            ResourceLocation tex = type == LimbType.HEAD ? LimbInspectionButton.HEAD :
                    type == LimbType.TORSO ?
                            LimbInspectionButton.TORSO :
                            type == LimbType.LEFT_SHOULDER ?
                                    LimbInspectionButton.LEFT_SHOULDER :
                                    type == LimbType.RIGHT_SHOULDER ?
                                            LimbInspectionButton.RIGHT_SHOULDER :
                                            LimbInspectionButton.RIGHT_SHOULDER;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.getRealX() + (((9.5f*16f)+17f+17f)/2f) - 16f, this.getRealY() + 10, 0);
            guiGraphics.drawString(Minecraft.getInstance().font,name,- nameSize/2 + 21,46,color.getRGB());
            guiGraphics.blit(tex,0,0,0,0,36,36,36,36);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(-0.5,0,0);
            guiGraphics.fillGradient(
                    (int) (((this.getRealWidth()*16)+17+17)/2) -75,
                    58,
                    (int) (((this.getRealWidth()*16)+17+17)/2)-74,
                    110,first,second);
            guiGraphics.fillGradient(
                    (int) (((this.getRealWidth()*16)+17+17)/2) -75,
                    110,
                    (int) (((this.getRealWidth()*16)+17+17)/2)-74,
                    176,second,first);
            guiGraphics.pose().popPose();

            guiGraphics.pose().popPose();


            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.getRealX(), this.getRealY() + 53, 0);
            guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-90));

            guiGraphics.fillGradient(-1,lengthOffset,0, (int) (((this.getRealWidth()*16)+17+17)/2),
                    first,
                    second);
            guiGraphics.fillGradient(-1,(int) (((this.getRealWidth()*16)+17+17)/2),0, (int) (((this.getRealWidth()*16)+17+17) -lengthOffset),
                    second,
                    first);
            guiGraphics.pose().translate(-14, 0, 0);
            guiGraphics.fillGradient(-1,lengthOffset,0, (int) (((this.getRealWidth()*16)+17+17)/2),
                    first,
                    second);
            guiGraphics.fillGradient(-1,(int) (((this.getRealWidth()*16)+17+17)/2),0, (int) (((this.getRealWidth()*16)+17+17) -lengthOffset),
                    second,
                    first);
            guiGraphics.pose().popPose();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.getRealX() + 51,this.getRealY()+114,0);
            guiGraphics.blit(MODEL_BACKGROUND_TEXTURE,-48,-48,0,0,96,96,96,96);
            guiGraphics.pose().popPose();


            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.getRealX() + 50,this.getRealY() +114,200);
            guiGraphics.pose().scale(-90,-90,-1);
            guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(180));
            guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-15));
            guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(15));
            guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount+partialTick));
            guiGraphics.setColor(40f,40f,40f,1);

            Optional<CyberModule> module = this.screen.moduleCache;
            if (module.isPresent()) {
                module.get()
                        .render(module.get(), Minecraft.getInstance().player,
                                partialTick, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880, true);
            }
            guiGraphics.setColor(1,1,1,1);
            guiGraphics.pose().popPose();

        }

    }

    @Override
    public float getRealY() {
        return super.getRealY();
    }
}
