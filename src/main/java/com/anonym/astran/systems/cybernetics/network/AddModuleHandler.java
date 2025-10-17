package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.registries.custom.AstranRegistries;
import com.anonym.astran.systems.assembly.network.AssembleModulePayload;
import com.anonym.astran.systems.cybernetics.IContainCyberneticsManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class AddModuleHandler {
    public class Server {
        public static void handleDataOnNetwork(AddModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().addModule(data.module());
            }
        }
    }
    public class Client {
        public static void handleDataOnNetwork(AddModulePayload data, final IPayloadContext context) {
            if (context.player() instanceof IContainCyberneticsManager manager) {
                manager.manager().addModule(data.module());
            }
        }
    }
}
