package com.anonym.astran.systems.entity.breakableentity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;



public class BreakingStageHandler {
    public class Client {
        public static void handleDataOnNetwork(BreakingStagePayload data, final IPayloadContext context) {

            Level level = context.player().level();
            if (level.getEntity(data.id()) instanceof BreakableEntity entity) {
                entity.setBreakingStage(data.stage());
                System.out.println("CLIENT");
            }
        }
    }

    public static class Server {
        public static void handleDataOnNetwork(BreakingStagePayload data, final IPayloadContext context) {
            Level level = context.player().level();
            if (level.getEntity(data.id()) instanceof BreakableEntity entity) {
                if (data.stage() == 5) {
                    entity.onBreak();
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }

                entity.setBreakingStage(data.stage());
                System.out.println("SERVER");
            }
        }
    }
}
