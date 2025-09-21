package com.anonym.astran.client.layers;

import com.anonym.astran.Astran;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class LayerRegistry {

    public static final ModelLayerLocation ASTRANIUM_METEOR = register("astranium_meteor");



    private static ModelLayerLocation register(String loc) {
        return register(loc, "main");
    }

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Astran.MODID, id), name);
    }
}
