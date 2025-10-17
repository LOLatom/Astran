package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record RemoveModulePayload(CyberModule module) implements CustomPacketPayload {
    public static final Type<RemoveModulePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"remove_module_payload"));

    public static final StreamCodec<ByteBuf, RemoveModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CyberModule.CODEC),
            RemoveModulePayload::module,
            RemoveModulePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
