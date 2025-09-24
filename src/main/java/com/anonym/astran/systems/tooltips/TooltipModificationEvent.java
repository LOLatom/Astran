package com.anonym.astran.systems.tooltips;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.cybernetics.core.SteelHeartItem;
import com.anonym.astran.systems.energy.CoreNodeItem;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.extensions.IGuiGraphicsExtension;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@EventBusSubscriber
public class TooltipModificationEvent {

    private static final ResourceLocation STEELHEART_TOP = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_tooltip_top.png");
    private static final ResourceLocation STEELHEART_BOTTOM = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_tooltip_bottom.png");
    private static final ResourceLocation STEELHEART = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_tooltip.png");
    private static final ResourceLocation STEELHEART_EMPTY = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_tooltip_empty.png");

    private static final ResourceLocation STEELHEART_DESCRIPTION = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_description.png");
    private static final ResourceLocation STEELHEART_DESCRIPTION_KEY_INDICATOR = ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/tooltip/steel_heart_info_key_indicator.png");


    private static float heartShowDescriptionProgress = 0f;

    private static int COLOR1 = new Color(50,58,74,50).getRGB();
    private static int COLOR2 = new Color(52, 67, 69,255).getRGB();
    private static int COLOR3 = new Color(122,113,135).getRGB();
    private static int EMPTY_COLOR = new Color(67, 67, 67,255).getRGB();

    @SubscribeEvent
    public static void tooltipModification(RenderTooltipEvent.Pre event) {
        //event.getGraphics().
        //event.setCanceled(true);

        /*
        event.getGraphics().pose().pushPose();
        event.getGraphics().pose().translate(0,0,620);
        event.getGraphics().drawString(Minecraft.getInstance().font, "Testing",
                event.getX(),event.getY(), new Color(5,255,255,255).getRGB());
        event.getGraphics().pose().popPose();
        */

        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            heartShowDescriptionProgress = (float) Math.clamp(heartShowDescriptionProgress + 0.25,0,30);
        } else if(heartShowDescriptionProgress != 0) {
            heartShowDescriptionProgress = (float) Math.clamp(heartShowDescriptionProgress - 0.25,0,30);
        }

        if (event.getItemStack().getItem() instanceof SteelHeartItem) {
            event.setCanceled(true);

            SteelHeartData data = event.getItemStack().get(AstranDataComponentRegistry.STEEL_HEART_DATA);
            String definition = "♦ Empty";
            int defColor = EMPTY_COLOR;
            CoreNodeItem node1 = null;
            CoreNodeItem node2 = null;
            CoreNodeItem node3 = null;
            if (data.firstNode().isPresent()) {
                if (data.firstNode().get().getItem() instanceof CoreNodeItem node) {
                    node1 = node;
                }
            }
            if (data.secondNode().isPresent()) {
                if (data.secondNode().get().getItem() instanceof CoreNodeItem node) {
                    node2 = node;
                }
            }
            if (data.thirdNode().isPresent()) {
                if (data.thirdNode().get().getItem() instanceof CoreNodeItem node) {
                    node3 = node;
                }
            }

            event.getGraphics().pose().pushPose();
            event.getGraphics().pose().translate(event.getX(),event.getY(),750);

            float desProgress = heartShowDescriptionProgress / 30f;

            float addedTicks = Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

            float effect = Easing.EASE_IN_OUT_QUAD.ease(desProgress);
            float effect2 = Easing.EASE_IN_OUT_QUAD.ease(desProgress);

            event.getGraphics().pose().mulPose(Axis.ZN.rotationDegrees(
                    (float) (Math.sin(addedTicks * 0.1)*5)*(1f-effect)));
            event.getGraphics().blit(STEELHEART_EMPTY,-40,8,0,0,80,48,80,48);
            if (node1 != null) {
                event.getGraphics().blit(node1.getNodeTexture(),-10,18,0,0,20,27,20,27);
            }
            if (node2 != null) {
                event.getGraphics().blit(node1.getNodeTexture(),-34,18,0,0,20,27,20,27);
            }
            if (node3 != null) {
                event.getGraphics().blit(node1.getNodeTexture(),14,18,0,0,20,27,20,27);
            }

            event.getGraphics().pose().translate((73*effect2),0,-3);
            event.getGraphics().blit(STEELHEART_DESCRIPTION,-37,8,0,0,80,48,80,48);


            String text = "Steel Heart";

            event.getGraphics().drawString(Minecraft.getInstance().font, text,-24,16 , COLOR3,false);

            event.getGraphics().drawString(Minecraft.getInstance().font, text,-24,15 ,COLOR2,false);


            event.getGraphics().drawString(Minecraft.getInstance().font, node1 == null ? definition : node1.energyType()
                    ,-24,24 ,node1 == null ? defColor : node1.getNodeColor().getRGB(),false);

            event.getGraphics().drawString(Minecraft.getInstance().font, node2 == null ? definition : node2.energyType()
                    ,-24,33 ,node2 == null ? defColor : node2.getNodeColor().getRGB(),false);

            event.getGraphics().drawString(Minecraft.getInstance().font, node3 == null ? definition : node3.energyType()
                    ,-24,42 ,node3 == null ? defColor : node3.getNodeColor().getRGB(),false);



            event.getGraphics().pose().popPose();

            event.getGraphics().drawString(Minecraft.getInstance().font,
                    "Hold",
                    event.getScreenWidth() / 2 - 42,
                    event.getScreenHeight()- 14 ,
                    Color.WHITE.getRGB(),true);

            event.getGraphics().drawString(Minecraft.getInstance().font,
                    "For Info",
                    event.getScreenWidth() / 2 + 21,
                    event.getScreenHeight()- 14 ,
                    Color.WHITE.getRGB(),true);

            event.getGraphics().blit(STEELHEART_DESCRIPTION_KEY_INDICATOR,
                    event.getScreenWidth() / 2 - 18,
                    event.getScreenHeight()- 30,
                    0,0,36,28,36,28);

        }  else {
            heartShowDescriptionProgress = 0f;
        }

    }


}
