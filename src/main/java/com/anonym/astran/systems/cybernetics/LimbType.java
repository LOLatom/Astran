package com.anonym.astran.systems.cybernetics;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum LimbType implements StringRepresentable {
    HEAD("head"),
    TORSO("torso"),
    HIPS("hips"),
    RIGHT_SHOULDER("right_shoulder"),
    LEFT_SHOULDER("left_shoulder"),
    RIGHT_HAND("right_hand"),
    LEFT_HAND("left_hand"),
    RIGHT_LEG("right_leg"),
    LEFT_LEG("left_leg");

    private final String id;

    LimbType(String id) {
        this.id = id;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public static final Codec<LimbType> CODEC = StringRepresentable.fromEnum(LimbType::values);

    @Override
    public String toString() {
        return this.id;
    }
}
