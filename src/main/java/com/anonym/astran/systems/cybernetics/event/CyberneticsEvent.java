package com.anonym.astran.systems.cybernetics.event;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class CyberneticsEvent {

    @SubscribeEvent
    public static void onTickCybernetics(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof IContainCyberneticsManager manager) {
            if (!manager.manager().moduleCache().getEquippedTickable().isEmpty()) {
                for (CyberModule module : manager.manager().moduleCache().getEquippedTickable().values()) {
                    module.tickModule(event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public static void lungsHelp(LivingBreatheEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player instanceof IContainCyberneticsManager manager) {
                if (manager.manager().moduleCache().isModuleEquipped("aqua_lungs")) {
                    event.setConsumeAirAmount(0);
                }
            }
        }
    }

}
