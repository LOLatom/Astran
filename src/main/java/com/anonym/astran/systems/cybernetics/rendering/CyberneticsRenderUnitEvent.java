package com.anonym.astran.systems.cybernetics.rendering;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class CyberneticsRenderUnitEvent {

    @SubscribeEvent
    public static void renderPlayerCybernetics(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();


    }


}
