package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.registries.custom.AstranRegistries;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.gui.buttons.*;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AssemblyCyberInterface extends CyberInterfaceScreen {

    public float scrollOffsetY = 0f;
    public float infoOffsetX = 0f;
    public boolean[] scrollDisabled = {false,false,false,false,false,false};

    public AssemblyAbstractRecipe selectedRecipe = null;
    public float assemblyShown = 0f;
    public float initialWidth = 5.8f;
    public float initialHeight = 9f;
    public int[] selectedIngredients = {0,0,0,0,0};

    private LinkedHashMap<String, List<ItemStack>> cachedInventoryIngredients = new LinkedHashMap<>();
    private int lastCacheTick = -1;
    private AssemblyAbstractRecipe lastRecipe = null;

    public Optional<CyberModule> moduleCache = Optional.empty();
    public boolean cyberModuleDirty = false;



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
            this.addRenderableWidget(new AssemblyStackWidget(
                    this.getOffsetX() + ((6f*16)) + 31,
                    this.getOffsetY() + (v * 19) - 18
                    ,16,16,this,v));

        }

        this.addRenderableWidget(new AssemblyCraftButton(
                this.getOffsetX() + 13,
                this.getOffsetY() + 55,this
                ));

    }

    public LinkedHashMap<String, List<ItemStack>> getCachedInventoryIngredients(AssemblyAbstractRecipe recipe) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return cachedInventoryIngredients;

        if (player.tickCount % 5 == 0 || recipe != this.lastRecipe) {
            this.cachedInventoryIngredients = recipe.getInInventoryIngredients(player);
            this.lastRecipe = recipe;
        }

        return this.cachedInventoryIngredients;
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
            return super.mouseClicked(mouseX, mouseY, button);
        } else {
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

    @Override
    public void tick() {
        super.tick();
        if (this.cyberModuleDirty) {
            if (this.selectedRecipe != null) {
                LinkedHashMap<String, ItemStack> map = this.selectedRecipe.getSelectedStacks(this.selectedIngredients);
                Optional<CyberModule> optModule = this.selectedRecipe.buildCyberModule(map);
                if (optModule.isPresent()) {
                    this.moduleCache = optModule;
                } else {
                    this.moduleCache = Optional.empty();
                }
            } else {
                this.moduleCache = Optional.empty();
            }
            this.cyberModuleDirty = false;
            System.out.println("MODULE INSTANCE CACHED");
        }
    }
}
