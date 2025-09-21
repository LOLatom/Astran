package com.anonym.astran.client.layers;

import com.anonym.astran.client.models.entities.meteor.AstraniumMeteorModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
public class LayerDefinition {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LayerRegistry.ASTRANIUM_METEOR, AstraniumMeteorModel::createBodyLayer);

    }


}
