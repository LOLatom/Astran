package com.anonym.astran.systems.assembly.network;

import com.anonym.astran.Astran;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record AssembleModulePayload(String assemblyID, Map<String, ItemStack> selection) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AssembleModulePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"assemble_module_payload"));

    public static final StreamCodec<ByteBuf, AssembleModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.STRING),
            AssembleModulePayload::assemblyID,
            ByteBufCodecs.fromCodec(Codec.unboundedMap(Codec.STRING,ItemStack.CODEC)),
            AssembleModulePayload::selection,
            AssembleModulePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
