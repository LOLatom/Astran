package com.anonym.astran.helpers;

import com.mojang.serialization.Codec;

import java.util.UUID;

public class UUIDHelper {
    public static final Codec<UUID> CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);

}
