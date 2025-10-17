package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record EquipModulePayload(int socketIndex, CyberModule module) implements CustomPacketPayload {
    public static final Type<EquipModulePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"equip_module_payload"));

    public static final StreamCodec<ByteBuf, EquipModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.INT),
            EquipModulePayload::socketIndex,
            ByteBufCodecs.fromCodec(CyberModule.CODEC),
            EquipModulePayload::module,
            EquipModulePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
