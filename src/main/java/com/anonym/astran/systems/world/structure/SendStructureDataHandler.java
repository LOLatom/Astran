package com.anonym.astran.systems.world.structure;

import com.anonym.astran.helpers.StructureHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SendStructureDataHandler {
    public class Client {
        public static void handleDataOnNetwork(SendStructureDataPayload data, final IPayloadContext context) {
            for (ResourceLocation location : data.cache().keySet()) {
                //System.out.println(StructureHelper.structureCache);
                if (!StructureHelper.structureCache.containsKey(location)) {
                    System.out.println("SENDING COLLECTION");

                    StructureHelper.structureCache.put(location, data.cache().get(location));
                } else {
                    System.err.println("[StructureCache]: Structure -" + location.toString() + "- Is Already Present in Cache");
                }
            }
        }
    }
}
