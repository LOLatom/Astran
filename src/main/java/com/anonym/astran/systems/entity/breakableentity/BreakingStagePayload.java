package com.anonym.astran.systems.entity.breakableentity;

import com.anonym.astran.Astran;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record BreakingStagePayload(int stage, int id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BreakingStagePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"breaking_stage"));

    public static final StreamCodec<ByteBuf, BreakingStagePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.INT),
            BreakingStagePayload::stage,
            ByteBufCodecs.fromCodec(Codec.INT),
            BreakingStagePayload::id,
            BreakingStagePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
