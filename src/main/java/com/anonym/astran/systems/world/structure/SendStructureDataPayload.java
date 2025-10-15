package com.anonym.astran.systems.world.structure;

import com.anonym.astran.Astran;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public record SendStructureDataPayload(Map<ResourceLocation,CompoundTag> cache) implements CustomPacketPayload {

    public static final Type<SendStructureDataPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"send_structure_data_payload"));

    public static final StreamCodec<ByteBuf, SendStructureDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.unboundedMap(ResourceLocation.CODEC,CompoundTag.CODEC)),
            SendStructureDataPayload::cache,
            SendStructureDataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
