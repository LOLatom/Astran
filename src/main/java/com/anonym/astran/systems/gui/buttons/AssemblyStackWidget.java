package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.gui.theinterface.pages.AssemblyCyberInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AssemblyStackWidget extends InterfaceButton implements IGlowModifier {

    private AssemblyCyberInterface screen;
    private int id = 0;

    private static final ResourceLocation LEFT_STACK_BORDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/stack_border_left.png");
    private static final ResourceLocation RIGHT_STACK_BORDER =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/stack_border_right.png");

    private static final ResourceLocation STACK_SELECTION =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/stack_selection.png");

    public AssemblyStackWidget(float x, float y, float width, float height, AssemblyCyberInterface screen, int id) {
        super(x, y, width, height, (button) -> {});
        this.screen = screen;
        this.id = id;
    }



    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isMouseOver(mouseX, mouseY)) {
            if (!this.screen.scrollDisabled[this.id]) this.screen.scrollDisabled[this.id] = true;
        } else {
            if (this.screen.scrollDisabled[this.id]) this.screen.scrollDisabled[this.id] = false;
        }

        float addedTicks = Minecraft.getInstance().player.tickCount + partialTick;

        if (this.screen.selectedRecipe == null) return;

        Player player = Minecraft.getInstance().player;
        AssemblyAbstractRecipe recipe = this.screen.selectedRecipe;
        LinkedHashMap<String, List<ItemStack>> recipeItems = recipe.getNamedIngredients();
        List<List<ItemStack>> recipeValues = new ArrayList<>(recipeItems.values());

        List<ItemStack> list = (this.id >= 0 && this.id < recipeValues.size()) ? recipeValues.get(this.id) : Collections.emptyList();
        if (list.isEmpty()) return;

        final int s = list.size();
        int sel = 0;
        if (this.screen.selectedIngredients != null && this.screen.selectedIngredients.length > this.id) {
            sel = Math.floorMod(this.screen.selectedIngredients[this.id], s);
        }
        int nextIndex = (sel + 1) % s;
        int prevIndex = (sel - 1 + s) % s;

        LinkedHashMap<String, List<ItemStack>> itemInInv = this.screen.getCachedInventoryIngredients(recipe);

        List<String> recipeKeys = new ArrayList<>(recipeItems.keySet());
        String ingredientName = (this.id >= 0 && this.id < recipeKeys.size()) ? recipeKeys.get(this.id) : null;

        ItemStack required = list.get(sel);
        int requiredCount = required.getCount();

        int playerHas = 0;
        if (ingredientName != null && itemInInv != null && itemInInv.containsKey(ingredientName)) {
            for (ItemStack cachedStack : itemInInv.getOrDefault(ingredientName, Collections.emptyList())) {
                if (cachedStack.getItem().equals(required.getItem())) {
                    playerHas = cachedStack.getCount();
                    break;
                }
            }
        }

        Color countColor;
        if (playerHas >= requiredCount) {
            countColor = new Color(0, 255, 0);
        } else {
            countColor = new Color(255, 50, 50);
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX() + 4, this.getRealY(), 0);
        if (s > 1) {
            guiGraphics.blit(LEFT_STACK_BORDER, -26, 0, 0, 0, 12, 16, 12, 16);
            guiGraphics.blit(RIGHT_STACK_BORDER, 22, 0, 0, 0, 12, 16, 12, 16);
        } else {
            guiGraphics.blit(LEFT_STACK_BORDER, -19, 0, 0, 0, 12, 16, 12, 16);
            guiGraphics.blit(RIGHT_STACK_BORDER, 14, 0, 0, 0, 12, 16, 12, 16);
        }
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX(), this.getRealY(), 0);
        guiGraphics.blit(STACK_SELECTION, 0, 0, 0, 0, 18, 18, 18, 18);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX(), this.getRealY(), 0);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(8, 8, 0);
        guiGraphics.pose().scale(
                (float)(1 + (Math.sin(addedTicks * 0.1) * 0.1)),
                (float)(1 + (Math.sin(addedTicks * 0.1) * 0.1)),
                1);
        guiGraphics.renderItem(required, -8, -8);
        guiGraphics.pose().popPose();

        if (s > 1) {
            guiGraphics.setColor(1f, 1f, 1f, 0.4f);
            // next
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(14 + 8, 8, 0);
            guiGraphics.pose().scale(
                    (float)(0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                    (float)(0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                    1);
            guiGraphics.renderItem(list.get(nextIndex), -8, -8);
            guiGraphics.pose().popPose();

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(-8, 8, 0);
            guiGraphics.pose().scale(
                    (float)(0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                    (float)(0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                    1);
            guiGraphics.renderItem(list.get(prevIndex), -8, -8);
            guiGraphics.pose().popPose();

            guiGraphics.setColor(1f, 1f, 1f, 1f);
        }
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getRealX(), this.getRealY() + 10, 180);

        guiGraphics.drawString(Minecraft.getInstance().font,
                String.valueOf(requiredCount),
                0, 0, countColor.darker().darker().darker().getRGB(), false);

        guiGraphics.pose().translate(-0.8, -0.8, 0);

        guiGraphics.drawString(Minecraft.getInstance().font,
                String.valueOf(requiredCount),
                0, 0, countColor.getRGB(), false);

        guiGraphics.pose().popPose();
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.isMouseOver(mouseX,mouseY)) {
            System.err.println("HERE");
            if (this.screen.selectedRecipe != null) {
                AssemblyAbstractRecipe recipe = this.screen.selectedRecipe;
                LinkedHashMap<String, List<ItemStack>> itemInInv = recipe.getInInventoryIngredients(Minecraft.getInstance().player);
                LinkedHashMap<String, List<ItemStack>> recipeItems = recipe.getNamedIngredients();

                List<ItemStack> list =
                        recipeItems.values().stream().toList().size() - 1 >= this.id ?
                                recipeItems.values().stream().toList().get(this.id) : new ArrayList<>();

                if (list.size()-1 >= this.screen.selectedIngredients[this.id] + 1) {
                    if (scrollY>0) {
                        this.screen.selectedIngredients[this.id] = this.screen.selectedIngredients[this.id] + 1;
                    }
                } else {
                    if (scrollY>0){
                        this.screen.selectedIngredients[this.id] = 0;
                    }
                }
                if (this.screen.selectedIngredients[this.id] > 0) {
                    if (scrollY<0){
                        this.screen.selectedIngredients[this.id] = this.screen.selectedIngredients[this.id] - 1;
                    }
                } else {
                    if (scrollY<0){
                        this.screen.selectedIngredients[this.id] = list.size() - 1;
                    }
                }
                this.screen.cyberModuleDirty = true;

            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
