package com.anonym.astran.systems.gui.theinterface;


import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.core.SetSteelHeartPayload;
import com.anonym.astran.systems.cybernetics.core.SteelHeartItem;
import com.anonym.astran.systems.gui.theinterface.pages.HomePageCyberInterface;
import com.anonym.astran.systems.keybind.base.AstranKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.Optional;

@EventBusSubscriber(value = Dist.CLIENT)
public class InterfaceOpenEvent {

    @SubscribeEvent
    public static void onPressOpenInterface(ClientTickEvent.Post event) {
        if (AstranKeybinds.MODULA.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            SteelHeartReservoirData data = player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR);
            if (data.getSteelHeart().isPresent()) {
                if (Minecraft.getInstance().screen == null) {
                    player.playSound(AstranSoundRegistry.INTERFACE_START.get(), 1f, 1f);
                    Minecraft.getInstance().setScreen(new HomePageCyberInterface(true));
                }
            } else {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SteelHeartItem steelHeartItem) {
                    if (steelHeartItem.getNodes(player.getItemInHand(InteractionHand.MAIN_HAND)).firstNode().isPresent()) {
                        player.setData(
                                AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR,
                                new SteelHeartReservoirData(Optional.of(player.getItemInHand(InteractionHand.MAIN_HAND).copy())));
                        LocalPlayer localPlayer = (LocalPlayer) player;
                        localPlayer.connection.send(new SetSteelHeartPayload(player.getItemInHand(InteractionHand.MAIN_HAND)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof CyberInterfaceScreen) {
            event.setCanceled(true);
        }
    }

}
