package com.anonym.astran.systems.cybernetics.network;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.network.AssembleModulePayload;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record AddModulePayload(CyberModule module) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AddModulePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"add_module_payload"));

    public static final StreamCodec<ByteBuf, AddModulePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CyberModule.CODEC),
            AddModulePayload::module,
            AddModulePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
