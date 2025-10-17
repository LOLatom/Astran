package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RemoveModuleHandler {
    public class Server {
        public static void handleDataOnNetwork(RemoveModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().removeModule(data.module());
            }
        }
    }
    public class Client {
        public static void handleDataOnNetwork(RemoveModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().removeModule(data.module());
            }
        }
    }
}
