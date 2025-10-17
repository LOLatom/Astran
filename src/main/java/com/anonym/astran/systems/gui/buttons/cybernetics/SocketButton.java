package com.anonym.astran.systems.gui.buttons.cybernetics;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.LimbType;
import com.anonym.astran.systems.cybernetics.SocketData;
import com.anonym.astran.systems.gui.buttons.DiamondDetectionButton;
import com.anonym.astran.systems.gui.theinterface.CyberInterfaceScreen;
import com.anonym.astran.systems.gui.theinterface.pages.LimbCyberInterfaceScreen;
import com.anonym.astran.systems.gui.theinterface.pages.LimbInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;

public class SocketButton extends DiamondDetectionButton {

    private LimbInterface limbScreen;
    private SocketData data;
    private int index;

    public static final ResourceLocation LEVEL1 =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/module_socket_level1.png");
    public static final ResourceLocation LEVEL2 =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/module_socket_level2.png");
    public static final ResourceLocation LEVEL3 =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/module_socket_level3.png");

    public static final ResourceLocation NOT_EMPTY =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/gui/interface/cybernetics/taken_socket.png");


    public SocketButton(float x, float y, int size, LimbInterface screen, SocketData data , int index) {
        super(((data.getSocketTier() > 0 && data.getSocketTier() <= 4) ? LEVEL1 :
                        (data.getSocketTier() > 4 && data.getSocketTier() <= 8) ? LEVEL2 : LEVEL3)
                , x, y, size, (button -> {
                    if (screen.selectedIndex != index) {
                        screen.selection = 0;
                        screen.collectionMap = screen.manager.collectFromLimb(screen.type);
                        if (data.hasModule()) {
                            int i = 0;
                            for (CyberModule module : screen.collectionMap.values()) {
                                if (module.getInstanceId().equals(data.getModuleInstanceId())) {
                                    screen.selection = i;
                                }
                                i++;
                            }
                        }
                        screen.selectedIndex = index;
                    } else {
                        screen.selection = 0;
                        screen.collectionMap.clear();
                        screen.selectedIndex = -1;
                    }
                    System.out.println(screen.collectionMap);
                }), screen, "scoket");
        this.limbScreen = screen;
        this.data = data;
        this.index = index;
    }
    @Override
    public void playDownSound(SoundManager handler) {
        Minecraft.getInstance().player.playSound(AstranSoundRegistry.INTERFACE_SLOT_SELECT.get(),1f,1f);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.shakeDecrease > 0) {
            this.shakeDecrease = Math.clamp((this.shakeDecrease - 0.2f),0,1);
        }

        if (this.isMouseOver(mouseX,mouseY) || this.limbScreen.selectedIndex == this.index) {
            if (this.addedScaleXY ==0f) {
            }
            this.addedScaleXY = Math.clamp((this.addedScaleXY + 0.05f),0,1);
        } else if (this.addedScaleXY > 0) {
            this.addedScaleXY = Math.clamp((this.addedScaleXY - 0.05f),0,1);
        }
        if (this.isMouseOver(mouseX,mouseY)) {
            this.screen.setCurrentFocused(this);
        } else {
            if (this.screen.currentFocused() != null) {
                if (this.screen.currentFocused().equals(this)) {
                    this.screen.setCurrentFocused(null);
                }
            }
        }
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.getX(),this.getY(),0);

        float scaleXY = Easing.EASE_IN_CIRC.ease(this.addedScaleXY) * 0.2f;
        float color = Easing.EASE_IN_CIRC.ease(this.addedScaleXY) * 0.4f;


        float addedX = -(36*scaleXY) / 2;
        float addedY = -(36*scaleXY) / 2;
        guiGraphics.pose().translate(addedX,addedY,0);
        guiGraphics.pose().translate(Math.sin((this.shakeDecrease * 2f) *0.5),Math.cos((this.shakeDecrease * -2f) * 0.5),0);
        guiGraphics.pose().scale(1f+scaleXY,1f+scaleXY,1f);


        float[] colored = RenderSystem.getShaderColor();

        guiGraphics.setColor((colored[0] * 0.6f) + (colored[0] * color),
                (colored[1] * 0.6f) + (colored[1] * color),
                (colored[2] * 0.6f) + (colored[2] * color),
                colored[3]);

        guiGraphics.blit(this.texture,0,0,0,((this.data.getSocketTier() % 4)-1)* 32,32,32,32,128);
        if (this.limbScreen.manager.getBoneDataFromModule(this.limbScreen.type).getSockets().get(this.index).hasModule()) {
            guiGraphics.blit(NOT_EMPTY, -2, -2, 0, 0, 36, 36, 36, 36);
        }

        guiGraphics.pose().popPose();
        guiGraphics.setColor(1,1,1,1f);    }
}
