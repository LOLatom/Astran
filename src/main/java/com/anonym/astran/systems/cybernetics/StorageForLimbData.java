package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.helpers.UUIDHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import javax.annotation.Nullable;
import java.util.*;

public class StorageForLimbData {
    public static final Codec<StorageForLimbData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(UUIDHelper.CODEC, CyberModule.CODEC)
                            .fieldOf("CyberModuleStorage")
                            .forGetter(StorageForLimbData::getCyberModuleMap)
            ).apply(instance, StorageForLimbData::new)
    );

    public static final StreamCodec<ByteBuf, StorageForLimbData> STREAM_CODEC =
            ByteBufCodecs.fromCodec(CODEC);

    private final Map<UUID, CyberModule> cyberModuleMap;

    public StorageForLimbData(Map<UUID, CyberModule> cyberModuleMap) {
        this.cyberModuleMap = cyberModuleMap;
    }

    public StorageForLimbData() {
        this(new HashMap<>());
    }

    public Map<UUID, CyberModule> getCyberModuleMap() {
        return this.cyberModuleMap;
    }

    public UUID addCyberModule(CyberModule module) {
        this.cyberModuleMap.put(module.getInstanceId(), module);
        return module.getInstanceId();
    }



    public void putCyberModule(UUID id, CyberModule module) {
        this.cyberModuleMap.put(id, module);
    }

    @Nullable
    public CyberModule getCyberModule(UUID id) {
        return this.cyberModuleMap.get(id);
    }

    public void removeCyberModule(UUID id) {
        this.cyberModuleMap.remove(id);
    }

    public List<CyberModule> getAllModules() {
        return new ArrayList<>(this.cyberModuleMap.values());
    }

    public boolean containsModule(UUID id) {
        return this.cyberModuleMap.containsKey(id);
    }

    public void clear() {
        this.cyberModuleMap.clear();
    }

    public StorageForLimbData copy() {
        return new StorageForLimbData(new HashMap<>(this.cyberModuleMap));
    }
}