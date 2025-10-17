package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.helpers.UUIDHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;
import java.util.UUID;

public class SocketData {
    public static final Codec<SocketData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.INT.fieldOf("SocketTier").forGetter(SocketData::getSocketTier),
                    UUIDHelper.CODEC.optionalFieldOf("ModuleInstanceId").forGetter(sd ->
                            Optional.ofNullable(sd.moduleInstanceId)
                    )
            ).apply(questInstance, (tier, idOpt) ->
                    new SocketData(tier, idOpt.orElse(null))
            ));

    public static final StreamCodec<ByteBuf, SocketData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private final int socketTier;
    private UUID moduleInstanceId;

    public SocketData(int socketTier, UUID moduleInstanceId) {
        this.socketTier = socketTier;
        this.moduleInstanceId = moduleInstanceId;
    }

    public SocketData(int socketTier) { this(socketTier, null); }

    public int getSocketTier() { return this.socketTier; }
    public UUID getModuleInstanceId() { return this.moduleInstanceId; }
    public boolean hasModule() { return this.moduleInstanceId != null; }
    public void clearModule() { this.moduleInstanceId = null; }
    public void setModuleInstanceId(UUID id) { this.moduleInstanceId = id; }
}
