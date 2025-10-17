package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.gui.theinterface.AssemblyMiniGame;
import com.anonym.astran.systems.gui.theinterface.pages.AssemblyCyberInterface;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class AssemblyCraftButton extends InterfaceButton{

    private AssemblyCyberInterface screen;

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/interface/craft_button.png");

    private static final ResourceLocation TEXTURE_IN =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/interface/craft_button_inside.png");
    public AssemblyCraftButton(float x, float y, AssemblyCyberInterface screen) {
        super(x, y, 72, 36, (button) -> {
            if (screen.selectedRecipe.canCraftWithChosen(Minecraft.getInstance().player,
                    screen.selectedRecipe.getSelectedStacks(screen.selectedIngredients))) {
                screen.onClose();
                Minecraft.getInstance().setScreen(new AssemblyMiniGame(screen.selectedRecipe, screen.moduleCache.get(),screen.selectedRecipe.getSelectedStacks(screen.selectedIngredients)));
            }
        });
        this.screen = screen;
    }

    @Override
    public float getRealX() {
        float eased = Easing.EASE_IN_OUT_BACK.ease(this.screen.assemblyShown);
        return super.getRealX() + ((1 - eased) * 80);
    }

    @Override
    public void playDownSound(SoundManager handler) {
        Minecraft.getInstance().player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(),1f,2f);
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        float addedTicks = Minecraft.getInstance().player.tickCount + partialTick;

        float rot = (float) -Math.sin(addedTicks * 0.1);
        float cosRot = (float) Math.cos(addedTicks * 0.1);

        guiGraphics.pose().translate(this.getRealX()+36,this.getRealY() + (cosRot*1) +16,0);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rot*2.5f));
        guiGraphics.blit(TEXTURE,-36,-16,0,0,72,36,72,36);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale( 1+(rot*0.05f) + 0.05f,1+(rot*0.05f) +0.05f,0);
        guiGraphics.blit(TEXTURE_IN,-36,-16,0,0,72,36,72,36);

        guiGraphics.pose().popPose();
        guiGraphics.pose().popPose();
    }
}
