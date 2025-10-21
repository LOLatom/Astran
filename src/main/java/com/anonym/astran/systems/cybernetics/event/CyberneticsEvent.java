package com.anonym.astran.systems.cybernetics.event;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.effects.DamageEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
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

    @SubscribeEvent
    public static void takeDamageEvent(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            CyberneticsManager manager = CyberneticsManager.getManager(player);
            for (CyberModule  module : manager.moduleCache().getEquippedModuleInstances().values()) {
                event.setNewDamage(module.playerTakeDamage(module, event.getSource(), player, event.getNewDamage()));
            }
        }
    }

    @SubscribeEvent
    public static void takeDamageEvent(AttackEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            CyberneticsManager manager = CyberneticsManager.getManager(player);
            for (CyberModule  module : manager.moduleCache().getEquippedModuleInstances().values()) {
                module.attackEntity(module,event.getEntity(),event.getTarget());
            }
        }
    }

}
