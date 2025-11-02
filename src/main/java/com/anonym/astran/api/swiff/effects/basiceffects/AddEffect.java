package com.anonym.astran.api.swiff.effects.basiceffects;

import com.anonym.astran.api.swiff.effects.SwiffEffect;
import net.minecraft.resources.ResourceLocation;

public class AddEffect extends SwiffEffect {
    public AddEffect() {
        super(ResourceLocation.fromNamespaceAndPath("astran","swiff/additive"));
    }
}
