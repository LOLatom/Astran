package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.Astran;
import com.anonym.astran.helpers.UUIDHelper;
import com.anonym.astran.registries.custom.AstranRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CachedModuleData {
    public static final Codec<CachedModuleData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(UUIDHelper.CODEC, Codec.STRING)
                            .fieldOf("EquippedModules")
                            .forGetter(CachedModuleData::getEquippedModules),
                    Codec.unboundedMap(Codec.STRING, UUIDHelper.CODEC)
                            .fieldOf("EquippedUUIDs")
                            .forGetter(CachedModuleData::getEquippedUUIDs),
                    Codec.unboundedMap(UUIDHelper.CODEC, Codec.STRING)
                            .fieldOf("EquippedModuleTypes")
                            .forGetter(CachedModuleData::getEquippedModuleTypes),
                    Codec.unboundedMap(UUIDHelper.CODEC, CyberModule.CODEC)
                            .fieldOf("EquippedModuleInstances")
                            .forGetter(CachedModuleData::getEquippedModuleInstances),
                    Codec.unboundedMap(UUIDHelper.CODEC, CyberModule.CODEC)
                            .fieldOf("EquippedTickable")
                            .forGetter(CachedModuleData::getEquippedTickable)
                    , Codec.FLOAT.fieldOf("WeightCached").forGetter(CachedModuleData::getWeightCached)
            ).apply(instance, CachedModuleData::new)
    );

    public static final StreamCodec<ByteBuf, CachedModuleData> STREAM_CODEC =
            ByteBufCodecs.fromCodec(CODEC);

    private final Map<UUID, String> equippedModules;
    private final Map<String, UUID> equippedUUIDs;

    private final Map<UUID, String> equippedModuleTypes;
    private final Map<UUID, CyberModule> equippedModuleInstances;
    private final Map<UUID, CyberModule> equippedTickable;

    private float weightCached;

    public CachedModuleData(Map<UUID, String> equippedModules,Map<String, UUID> equippedUUIDs, Map<UUID, String> equippedModuleTypes, Map<UUID, CyberModule> equippedModuleInstances, Map<UUID, CyberModule> equippedTickable, float weightCached) {
        this.equippedModules = equippedModules;
        this.equippedUUIDs = equippedUUIDs;
        this.equippedModuleTypes = equippedModuleTypes;
        this.equippedModuleInstances = equippedModuleInstances;
        this.equippedTickable = equippedTickable;
        this.weightCached = weightCached;
    }

    public Map<UUID, String> getEquippedModules() {
        return this.equippedModules;
    }

    public boolean isModuleEquipped(UUID uuid) {
        return this.equippedModules.containsKey(uuid);
    }
    public boolean isModuleEquipped(String id) {
        return this.equippedModules.containsValue(id);
    }

    public boolean isModuleTypeEquipped(UUID uuid) {
        return this.equippedModuleTypes.containsKey(uuid);
    }
    public boolean isModuleTypeEquipped(String type) {
        return this.equippedModuleTypes.containsValue(type);
    }

    public CyberModule fromRegistryLookup(UUID uuid) {
        return fromRegistryLookup(getEquippedModules().get(uuid));
    }

    public static CyberModule fromRegistryLookup(String id) {
        return AstranRegistries.CYBER_MODULE_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(Astran.MODID,id));
    }

    public Map<UUID, String> getEquippedModuleTypes() {
        return this.equippedModuleTypes;
    }

    public Map<String, UUID> getEquippedUUIDs() {
        return this.equippedUUIDs;
    }

    public Map<UUID, CyberModule> getEquippedTickable() {
        return this.equippedTickable;
    }

    public Map<UUID, CyberModule> getEquippedModuleInstances() {
        return this.equippedModuleInstances;
    }

    public float getWeightCached() {
        return this.weightCached;
    }

    public void setWeightCached(float weightCached) {
        this.weightCached = weightCached;
    }

    public CachedModuleData copy() {
        return new CachedModuleData(
                new HashMap<>(this.equippedModules),
                new HashMap<>(this.equippedUUIDs),
                new HashMap<>(this.equippedModuleTypes),
                new HashMap<>(this.equippedModuleInstances),
                new HashMap<>(this.equippedTickable),
                this.weightCached);
    }
}
