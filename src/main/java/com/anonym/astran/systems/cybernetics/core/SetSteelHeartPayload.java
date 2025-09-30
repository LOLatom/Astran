package com.anonym.astran.systems.cybernetics.core;

import com.anonym.astran.Astran;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SetSteelHeartPayload(ItemStack stack) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SetSteelHeartPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"set_steel_heart"));

    public static final StreamCodec<ByteBuf, SetSteelHeartPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ItemStack.CODEC),
            SetSteelHeartPayload::stack,
            SetSteelHeartPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
