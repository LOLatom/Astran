package com.anonym.astran.systems.world.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestStructureHandler {
    public class Server {
        public static void handleDataOnNetwork(RequestStructurePayload data, final IPayloadContext context) {
            Map<ResourceLocation, CompoundTag> cache = new HashMap<>();
            ServerLevel level = (ServerLevel) context.player().level();
            StructureTemplateManager templateManager = level.getStructureManager();
            for (ResourceLocation location : data.locations()) {
                Optional<StructureTemplate> template = templateManager.get(location);
                if (template.isPresent()) {
                    CompoundTag tag = template.get().save(new CompoundTag());
                    cache.put(location,tag);
                } else {
                    System.err.println("[StructureRequest]: Structure -" + location.toString() + "- Does not Exist In StructureManager");
                }
            }
            if (context.player() instanceof ServerPlayer player) {
                player.connection.send(new SendStructureDataPayload(cache));
            }
        }
    }
}
