package com.anonym.astran.registries.client;

import com.anonym.astran.client.TestRender;
import com.anonym.astran.registries.AstranEntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(value = Dist.CLIENT)
public class RenderRegistryEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(
                AstranEntityRegistry.TEST.get(),
                TestRender::new);
    }
}
