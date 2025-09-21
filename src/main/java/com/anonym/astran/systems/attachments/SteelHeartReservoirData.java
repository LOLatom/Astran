package com.anonym.astran.systems.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class SteelHeartReservoirData {
    public static final SteelHeartReservoirData EMPTY = new SteelHeartReservoirData(ItemStack.EMPTY);
    public static final Codec<SteelHeartReservoirData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                ItemStack.CODEC.fieldOf("SteelHeart").forGetter(SteelHeartReservoirData::getSteelHeart)
            ).apply(questInstance, SteelHeartReservoirData::new));

    public static final StreamCodec<ByteBuf, SteelHeartReservoirData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    private ItemStack steelHeart;

    public SteelHeartReservoirData(ItemStack stack) {
        this.steelHeart = stack;
    }

    public ItemStack getSteelHeart() {
        return this.steelHeart;
    }


    public static SteelHeartReservoirData getDefault() {
        return new SteelHeartReservoirData(ItemStack.EMPTY);
    }


}
