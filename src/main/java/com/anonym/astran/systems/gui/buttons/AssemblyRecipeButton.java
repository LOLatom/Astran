package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.theinterface.pages.AssemblyCyberInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;


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


    private final AssemblyAbstractRecipe recipe;
    private boolean canBeCrafted = false;
    private AssemblyCyberInterface screen;


    public AssemblyRecipeButton(float x, float y, float width, float height, LimbType type, AssemblyCyberInterface screen, AssemblyAbstractRecipe recipe) {
        super(x, y, width, height, (button) -> {
            if (screen.selectedRecipe != null) {
                if (!screen.selectedRecipe.equals(recipe)) {
                    int v[] = {0,0,0,0,0,0};
                    screen.selectedIngredients = v;
                    screen.selectedRecipe = recipe;
                } else {
                    int v[] = {0,0,0,0,0,0};
                    screen.selectedIngredients = v;
                    screen.selectedRecipe = null;
                }
            } else {
                int v[] = {0,0,0,0,0,0};
                screen.selectedIngredients = v;
                screen.selectedRecipe = recipe;
            }
            screen.cyberModuleDirty = true;
        });
        this.type = type;
        this.recipe = recipe;
        this.screen = screen;
        LinkedHashMap<String,List<ItemStack>> stack = this.recipe.getInInventoryIngredients(Minecraft.getInstance().player);
        if (this.recipe.canBeCrafted(stack)) {
            this.canBeCrafted = true;
        }
    }

    @Override
    public float getRealY() {
        return super.getRealY() - this.screen.scrollOffsetY;
    }

    @Override
    public float getRealX() {
        return super.getRealX() - this.screen.infoOffsetX;
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
        guiGraphics.pose().pushPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX() + 7 + ((float) (this.width * 16) /2) - ((float) Minecraft.getInstance().font.width(recipe.getRecipeName()) /4), this.getRealY() + 7 + 5,0);
        guiGraphics.drawString(Minecraft.getInstance().font, this.recipe.getRecipeName(),0,0, Color.WHITE.getRGB(),false);
        guiGraphics.pose().popPose();
        float side = (this.width*16)+17+17- (7+42+7);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX() + 7 + 40, this.getRealY() + 7 + 16,0);
        String thing = this.recipe.getDescription().getString().length() >= 23 ?
                this.recipe.getDescription().getString(23) + "..." :
                this.recipe.getDescription().toString();
        guiGraphics.drawString(Minecraft.getInstance().font,thing ,0,0, Color.GRAY.getRGB(),false);
        guiGraphics.pose().translate(this.canBeCrafted ? 10 : 15,10,0);
        guiGraphics.drawString(Minecraft.getInstance().font,this.canBeCrafted ? "Can Be Crafted" : "Not Craft-able" ,0,0, this.canBeCrafted ? Color.GREEN.getRGB() : Color.RED.getRGB(),false);
        guiGraphics.pose().popPose();

        guiGraphics.setColor(1,1,1,1);
    }
}
