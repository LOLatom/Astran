package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EquipModuleHandler {
    public class Server {
        public static void handleDataOnNetwork(EquipModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().equipModule(data.socketIndex(),data.module());
            }
        }
    }
    public class Client {
        public static void handleDataOnNetwork(EquipModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().equipModule(data.socketIndex(),data.module());
            }
        }
    }
}
