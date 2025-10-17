package com.anonym.astran.helpers;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

import javax.annotation.Nullable;

public class CodecHelper {
    public static <T> CompoundTag encodeToTag(Codec<T> codec, T value) {
        return codec.encodeStart(NbtOps.INSTANCE, value)
                .resultOrPartial(error -> System.err.println("Encoding has failed OMG HELP HELP: " + error))
                .map(tag -> (CompoundTag) tag)
                .orElse(new CompoundTag());
    }
    public static <T> @Nullable T decodeFromTag(Codec<T> codec, CompoundTag tag) {
        return codec.parse(NbtOps.INSTANCE, tag)
                .resultOrPartial(error -> System.err.println("If you encounter that, it's bad I think: " + error))
                .orElse(null);
    }
}
