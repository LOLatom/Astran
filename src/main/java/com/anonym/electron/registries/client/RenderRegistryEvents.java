package com.anonym.electron.registries.client;

import com.anonym.electron.client.TestRender;
import com.anonym.electron.registries.ElectronEntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(value = Dist.CLIENT)
public class RenderRegistryEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(
                ElectronEntityRegistry.TEST.get(),
                TestRender::new);
    }
}
