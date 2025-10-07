package com.anonym.astran.systems.gui.buttons;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.gui.theinterface.pages.AssemblyCyberInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        if (this.isMouseOver(mouseX,mouseY)) {
            if (!this.screen.scrollDisabled[this.id]) {
                this.screen.scrollDisabled[this.id] = true;
            }
        } else {
            if (this.screen.scrollDisabled[this.id]) {
                this.screen.scrollDisabled[this.id] = false;
            }
        }
        float addedTicks = Minecraft.getInstance().player.tickCount + partialTick;
        if (this.screen.selectedRecipe != null) {
            AssemblyAbstractRecipe recipe = this.screen.selectedRecipe;
            LinkedHashMap<String, List<ItemStack>> itemInInv = recipe.getInInventoryIngredients(Minecraft.getInstance().player);
            LinkedHashMap<String, List<ItemStack>> recipeItems = recipe.getNamedIngredients();
            //LinkedHashMap<String, ItemStack> selectedItems = recipe.getSelectedStacks()

            List<ItemStack> list =
                    recipeItems.values().stream().toList().size() -1 >= this.id ?
                            recipeItems.values().stream().toList().get(this.id) : new ArrayList<>();
            System.out.println(list.size());
            if (!list.isEmpty()) {
                if (this.screen.selectedIngredients.length >= this.id && list.size() >= this.id) {
                    if (list.size() -1 >= this.screen.selectedIngredients[this.id]) {
                        //System.out.println(recipeItems.values());

                        int s = list.size();
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(this.getRealX() + 4,this.getRealY(),0);
                        //guiGraphics.pose().scale(2f,2f,0);

                        Color color = AstranMaterialTypeRegistry.BRONZINE.get().getColorPaletteModifier().getLighter();

                        if (s > 1) {
                            guiGraphics.blit(LEFT_STACK_BORDER,-26,0,0,0,12,16,12,16);
                            guiGraphics.blit(RIGHT_STACK_BORDER,22,0,0,0,12,16,12,16);

                        } else {
                            guiGraphics.blit(LEFT_STACK_BORDER,-19,0,0,0,12,16,12,16);
                            guiGraphics.blit(RIGHT_STACK_BORDER,14,0,0,0,12,16,12,16);

                        }
                        guiGraphics.pose().popPose();
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(this.getRealX(),this.getRealY(),0);
                        guiGraphics.blit(STACK_SELECTION,0,0,0,0,18,18,18,18);

                        guiGraphics.pose().popPose();


                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(this.getRealX(),this.getRealY(),-150.5);
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(8,8,0);
                        guiGraphics.pose().scale(
                                (float) (1+(Math.sin(addedTicks*0.1)*0.1)),
                                (float) (1+(Math.sin(addedTicks*0.1)*0.1)),
                                1);
                        guiGraphics.renderItem(list.get(this.screen.selectedIngredients[this.id]),-8,-8);
                        guiGraphics.pose().popPose();
                        if (s -1 >= this.screen.selectedIngredients[this.id] +1) {
                            guiGraphics.setColor(1f,1f,1f,0.4f);
                            guiGraphics.pose().pushPose();
                            guiGraphics.pose().translate(14+8,8,0);

                            guiGraphics.pose().scale(
                                    (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                    (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                    1);
                            guiGraphics.renderItem(list.get(this.screen.selectedIngredients[this.id] +1),-8,-8);
                            guiGraphics.pose().popPose();
                            if (this.screen.selectedIngredients[this.id] == 0) {
                                guiGraphics.pose().pushPose();
                                guiGraphics.pose().translate(-(8),8,0);
                                guiGraphics.pose().scale(
                                        (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                        (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                        1);
                                guiGraphics.renderItem(list.get(s-1),-8,-8);
                                guiGraphics.pose().popPose();
                            } else {
                                guiGraphics.pose().pushPose();
                                guiGraphics.pose().translate(-(8),8,0);
                                guiGraphics.pose().scale(
                                        (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                        (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                        1);
                                guiGraphics.renderItem(list.get(this.screen.selectedIngredients[this.id] -1),-8,-8);
                                guiGraphics.pose().popPose();
                            }
                        } else if (this.screen.selectedIngredients[this.id] > 0) {
                            guiGraphics.setColor(1f,1f,1f,0.4f);

                            if (s - 1 > this.screen.selectedIngredients[this.id]) {
                                guiGraphics.pose().pushPose();
                                guiGraphics.pose().translate(14 + 8, 8, 0);
                                guiGraphics.pose().scale(
                                        (float) (0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                                        (float) (0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                                        1);
                                guiGraphics.renderItem(list.get(this.screen.selectedIngredients[this.id] + 1), -8, -8);
                                guiGraphics.pose().popPose();
                            } else {
                                guiGraphics.pose().pushPose();
                                guiGraphics.pose().translate(14 + 8, 8, 0);
                                guiGraphics.pose().scale(
                                        (float) (0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                                        (float) (0.6 + (Math.sin(addedTicks * 0.1) * 0.2)),
                                        1);
                                guiGraphics.renderItem(list.get(0), -8, -8);
                                guiGraphics.pose().popPose();
                            }
                            guiGraphics.pose().pushPose();
                            guiGraphics.pose().translate(-(8),8,0);
                            guiGraphics.pose().scale(
                                    (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                    (float) (0.6+(Math.sin(addedTicks*0.1)*0.2)),
                                    1);
                            guiGraphics.renderItem(list.get(this.screen.selectedIngredients[this.id] -1),-8,-8);
                            guiGraphics.pose().popPose();
                        }

                        guiGraphics.setColor(1f,1f,1f,1f);

                        guiGraphics.pose().popPose();
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(this.getRealX(),this.getRealY()+10,-0.03);
                        guiGraphics.drawString(Minecraft.getInstance().font,
                                String.valueOf(list.get(this.screen.selectedIngredients[this.id]).getCount()),
                                0,0,Color.DARK_GRAY.getRGB());
                        guiGraphics.pose().translate(-0.8,-0.8,0);
                        guiGraphics.drawString(Minecraft.getInstance().font,
                                String.valueOf(list.get(this.screen.selectedIngredients[this.id]).getCount()),
                                0,0,Color.WHITE.getRGB());
                        guiGraphics.pose().popPose();

                    }
                }
            }

        }
        //guiGraphics.renderItem(this.stackUsed,0,0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.isMouseOver(mouseX,mouseY)) {
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

            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
