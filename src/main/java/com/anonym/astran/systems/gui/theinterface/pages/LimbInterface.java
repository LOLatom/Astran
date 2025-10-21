package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.cybernetics.*;
import com.anonym.astran.systems.cybernetics.material.MaterialType;
import com.anonym.astran.systems.gui.buttons.DiamondDetectionButton;
import com.anonym.astran.systems.gui.buttons.cybernetics.SocketButton;
import com.anonym.astran.systems.gui.theinterface.CameraCyberInterfaceScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LimbInterface extends CameraCyberInterfaceScreen {

    public LimbType type;
    public int selectedIndex = -1;
    public Map<UUID, CyberModule> collectionMap = new HashMap<>();
    public CyberneticsManager manager;
    public float collectionShow = 0f;
    public int selection = 0;
    public static final Color notFoundRED = new Color(204, 60, 60,255);

    public LimbInterface(int width, int height, boolean animated, LimbType type) {
        super(width, height, animated);
        this.type = type;
        this.manager = ((IContainCyberneticsManager)Minecraft.getInstance().player).manager();
    }

    @Override
    protected void init() {
        super.init();
        this.addGlowingRenderable(new DiamondDetectionButton(
                ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/interface/interface_home.png"),
                this.width / 2f + 3f, this.height / 2f - 82f,
                36, button -> {
            this.addTransition(new LimbCyberInterfaceScreen(),20, Easing.EASE_IN_BACK);
        },this, "HOME"));

        CyberneticsManager manager = ((IContainCyberneticsManager) Minecraft.getInstance().player).manager();
        for (int i = 0; i < 10; i++) {
            float y = (i > 4) ? 0 : 40;
            boolean isFirstRow = (i > 4);
            float x = (i < 5) ? i : i - 5;
            this.addGlowingRenderable(new SocketButton(this.width / 2 + 10 + 15 + ((38*(x))) - (isFirstRow ? 0 : 19), this.height / 2 + y - 55, 30, this, manager.getBoneDataFromModule(this.type).getSockets().get(i), i));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(118,this.height - 25,0);
        guiGraphics.drawString(Minecraft.getInstance().font,"Press W, A, S, D to move",
                -(Minecraft.getInstance().font.width("Press W, A, S, D to move")/2),8,Color.WHITE.getRGB());
        guiGraphics.drawString(Minecraft.getInstance().font,"Scroll to switch Modules",
                -(Minecraft.getInstance().font.width("Scroll to switch Modules")/2),-this.height + 30,Color.WHITE.getRGB());
        guiGraphics.drawString(Minecraft.getInstance().font,"Press E to Equip",
                -(Minecraft.getInstance().font.width("Press E to Equip")/2),-this.height + 50,Color.WHITE.getRGB());
        guiGraphics.pose().popPose();

    }

    @Override
    public void renderCut(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderCut(guiGraphics, mouseX, mouseY, partialTick);
        if (this.selectedIndex != -1) {
            this.collectionShow = Math.clamp(this.collectionShow + 0.02f,0f,1f);
        } else if (this.collectionShow > 0f) {
            this.collectionShow = Math.clamp(this.collectionShow - 0.02f,0f,1f);
        }
        float ease = 1 - Easing.EASE_IN_OUT_BACK.ease(this.collectionShow);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,160 + (90 *ease),0);
        guiGraphics.fill(-90,0,900,120, Color.BLACK.getRGB());
        guiGraphics.fill(-90,2,900,3, AstranMaterialTypeRegistry.BRONZINE.get().getColorPaletteModifier().getLighter().getRGB());

        guiGraphics.pose().popPose();
        if (!this.collectionMap.isEmpty()) {
            List<CyberModule> modules = this.collectionMap.values().stream().toList();
            float addedTicks = Minecraft.getInstance().player.tickCount + partialTick;
            float sin = (float) (Math.sin(addedTicks*0.3f) * 10f);
            guiGraphics.setColor(25f,25f,25f,1f);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((this.width / 2f + 45f) + 60, 195 + (90 * ease), 20);
            guiGraphics.pose().scale(100 + sin, 100 + sin, 5);
            guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(-25));
            guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTick));
            modules.get(this.selection).getPrimitiveClass().render(
                    modules.get(this.selection), Minecraft.getInstance().player,
                    partialTick, guiGraphics.pose(), guiGraphics.bufferSource(),
                    15728880, true);
            guiGraphics.pose().popPose();
            guiGraphics.flush();
            guiGraphics.setColor(5f,5f,5f,0.2f);
            if (collectionMap.size() - 1 == this.selection) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((this.width / 2f + 45f) + 120, 195 + (90 * ease), 20);
                guiGraphics.pose().scale(80, 80, 5);
                guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(-25));
                guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTick));
                modules.get(0).getPrimitiveClass().render(
                        modules.get(0), Minecraft.getInstance().player,
                        partialTick, guiGraphics.pose(), guiGraphics.bufferSource(),
                        15728880, true);
                guiGraphics.pose().popPose();
            } else {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((this.width / 2f + 45f) + 120, 195 + (90 * ease), 20);
                guiGraphics.pose().scale(80, 80, 5);
                guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(-25));
                guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTick));
                modules.get(this.selection+1).getPrimitiveClass().render(
                        modules.get(this.selection+1), Minecraft.getInstance().player,
                        partialTick, guiGraphics.pose(), guiGraphics.bufferSource(),
                        15728880, true);
                guiGraphics.pose().popPose();
            }
            if (this.selection == 0) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((this.width / 2f + 45f), 195 + (90 * ease), 20);
                guiGraphics.pose().scale(80, 80, 5);
                guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(-25));
                guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTick));
                modules.get(this.collectionMap.size()-1).getPrimitiveClass().render(
                        modules.get(this.collectionMap.size()-1), Minecraft.getInstance().player,
                        partialTick, guiGraphics.pose(), guiGraphics.bufferSource(),
                        15728880, true);
                guiGraphics.pose().popPose();
            } else {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((this.width / 2f + 45f), 195 + (90 * ease), 20);
                guiGraphics.pose().scale(80, 80, 5);
                guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(-25));
                guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTick));
                modules.get(this.selection-1).getPrimitiveClass().render(
                        modules.get(this.selection-1), Minecraft.getInstance().player,
                        partialTick, guiGraphics.pose(), guiGraphics.bufferSource(),
                        15728880, true);
                guiGraphics.pose().popPose();
            }
            guiGraphics.flush();

            guiGraphics.setColor(1f,1f,1f,1f);
        } else {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((this.width / 2f + 108f), 190 + (90 * ease), 20);
            guiGraphics.pose().scale(1.6f,1.6f,1);
            guiGraphics.drawString(Minecraft.getInstance().font, "Modules Not Found" ,
                    -(Minecraft.getInstance().font.width("Modules Not Found")/2),0,
                    notFoundRED.getRGB());
            guiGraphics.pose().popPose();

        }

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E) {
            if (this.collectionShow == 1) {
                if (!this.collectionMap.isEmpty()) {
                    int i = 0;
                    SocketData data = manager.getBoneDataFromModule(this.type).getSockets().get(this.selectedIndex);
                    for (CyberModule module : this.collectionMap.values()) {
                        if (i == this.selection) {
                            if (data.hasModule()) {
                                if (data.getModuleInstanceId().equals(module.getInstanceId())) {
                                    this.manager.syncUnEquipModule(this.selectedIndex, module);
                                    Minecraft.getInstance().player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(),1,1);
                                    System.out.println("UN-EQUIP");
                                    return true;

                                } else {
                                    if (module.isEquippable(module,this.manager,data)) {
                                        this.manager.syncEquipModule(this.selectedIndex, module);
                                        Minecraft.getInstance().player.playSound(SoundEvents.ARMOR_EQUIP_IRON.value(),0.7f,1);
                                        System.out.println("EQUIP");
                                    } else {
                                        Minecraft.getInstance().player.playSound(AstranSoundRegistry.INTERFACE_ERROR.get(),0.7f,1);
                                    }
                                    return true;

                                }
                            } else {
                                if (module.isEquippable(module,this.manager,data)) {
                                    this.manager.syncEquipModule(this.selectedIndex, module);
                                    Minecraft.getInstance().player.playSound(SoundEvents.ARMOR_EQUIP_IRON.value(),1,1);
                                    System.out.println("EQUIP2");
                                } else {
                                    Minecraft.getInstance().player.playSound(AstranSoundRegistry.INTERFACE_ERROR.get(),1,1);
                                }
                                return true;
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.collectionShow == 1) {
            if (this.collectionMap.size() > 1) {
                System.out.println(this.selection);
                if (scrollY > 0) {
                    if (this.selection == 0) {
                        this.selection = this.collectionMap.size() - 1;
                    } else {
                        this.selection--;
                    }
                } else if (scrollY < 0) {
                    if (collectionMap.size() - 1 == this.selection) {
                        this.selection = 0;
                    } else {
                        this.selection++;
                    }

                }
                System.out.println(this.selection);

            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
