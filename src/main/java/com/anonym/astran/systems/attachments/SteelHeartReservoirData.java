package com.anonym.astran.systems.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Optional;

public class SteelHeartReservoirData {
    public static final SteelHeartReservoirData EMPTY = new SteelHeartReservoirData(Optional.empty());
    public static final Codec<SteelHeartReservoirData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                Codec.optionalField("SteelHeart",ItemStack.CODEC,false).forGetter(SteelHeartReservoirData::getSteelHeart)
            ).apply(questInstance, SteelHeartReservoirData::new));

    public static final StreamCodec<ByteBuf, SteelHeartReservoirData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    private Optional<ItemStack> steelHeart;

    public SteelHeartReservoirData(Optional<ItemStack> stack) {
        this.steelHeart = stack;
    }

    public Optional<ItemStack> getSteelHeart() {
        return this.steelHeart;
    }

    public void setSteelHeart(Optional<ItemStack> steelHeart) {
        this.steelHeart = steelHeart;
    }

    public void setSteelHeart(ItemStack steelHeart) {
        if (steelHeart.equals(ItemStack.EMPTY)) { this.steelHeart = Optional.empty();} else {
            this.steelHeart = Optional.of(steelHeart);
        }
    }

    public static SteelHeartReservoirData getDefault() {
        return new SteelHeartReservoirData(Optional.empty());
    }


}
