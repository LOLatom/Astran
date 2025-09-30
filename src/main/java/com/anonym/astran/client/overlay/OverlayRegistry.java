package com.anonym.astran.client.overlay;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.core.SteelHeartInsertionOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class OverlayRegistry {

    @SubscribeEvent
    public static void registerAstranOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"steel_heart_insertion_ov"),SteelHeartInsertionOverlay::render);
    }

}
