package com.anonym.astran.common.events;

import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import com.anonym.astran.systems.cybernetics.LimbType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class TestingStuffForCyb {

    @SubscribeEvent
    public static void testing(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof IContainCyberneticsManager manager) {
            //System.out.println("IsClientSide : " + event.getEntity().level().isClientSide +  "   |   " + manager.manager().getStorageForLimb(LimbType.HEAD).getCyberModuleMap());
        }
    }
}
