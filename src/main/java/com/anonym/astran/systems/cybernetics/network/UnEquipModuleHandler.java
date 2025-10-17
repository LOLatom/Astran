package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UnEquipModuleHandler {
    public class Server {
        public static void handleDataOnNetwork(UnEquipModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().unEquipModule(data.socketIndex(),data.module());
            }
        }
    }
    public class Client {
        public static void handleDataOnNetwork(UnEquipModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().unEquipModule(data.socketIndex(),data.module());
            }
        }
    }
}
