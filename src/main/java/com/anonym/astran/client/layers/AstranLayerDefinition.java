package com.anonym.astran.client.layers;

import com.anonym.astran.client.models.PlayerBaseModel;
import com.anonym.astran.client.models.entities.meteor.AstraniumMeteorModel;
import com.anonym.astran.client.models.misc.MinigameAnvil;
import com.anonym.astran.client.models.misc.MinigameHammer;
import com.anonym.astran.client.models.modules.head.AztecFaceModel;
import com.anonym.astran.client.models.modules.head.EyeModuleModel;
import com.anonym.astran.client.models.modules.head.FrontFaceModel;
import com.anonym.astran.client.models.modules.torso.*;
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
        event.registerLayerDefinition(LayerRegistry.PLAYER_MODEL, PlayerBaseModel::createBodyLayer);

    }

    @SubscribeEvent
    public static void registerModuleLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModuleLayerRegistry.EYE_MODULE, EyeModuleModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.AQUA_LUNGS_MODULE, AquaLungsModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.POISON_FILTER_MODULE, PoisonFilterModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.BACK_BASE, BackBaseModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.BACK_COVER, BackCoverModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.KINETIC_ACCUMULATOR, KineticAccumulatorModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.KINETIC_DISTRIBUTOR, KineticDistributorModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.BACK_WINGS, BackWingsModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.FRONT_BASE, FrontBaseModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.FRONT_COVER, FrontCoverModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.FRONT_FACE, FrontFaceModel::createBodyLayer);
        event.registerLayerDefinition(ModuleLayerRegistry.AZTEC_FACE, AztecFaceModel::createBodyLayer);

    }


}
