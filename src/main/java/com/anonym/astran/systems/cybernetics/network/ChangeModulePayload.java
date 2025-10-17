package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ChangeModulePayload(CyberModule module) implements CustomPacketPayload {
    public static final Type<ChangeModulePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"change_module_payload"));

    public static final StreamCodec<ByteBuf, ChangeModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CyberModule.CODEC),
            ChangeModulePayload::module,
            ChangeModulePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
