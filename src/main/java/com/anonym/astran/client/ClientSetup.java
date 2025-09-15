package com.anonym.astran.client;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static com.anonym.astran.Astran.MODID;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PostProcessingManager postManager = VeilRenderSystem.renderer().getPostProcessingManager();
        postManager.add(ResourceLocation.fromNamespaceAndPath(MODID,"bloom_pipeline"));
    }
}
