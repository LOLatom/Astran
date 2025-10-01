package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class BoneData {
    public static final BoneData EMPTY = new BoneData(Optional.empty());
    public static final Codec<BoneData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.optionalField("SteelHeart", ItemStack.CODEC,false).forGetter(BoneData::getSteelHeart)
            ).apply(questInstance, BoneData::new));

    public static final StreamCodec<ByteBuf, BoneData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    private Optional<ItemStack> steelHeart;

    public BoneData(Optional<ItemStack> stack) {
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
