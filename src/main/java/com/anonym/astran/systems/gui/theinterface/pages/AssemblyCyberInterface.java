package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.registries.custom.AstranAssemblyRecipesRegistry;
import com.anonym.astran.registries.custom.AstranRegistries;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.gui.buttons.AssemblyRecipeButton;
import com.anonym.astran.systems.gui.buttons.AssemblyRecipeInfoWidget;
import com.anonym.astran.systems.gui.buttons.AssemblyStackWidget;
import com.anonym.astran.systems.gui.buttons.InformativeButton;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AssemblyCyberInterface extends CyberInterfaceScreen {

    public float scrollOffsetY = 0f;
    public float infoOffsetX = 0f;
    public boolean[] scrollDisabled = {false,false,false,false,false,false};

    public AssemblyAbstractRecipe selectedRecipe = null;
    public float assemblyShown = 0f;
    public float initialWidth = 5.8f;
    public float initialHeight = 9f;
    public int[] selectedIngredients = {0,0,0,0,0};

    public AssemblyCyberInterface() {
        super(5.8f,9,false);

    }

    @Override
    protected void init() {
        super.init();
        final float[] i = {0};
        for (AssemblyAbstractRecipe recipe : AstranRegistries.ASSEMBLY_RECIPES_REGISTRY.stream().toList()) {
            if (recipe.hasAssemblyBlueprint(Minecraft.getInstance().player)) {
                this.addGlowingRenderable(new AssemblyRecipeButton(
                        this.getOffsetX() - (6f*16) + 6.5f,
                        this.getOffsetY() - ((48+48+(16*this.getInterfaceHeight()))/2) +36.5f + i[0],
                        9f,1f
                        ,recipe.getResultModule().get().getAttachment()
                        ,this,recipe));
                i[0] = i[0] + 53;
                System.out.println("ASSEMBLY ADDED");
            }
        }
        this.addGlowingRenderableOnly(new AssemblyRecipeInfoWidget(
                this.getOffsetX() + ((6f*16)),
                this.getOffsetY() - 89,
                this));

        for (int v = 0; v < 6; v++) {
            this.addGlowingRenderable(new AssemblyStackWidget(
                    this.getOffsetX() + ((6f*16)) + 31,
                    this.getOffsetY() + (v * 19) - 18
                    ,16,16,this,v));

        }

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.selectedRecipe != null) {
            this.assemblyShown = Math.clamp(this.assemblyShown + 0.025f,0f,1f);
        } else if (this.assemblyShown > 0) {
            this.assemblyShown = Math.clamp(this.assemblyShown - 0.025f,0f,1f);
        }
        float easedProportion = Easing.EASE_IN_OUT_BACK.ease(this.assemblyShown);
        this.setInterfaceWidth(this.initialWidth + (easedProportion*12));
        this.infoOffsetX = easedProportion *95.5f;

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float x = (float) getOffsetX() - (((128) + (16f*getInterfaceWidth()))/2) + 16;
        float y = (float) getOffsetY() - (((96) + (16f*getInterfaceHeight()))/2) + 16;
        float x2 =  (float) getOffsetX() + (((128) + (16f*getInterfaceWidth()))/2) - 16;
        float y2 = (float)  getOffsetY() + (((96) + (16f*getInterfaceHeight()))/2) - 16;
        if (mouseX > x && mouseX < x2 && mouseY > y && mouseY < y2) {
            System.out.println("INSIDE  x:" + mouseX + " y:" + mouseY );
            return super.mouseClicked(mouseX, mouseY, button);
        } else {
            System.out.println("OUTSIDE  x:" + mouseX + " y:" + mouseY );
            return false;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        boolean isDisabled = false;
        for (boolean b : this.scrollDisabled) {
            if (b) isDisabled = true;
        }

        if (!isDisabled) {
            this.scrollOffsetY = (float) Math.clamp(this.scrollOffsetY - (scrollY * 10f), 0f, AstranRegistries.ASSEMBLY_RECIPES_REGISTRY.stream().toList().size() * (52));
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
