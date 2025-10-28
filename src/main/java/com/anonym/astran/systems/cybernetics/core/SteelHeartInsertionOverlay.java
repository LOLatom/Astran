package com.anonym.astran.systems.cybernetics.core;

import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class SteelHeartInsertionOverlay {

    public static float appearing = 0f;


    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        float aOld = appearing;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SteelHeartItem steelHeartItem) {
            SteelHeartData data = steelHeartItem.getNodes(player.getItemInHand(InteractionHand.MAIN_HAND));
            if (data.firstNode().isPresent()) {
                appearing = (float) Math.clamp(appearing + 0.05,0,2);

            } else if (appearing > 0) {
                appearing = (float) Math.clamp(appearing - 0.05,0,2);
            }
        } else if (appearing > 0) {
            appearing = (float) Math.clamp(appearing - 0.05,0,2);
        }

        float easedDelta = 0;
        if (aOld < appearing) {
            easedDelta = Easing.EASE_OUT_BACK.ease(appearing/2);
        } else {
            easedDelta = Easing.EASE_IN_BACK.ease(appearing/2);
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SteelHeartItem steelHeartItem) {
            float pT = deltaTracker.getGameTimeDeltaPartialTick(true);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(guiGraphics.guiWidth() / 2, guiGraphics.guiHeight(), 0);
            guiGraphics.pose().translate(Math.sin((player.tickCount + pT) * 2) * 0.2, Math.cos((player.tickCount + pT) * 2) * 0.2, 0);
            guiGraphics.pose().scale(2, 2, 0);
            guiGraphics.pose().translate(-12, -40, 0);

            RenderSystem.enableBlend();
            guiGraphics.setColor(1, 1, 1, easedDelta);
            guiGraphics.drawString(Minecraft.getInstance().font, "<[R]>", 0, 0, Color.WHITE.getRGB());
            guiGraphics.setColor(1, 1, 1, 1f);
            guiGraphics.pose().popPose();
            RenderSystem.disableBlend();
        }
    }
}
