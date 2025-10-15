package com.anonym.astran.systems.world.structure;

import com.anonym.astran.Astran;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RequestStructurePayload(List<ResourceLocation> locations) implements CustomPacketPayload {
    public static final Type<RequestStructurePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"request_structure_payload"));

    public static final StreamCodec<ByteBuf, RequestStructurePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.list(ResourceLocation.CODEC)),
            RequestStructurePayload::locations,
            RequestStructurePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
