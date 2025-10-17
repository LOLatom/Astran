package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record UnEquipModulePayload(int socketIndex, CyberModule module) implements CustomPacketPayload {
    public static final Type<UnEquipModulePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"unequip_module_payload"));

    public static final StreamCodec<ByteBuf, UnEquipModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.INT),
            UnEquipModulePayload::socketIndex,
            ByteBufCodecs.fromCodec(CyberModule.CODEC),
            UnEquipModulePayload::module,
            UnEquipModulePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
