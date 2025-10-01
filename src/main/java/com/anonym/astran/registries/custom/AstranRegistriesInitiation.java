package com.anonym.astran.registries.custom;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber
public class AstranRegistriesInitiation {

    @SubscribeEvent
    static void initialiseConsoleRegistries(NewRegistryEvent event) {
        event.register(AstranRegistries.CYBER_MODULE_REGISTRY);
        event.register(AstranRegistries.MATERIAL_TYPE_REGISTRY);
    }
}
