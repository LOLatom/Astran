package com.anonym.astran.systems.cybernetics.core;

import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record SteelHeartData(Optional<ItemStack> firstNode,Optional<ItemStack> secondNode,Optional<ItemStack> thirdNode) {
    public static final SteelHeartData EMPTY = new SteelHeartData(Optional.empty(),Optional.empty(),Optional.empty());
    public static final Codec<SteelHeartData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.optionalField("FirstNode", ItemStack.CODEC,false).forGetter(SteelHeartData::firstNode),
                    Codec.optionalField("SecondNode", ItemStack.CODEC,false).forGetter(SteelHeartData::secondNode),
                    Codec.optionalField("ThirdNode", ItemStack.CODEC,false).forGetter(SteelHeartData::thirdNode)
            ).apply(questInstance, SteelHeartData::new));

    public static final StreamCodec<ByteBuf, SteelHeartData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);





}
