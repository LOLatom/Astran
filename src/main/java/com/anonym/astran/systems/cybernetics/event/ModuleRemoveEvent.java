package com.anonym.astran.systems.cybernetics.event;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.CyberneticsManager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class ModuleRemoveEvent {

    public static class Pre extends Event implements ICancellableEvent {
        private final CyberneticsManager manager;
        private final CyberModule module;

        public Pre(CyberneticsManager manager, CyberModule module) {
            this.manager = manager;
            this.module = module;
        }

        public CyberModule getModule() {
            return this.module;
        }

        public CyberneticsManager getManager() {
            return this.manager;
        }

        public Player getPlayer() {
            return this.manager.getPlayer();
        }

    }

    public static class Post extends Event {
        private final CyberneticsManager manager;
        private final CyberModule module;

        public Post(CyberneticsManager manager, CyberModule module) {
            this.manager = manager;
            this.module = module;
        }

        public CyberModule getModule() {
            return this.module;
        }

        public CyberneticsManager getManager() {
            return this.manager;
        }

        public Player getPlayer() {
            return this.manager.getPlayer();
        }
    }
}
