package com.anonym.astran.client.layers;

import com.anonym.astran.client.models.entities.meteor.AstraniumMeteorModel;
import com.anonym.astran.client.models.misc.MinigameAnvil;
import com.anonym.astran.client.models.misc.MinigameHammer;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.client.models.modules.torso.AquaLungsModel;
import com.anonym.astran.client.models.modules.torso.PoisonFilterModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
public class AstranLayerDefinition {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LayerRegistry.ASTRANIUM_METEOR, AstraniumMeteorModel::createBodyLayer);
        event.registerLayerDefinition(LayerRegistry.MINIGAME_ANVIL, MinigameAnvil::createBodyLayer);
        event.registerLayerDefinition(LayerRegistry.MINIGAME_HAMMER, MinigameHammer::createBodyLayer);

    }

    @SubscribeEvent
    public static void registerModuleLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModuleLayerRegistry.EYE_MODULE, EyeModuleModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.AQUA_LUNGS_MODULE, AquaLungsModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.POISON_FILTER_MODULE, PoisonFilterModel::createBodyLayer);

    }


}
